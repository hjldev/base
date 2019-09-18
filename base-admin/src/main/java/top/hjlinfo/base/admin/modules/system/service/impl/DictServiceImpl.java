package top.hjlinfo.base.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.hjlinfo.base.admin.modules.logging.domain.SysLog;
import top.hjlinfo.base.admin.modules.system.dao.DictDao;
import top.hjlinfo.base.admin.modules.system.domain.SysDict;
import top.hjlinfo.base.admin.modules.system.service.DictService;
import top.hjlinfo.base.admin.modules.system.service.dto.DictDTO;
import top.hjlinfo.base.admin.modules.system.service.mapper.DictMapper;
import top.hjlinfo.base.common.utils.PageUtil;
import top.hjlinfo.base.common.utils.QueryHelp;

/**
* @author sting
* @date 2019-04-10
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@AllArgsConstructor
public class DictServiceImpl implements DictService {

    private final DictDao dictDao;
    private final DictMapper dictMapper;

    @Override
    public Object queryAll(DictDTO dict, Pageable pageable) {
        Page<SysDict> page = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<SysDict> data = dictDao.selectPage(page, QueryHelp.<SysDict, DictDTO>buildWrapper(dict));
        return PageUtil.toPage(data);
    }

    @Override
    public DictDTO findById(Long id) {
        SysDict dict = dictDao.selectById(id);
        return dictMapper.toDto(dict);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DictDTO create(SysDict resources) {
        dictDao.insert(resources);
        return dictMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDict resources) {
        dictDao.updateById(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        dictDao.deleteById(id);
    }
}