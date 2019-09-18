package top.hjlinfo.base.admin.modules.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.hjlinfo.base.admin.modules.system.dao.*;
import top.hjlinfo.base.admin.modules.system.domain.*;
import top.hjlinfo.base.admin.modules.system.service.RoleService;
import top.hjlinfo.base.admin.modules.system.service.dto.CommonQueryCriteria;
import top.hjlinfo.base.admin.modules.system.service.dto.RoleDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.RoleSmallDTO;
import top.hjlinfo.base.admin.modules.system.service.mapper.RoleMapper;
import top.hjlinfo.base.admin.modules.system.service.mapper.RoleSmallMapper;
import top.hjlinfo.base.common.exception.EntityExistException;
import top.hjlinfo.base.common.utils.PageUtil;
import top.hjlinfo.base.common.utils.QueryHelp;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author sting
 * @date 2018-12-03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    private final RoleSmallMapper roleSmallMapper;

    private final RoleDao roleDao;
    private final RolesDeptsDao rolesDeptsDao;
    private final RolesMenusDao rolesMenusDao;
    private final RolesPermissionsDao rolesPermissionsDao;
    private final UsersRolesDao usersRolesDao;

    @Override
    public Object queryAll(Pageable pageable) {
        Page<SysRole> page = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<SysRole> data = roleDao.selectPage(page, null);
        return roleMapper.toDto(data.getRecords());
    }

    @Override
    public Object queryAll(CommonQueryCriteria criteria, Pageable pageable) {
        Page<SysRole> page = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<SysRole> data = roleDao.selectPage(page, QueryHelp.<SysRole, CommonQueryCriteria>buildWrapper(criteria));
        return PageUtil.toPage(roleMapper.toDto(data.getRecords()), data.getTotal());
    }

    @Override
    public RoleDTO findById(long id) {
        SysRole role = roleDao.selectById(id);
        return roleMapper.toDto(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDTO create(SysRole resources) {
        roleDao.insert(resources);
        return roleMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRole resources) {

        SysRole role = roleDao.selectById(resources.getId());

        SysRole role1 = roleDao.selectOne(new QueryWrapper<SysRole>().eq("name", resources.getName()));

        if(role1 != null && !role1.getId().equals(role.getId())){
            throw new EntityExistException(SysRole.class,"username",resources.getName());
        }

        role.setName(resources.getName());
        role.setRemark(resources.getRemark());
        role.setDataScope(resources.getDataScope());
        // 先删除role字段然后再添加
        rolesDeptsDao.delete(new QueryWrapper<SysRolesDepts>().eq("role_id", resources.getId()));
        resources.getDepts().forEach(sysDept -> {
            SysRolesDepts rolesDepts = new SysRolesDepts();
            rolesDepts.setDeptId(sysDept.getId());
            rolesDepts.setRoleId(resources.getId());
            rolesDeptsDao.insert(rolesDepts);
        });
        role.setLevel(resources.getLevel());
        roleDao.updateById(role);
    }

    @Override
    public void updatePermission(SysRole resources) {
        rolesPermissionsDao.delete(new QueryWrapper<SysRolesPermissions>().eq("role_id", resources.getId()));
        resources.getPermissions().forEach(sysPermission -> {
            SysRolesPermissions rolesPermissions = new SysRolesPermissions();
            rolesPermissions.setPermissionId(sysPermission.getId());
            rolesPermissions.setRoleId(resources.getId());
            rolesPermissionsDao.insert(rolesPermissions);
        });
    }

    @Override
    public void updateMenu(SysRole resources) {
        rolesMenusDao.delete(new QueryWrapper<SysRolesMenus>().eq("role_id", resources.getId()));
        resources.getMenus().forEach(sysMenu -> {
            SysRolesMenus rolesMenus = new SysRolesMenus();
            rolesMenus.setMenuId(sysMenu.getId());
            rolesMenus.setRoleId(resources.getId());
            rolesMenusDao.insert(rolesMenus);
        });
    }

    @Override
    public void untiedMenu(SysMenu menu) {
        rolesMenusDao.delete(new QueryWrapper<SysRolesMenus>().eq("menu_id", menu.getId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        List<SysUsersRoles> usersRoles = usersRolesDao.selectList(new QueryWrapper<SysUsersRoles>().eq("role_id", id));
        if (CollectionUtil.isNotEmpty(usersRoles)) {
            return;
        }
        // 删除相关关联表数据
        roleDao.deleteById(id);
        rolesDeptsDao.delete(new QueryWrapper<SysRolesDepts>().eq("role_id", id));
        rolesMenusDao.delete(new QueryWrapper<SysRolesMenus>().eq("role_id", id));
        rolesPermissionsDao.delete(new QueryWrapper<SysRolesPermissions>().eq("role_id", id));

    }

    @Override
    public List<RoleSmallDTO> findByUsers_Id(Long id) {
        return roleSmallMapper.toDto(roleDao.findByUserId(id));
    }

    @Override
    public Integer findByRoles(List<SysRole> roles) {
        Set<RoleDTO> roleDTOS = new HashSet<>();
        for (SysRole role : roles) {
            roleDTOS.add(findById(role.getId()));
        }
        return Collections.min(roleDTOS.stream().map(RoleDTO::getLevel).collect(Collectors.toList()));
    }
}
