package top.hjlinfo.base.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.hjlinfo.base.admin.modules.system.dao.DictDetailDao;
import top.hjlinfo.base.admin.modules.system.domain.SysDictDetail;
import top.hjlinfo.base.admin.modules.system.service.DictDetailService;
import top.hjlinfo.base.admin.modules.system.service.dto.DictDetailDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.DictDetailQueryCriteria;
import top.hjlinfo.base.admin.modules.system.service.mapper.DictDetailMapper;
import top.hjlinfo.base.common.utils.PageUtil;
import top.hjlinfo.base.common.utils.QueryHelp;

/**
* @author sting
* @date 2019-04-10
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@AllArgsConstructor
public class DictDetailServiceImpl implements DictDetailService {

    private final DictDetailDao dictDetailDao;

    private final DictDetailMapper dictDetailMapper;

    @Override
    public Object queryAll(DictDetailQueryCriteria criteria, Pageable pageable) {
        Page<SysDictDetail> page = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<SysDictDetail> data = dictDetailDao.selectPage(page, QueryHelp.<SysDictDetail, DictDetailQueryCriteria>buildWrapper(criteria));
        return PageUtil.toPage(data);
    }

    @Override
    public DictDetailDTO findById(Long id) {
        SysDictDetail dictDetail = dictDetailDao.selectById(id);
        return dictDetailMapper.toDto(dictDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDetailDTO create(SysDictDetail resources) {
        dictDetailDao.insert(resources);
        return dictDetailMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictDetail resources) {
        dictDetailDao.updateById(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        dictDetailDao.deleteById(id);
    }
}