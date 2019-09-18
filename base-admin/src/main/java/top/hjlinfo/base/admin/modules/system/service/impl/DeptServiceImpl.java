package top.hjlinfo.base.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.hjlinfo.base.admin.modules.system.dao.DeptDao;
import top.hjlinfo.base.admin.modules.system.domain.SysDept;
import top.hjlinfo.base.admin.modules.system.service.DeptService;
import top.hjlinfo.base.admin.modules.system.service.dto.DeptDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.DeptQueryCriteria;
import top.hjlinfo.base.admin.modules.system.service.mapper.DeptMapper;
import top.hjlinfo.base.common.utils.QueryHelp;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author sting
* @date 2019-03-25
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@AllArgsConstructor
public class DeptServiceImpl implements DeptService {

    private final DeptMapper deptMapper;
    private final DeptDao deptDao;

    @Override
    public List<DeptDTO> queryAll(DeptQueryCriteria criteria) {
        List<SysDept> data = deptDao.selectList(QueryHelp.<SysDept, DeptQueryCriteria>buildWrapper(criteria));
        return deptMapper.toDto(data);
    }

    @Override
    public DeptDTO findById(Long id) {
        SysDept dept = deptDao.selectById(id);
        return deptMapper.toDto(dept);
    }

    @Override
    public List<SysDept> findByPid(long pid) {
        return deptDao.selectList(new QueryWrapper<SysDept>().eq("pid", pid));
    }

    @Override
    public List<SysDept> findByRoleIds(Long id) {
        List<Long> deptIds = deptDao.findIdByRoleId(id);
        return deptDao.selectList(new QueryWrapper<SysDept>().in("id", deptIds));
    }

    @Override
    public List<Long> findIdByRoleId(Long roleId) {
        return deptDao.findIdByRoleId(roleId);
    }

    @Override
    public Object buildTree(List<DeptDTO> deptDTOS) {
        Set<DeptDTO> trees = new LinkedHashSet<>();
        Set<DeptDTO> depts= new LinkedHashSet<>();
        List<String> deptNames = deptDTOS.stream().map(DeptDTO::getName).collect(Collectors.toList());
        Boolean isChild;
        for (DeptDTO deptDTO : deptDTOS) {
            isChild = false;
            if ("0".equals(deptDTO.getPid().toString())) {
                trees.add(deptDTO);
            }
            for (DeptDTO it : deptDTOS) {
                if (it.getPid().equals(deptDTO.getId())) {
                    isChild = true;
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<>());
                    }
                    deptDTO.getChildren().add(it);
                }
            }
            if(isChild)
                depts.add(deptDTO);
            else if(!deptNames.contains(deptDao.findNameById(deptDTO.getPid())))
                depts.add(deptDTO);
        }

        if (CollectionUtils.isEmpty(trees)) {
            trees = depts;
        }

        Integer totalElements = deptDTOS!=null?deptDTOS.size():0;

        Map map = new HashMap();
        map.put("totalElements",totalElements);
        map.put("content",CollectionUtils.isEmpty(trees)?deptDTOS:trees);
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeptDTO create(SysDept resources) {
        deptDao.insert(resources);
        return deptMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDept resources) {
        deptDao.updateById(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        deptDao.deleteById(id);
    }
}