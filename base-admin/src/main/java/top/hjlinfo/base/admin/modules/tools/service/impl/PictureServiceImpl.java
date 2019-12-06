package top.hjlinfo.base.admin.modules.tools.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.hjlinfo.base.admin.modules.tools.dao.PictureDao;
import top.hjlinfo.base.admin.modules.tools.domain.Picture;
import top.hjlinfo.base.admin.modules.tools.service.PictureService;
import top.hjlinfo.base.admin.modules.tools.service.dto.PictureQueryCriteria;
import top.hjlinfo.base.common.exception.BadRequestException;
import top.hjlinfo.base.common.utils.BaseConstant;
import top.hjlinfo.base.common.utils.FileUtil;
import top.hjlinfo.base.common.utils.PageUtil;
import top.hjlinfo.base.common.utils.QueryHelp;

import java.io.File;
import java.util.HashMap;

/**
 * @author sting
 * @date 2018-12-27
 */
@Slf4j
@Service(value = "pictureService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private final PictureDao pictureDao;

    public static final String SUCCESS = "success";

    public static final String CODE = "code";

    public static final String MSG = "msg";

    @Override
    public Object queryAll(PictureQueryCriteria criteria, Pageable pageable){
        Page<Picture> page = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<Picture> data = pictureDao.selectPage(page, QueryHelp.<Picture, PictureQueryCriteria>buildWrapper(criteria));
        return PageUtil.toPage(data);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Picture upload(MultipartFile multipartFile, String username) {
        File file = FileUtil.toFile(multipartFile);

        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("smfile", file);
        String result= HttpUtil.post(BaseConstant.Url.SM_MS_URL, paramMap);

        JSONObject jsonObject = JSONUtil.parseObj(result);
        Picture picture = null;
        if(!jsonObject.get(CODE).toString().equals(SUCCESS)){
            throw new BadRequestException(jsonObject.get(MSG).toString());
        }
        //转成实体类
        picture = JSON.parseObject(jsonObject.get("data").toString(), Picture.class);
        picture.setSize(FileUtil.getSize(Integer.valueOf(picture.getSize())));
        picture.setUsername(username);
        picture.setFilename(FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename())+"."+FileUtil.getExtensionName(multipartFile.getOriginalFilename()));
        pictureDao.insert(picture);
        //删除临时文件
        FileUtil.deleteFile(file);
        return picture;

    }

    @Override
    public Picture findById(Long id) {
        Picture picture = pictureDao.selectById(id);
        return picture;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Picture picture) {
        try {
            String result= HttpUtil.get(picture.getDeleteUrl());
            pictureDao.deleteById(picture.getId());
        } catch(Exception e){
            pictureDao.deleteById(picture.getId());
        }

    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            delete(findById(id));
        }
    }
}
