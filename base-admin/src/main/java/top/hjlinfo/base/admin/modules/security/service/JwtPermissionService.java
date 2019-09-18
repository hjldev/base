package top.hjlinfo.base.admin.modules.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import top.hjlinfo.base.admin.modules.system.dao.PermissionDao;
import top.hjlinfo.base.admin.modules.system.dao.RoleDao;
import top.hjlinfo.base.admin.modules.system.domain.SysRole;
import top.hjlinfo.base.admin.modules.system.service.dto.UserDTO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "role")
@RequiredArgsConstructor
public class JwtPermissionService {

    private final RoleDao roleDao;
    private final PermissionDao permissionDao;

    /**
     * key的名称如有修改，请同步修改 UserServiceImpl 中的 update 方法
     * @param user
     * @return
     */
    @Cacheable(key = "'loadPermissionByUser:' + #p0.username")
    public Collection<GrantedAuthority> mapToGrantedAuthorities(UserDTO user) {

        System.out.println("--------------------loadPermissionByUser:" + user.getUsername() + "---------------------");

        List<SysRole> roles = roleDao.findByUserId(user.getId());
        List<Long> roleIds = roles.stream().map(SysRole::getId)
                .collect(Collectors.toList());
        return permissionDao.findNameByRoleId(roleIds).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

//        return roles.stream().flatMap(role -> role.getPermissions().stream())
//                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
//                .collect(Collectors.toList());
    }
}
