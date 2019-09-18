package top.hjlinfo.base.admin.modules.tools.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.hjlinfo.base.admin.modules.tools.dao.QiNiuConfigDao;
import top.hjlinfo.base.admin.modules.tools.dao.QiniuContentDao;
import top.hjlinfo.base.admin.modules.tools.domain.QiniuConfig;
import top.hjlinfo.base.admin.modules.tools.domain.QiniuContent;
import top.hjlinfo.base.admin.modules.tools.service.QiNiuService;
import top.hjlinfo.base.admin.modules.tools.service.dto.QiniuQueryCriteria;
import top.hjlinfo.base.admin.modules.tools.utils.QiNiuUtil;
import top.hjlinfo.base.common.exception.BadRequestException;
import top.hjlinfo.base.common.utils.FileUtil;
import top.hjlinfo.base.common.utils.PageUtil;
import top.hjlinfo.base.common.utils.QueryHelp;

/**
 * @author sting
 * @date 2018-12-31
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class QiNiuServiceImpl implements QiNiuService {

    private final QiNiuConfigDao qiNiuConfigDao;
    private final QiniuContentDao qiniuContentDao;

    @Value("${qiniu.max-size}")
    private Long maxSize;

    private final String TYPE = "公开";

    @Override
    public Object queryAll(QiniuQueryCriteria criteria, Pageable pageable) {
        Page<QiniuContent> page = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<QiniuContent> data = qiniuContentDao.selectPage(page, QueryHelp.<QiniuContent, QiniuQueryCriteria>buildWrapper(criteria));
        return PageUtil.toPage(data);
    }

    @Override
    public QiniuConfig find() {
        return qiNiuConfigDao.selectById(1L);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QiniuConfig update(QiniuConfig qiniuConfig) {
        if (!(qiniuConfig.getHost().toLowerCase().startsWith("http://")||qiniuConfig.getHost().toLowerCase().startsWith("https://"))) {
            throw new BadRequestException("外链域名必须以http://或者https://开头");
        }
        qiniuConfig.setId(1L);
        qiNiuConfigDao.updateById(qiniuConfig);
        return qiniuConfig;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public QiniuContent upload(MultipartFile file, QiniuConfig qiniuConfig) {

        Long size = maxSize * 1024 * 1024;
        if(file.getSize() > size){
            throw new BadRequestException("文件超出规定大小");
        }
        if(qiniuConfig.getId() == null){
            throw new BadRequestException("请先添加相应配置，再操作");
        }
        /**
         * 构造一个带指定Zone对象的配置类
         */
        Configuration cfg = QiNiuUtil.getConfiguration(qiniuConfig.getZone());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(qiniuConfig.getBucket());
        try {
            String key = file.getOriginalFilename();

            if(qiniuContentDao.selectOne(new QueryWrapper<QiniuContent>().eq("key", key)) != null) {
                key = QiNiuUtil.getKey(key);
            }
            Response response = uploadManager.put(file.getBytes(), key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //存入数据库
            QiniuContent qiniuContent = new QiniuContent();
            qiniuContent.setBucket(qiniuConfig.getBucket());
            qiniuContent.setType(qiniuConfig.getType());
            qiniuContent.setKey(putRet.key);
            qiniuContent.setUrl(qiniuConfig.getHost()+"/"+putRet.key);
            qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(file.getSize()+"")));
            qiniuContentDao.insert(qiniuContent);
            return qiniuContent;
        } catch (Exception e) {
           throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public QiniuContent findByContentId(Long id) {
        return qiniuContentDao.selectById(id);
    }

    @Override
    public String download(QiniuContent content,QiniuConfig config){
        String finalUrl = null;
        if(TYPE.equals(content.getType())){
            finalUrl  = content.getUrl();
        } else {
            Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
            /**
             * 1小时，可以自定义链接过期时间
             */
            long expireInSeconds = 3600;
            finalUrl = auth.privateDownloadUrl(content.getUrl(), expireInSeconds);
        }
        return finalUrl;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(QiniuContent content, QiniuConfig config) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = QiNiuUtil.getConfiguration(config.getZone());
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(content.getBucket(), content.getKey());
        } catch (QiniuException ignored) {

        } finally {
            qiniuContentDao.deleteById(content.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void synchronize(QiniuConfig config) {
        if(config.getId() == null){
            throw new BadRequestException("请先添加相应配置，再操作");
        }
        //构造一个带指定Zone对象的配置类
        Configuration cfg = QiNiuUtil.getConfiguration(config.getZone());
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(config.getBucket(), prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            QiniuContent qiniuContent = null;
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                if(qiniuContentDao.selectOne(new QueryWrapper<QiniuContent>().eq("key", item.key)) == null){
                    qiniuContent = new QiniuContent();
                    qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(item.fsize+"")));
                    qiniuContent.setKey(item.key);
                    qiniuContent.setType(config.getType());
                    qiniuContent.setBucket(config.getBucket());
                    qiniuContent.setUrl(config.getHost()+"/"+item.key);
                    qiniuContentDao.insert(qiniuContent);
                }
            }
        }

    }

    @Override
    public void deleteAll(Long[] ids, QiniuConfig config) {
        for (Long id : ids) {
            delete(findByContentId(id), config);
        }
    }
}
