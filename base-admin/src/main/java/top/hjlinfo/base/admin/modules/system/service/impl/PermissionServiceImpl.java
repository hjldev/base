package top.hjlinfo.base.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.hjlinfo.base.admin.modules.system.dao.PermissionDao;
import top.hjlinfo.base.admin.modules.system.domain.SysPermission;
import top.hjlinfo.base.admin.modules.system.service.PermissionService;
import top.hjlinfo.base.admin.modules.system.service.dto.CommonQueryCriteria;
import top.hjlinfo.base.admin.modules.system.service.dto.PermissionDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.RoleSmallDTO;
import top.hjlinfo.base.admin.modules.system.service.mapper.PermissionMapper;
import top.hjlinfo.base.common.exception.BadRequestException;
import top.hjlinfo.base.common.exception.EntityExistException;
import top.hjlinfo.base.common.utils.QueryHelp;

import java.util.*;

/**
 * @author sting
 * @date 2018-12-03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionDao permissionDao;

    private final PermissionMapper permissionMapper;

    @Override
    public List<PermissionDTO> queryAll(CommonQueryCriteria criteria) {
        List<SysPermission> data = permissionDao.selectList(QueryHelp.<SysPermission, CommonQueryCriteria>buildWrapper(criteria));
        return permissionMapper.toDto(data);
    }

    @Override
    public PermissionDTO findById(long id) {
        SysPermission data = permissionDao.selectById(id);
        return permissionMapper.toDto(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PermissionDTO create(SysPermission resources) {
        permissionDao.insert(resources);
        return permissionMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysPermission resources) {
        if(resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        SysPermission permission = permissionDao.selectById(resources.getId());
        SysPermission permission1 = permissionDao.selectOne(new QueryWrapper<SysPermission>().eq("name", resources.getName()));

        if(permission1 != null && !permission1.getId().equals(permission.getId())){
            throw new EntityExistException(SysPermission.class,"name",resources.getName());
        }

        permission.setName(resources.getName());
        permission.setAlias(resources.getAlias());
        permission.setPid(resources.getPid());
        permissionDao.updateById(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        List<SysPermission> permissionList = permissionDao.selectList(new QueryWrapper<SysPermission>().eq("pid", id));
        for (SysPermission permission : permissionList) {
            permissionDao.deleteById(permission.getId());
        }
        permissionDao.deleteById(id);
    }

    @Override
    public Object getPermissionTree(List<SysPermission> permissions) {
        List<Map<String,Object>> list = new LinkedList<>();
        permissions.forEach(permission -> {
                    if (permission!=null){
                        List<SysPermission> permissionList = permissionDao.selectList(new QueryWrapper<SysPermission>().eq("pid", permission.getId()));
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",permission.getId());
                        map.put("label",permission.getAlias());
                        if(permissionList!=null && permissionList.size()!=0){
                            map.put("children",getPermissionTree(permissionList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Override
    public List<SysPermission> findByPid(long pid) {
        return permissionDao.selectList(new QueryWrapper<SysPermission>().eq("pid", pid));
    }

    @Override
    public Object buildTree(List<PermissionDTO> permissionDTOS) {

        List<PermissionDTO> trees = new ArrayList<PermissionDTO>();

        for (PermissionDTO permissionDTO : permissionDTOS) {

            if ("0".equals(permissionDTO.getPid().toString())) {
                trees.add(permissionDTO);
            }

            for (PermissionDTO it : permissionDTOS) {
                if (it.getPid().equals(permissionDTO.getId())) {
                    if (permissionDTO.getChildren() == null) {
                        permissionDTO.setChildren(new ArrayList<PermissionDTO>());
                    }
                    permissionDTO.getChildren().add(it);
                }
            }
        }

        Integer totalElements = permissionDTOS!=null?permissionDTOS.size():0;

        Map map = new HashMap();
        map.put("content",trees.size() == 0?permissionDTOS:trees);
        map.put("totalElements",totalElements);
        return map;
    }

    @Override
    public List<Long> findIdsByRole(RoleSmallDTO role) {
        return permissionDao.findIdsByRoleId(role.getId());
    }


}
