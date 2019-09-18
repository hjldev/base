package top.hjlinfo.base.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.hjlinfo.base.admin.modules.monitor.service.RedisService;
import top.hjlinfo.base.admin.modules.system.dao.RoleDao;
import top.hjlinfo.base.admin.modules.system.dao.UserDao;
import top.hjlinfo.base.admin.modules.system.dao.UsersRolesDao;
import top.hjlinfo.base.admin.modules.system.domain.SysRole;
import top.hjlinfo.base.admin.modules.system.domain.SysUser;
import top.hjlinfo.base.admin.modules.system.domain.SysUsersRoles;
import top.hjlinfo.base.admin.modules.system.service.UserService;
import top.hjlinfo.base.admin.modules.system.service.dto.UserDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.UserQueryCriteria;
import top.hjlinfo.base.admin.modules.system.service.mapper.UserMapper;
import top.hjlinfo.base.common.exception.EntityExistException;
import top.hjlinfo.base.common.exception.EntityNotFoundException;
import top.hjlinfo.base.common.utils.PageUtil;
import top.hjlinfo.base.common.utils.QueryHelp;
import top.hjlinfo.base.common.utils.ValidationUtil;

import java.util.List;

/**
 * @author sting
 * @date 2018-11-23
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final RedisService redisService;

    private final UserDao userDao;

    private final UsersRolesDao usersRolesDao;
    private final RoleDao roleDao;

    @Override
    public Object queryAll(UserQueryCriteria criteria, Pageable pageable) {
        Page<SysUser> page = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<SysUser> data = userDao.selectUsers(page, QueryHelp.<SysUser, UserQueryCriteria>buildWrapper(criteria));
        return PageUtil.toPage(data.getRecords(), data.getTotal());
    }

    @Override
    public UserDTO findById(long id) {
        SysUser user = userDao.selectById(id);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO create(SysUser resources) {

        // 默认密码 123456，此密码是加密后的字符
        resources.setPassword("e10adc3949ba59abbe56e057f20f883e");
        resources.setAvatar("https://i.loli.net/2019/04/04/5ca5b971e1548.jpeg");

        userDao.insert(resources);
        resources.getRoles().forEach(sysRole -> {
            SysUsersRoles usersRoles = new SysUsersRoles();
            usersRoles.setUserId(resources.getId());
            usersRoles.setRoleId(sysRole.getId());
            usersRolesDao.insert(usersRoles);
        });

        return userMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUser resources) {
        SysUser user = userDao.selectById(resources.getId());

        SysUser user1 = userDao.selectOne(new QueryWrapper<SysUser>().eq("username", user.getUsername()));
        SysUser user2 = userDao.selectOne(new QueryWrapper<SysUser>().eq("email", user.getEmail()));

        if(user1 !=null&&!user.getId().equals(user1.getId())){
            throw new EntityExistException(SysUser.class,"username",resources.getUsername());
        }

        if(user2!=null&&!user.getId().equals(user2.getId())){
            throw new EntityExistException(SysUser.class,"email",resources.getEmail());
        }

        List<SysRole> roles = roleDao.findByUserId(user.getId());
        if (!resources.getRoles().equals(roles)) {
            String key = "role::loadPermissionByUser:" + user.getUsername();
            redisService.delete(key);
            key = "role::findByUsers_Id:" + user.getId();
            redisService.delete(key);

            // 更新user跟role表多对多关系
            usersRolesDao.delete(new QueryWrapper<SysUsersRoles>().eq("user_id", resources.getId()));
            resources.getRoles().forEach(sysRole -> {
                SysUsersRoles usersRoles = new SysUsersRoles();
                usersRoles.setUserId(resources.getId());
                usersRoles.setRoleId(sysRole.getId());
                usersRolesDao.insert(usersRoles);
            });
        }

        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setPhone(resources.getPhone());
        user.setDeptId(resources.getDeptId());
        user.setJobId(resources.getJobId());
        userDao.updateById(resources);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userDao.deleteById(id);
        usersRolesDao.delete(new QueryWrapper<SysUsersRoles>().eq("user_id", id));
    }

    @Override
    public UserDTO findByName(String userName) {
        SysUser user = null;
        if(ValidationUtil.isEmail(userName)){
            user = userDao.findUserInfoByEmail(userName);
//            user = userDao.selectOne(new QueryWrapper<SysUser>().eq("email", userName));
        } else {
            user = userDao.findUserInfoByUsername(userName);
//            user = userDao.selectOne(new QueryWrapper<SysUser>().eq("username", userName));
        }
        if (user == null) {
            throw new EntityNotFoundException(SysUser.class, "name", userName);
        } else {
            return userMapper.toDto(user);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        SysUser user = userDao.selectOne(new QueryWrapper<SysUser>().eq("username", username));
        user.setPassword(pass);
        userDao.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String username, String url) {
        SysUser user = userDao.selectOne(new QueryWrapper<SysUser>().eq("username", username));
        user.setAvatar(url);
        userDao.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        SysUser user = userDao.selectOne(new QueryWrapper<SysUser>().eq("username", username));
        user.setEmail(email);
        userDao.updateById(user);
    }
}
