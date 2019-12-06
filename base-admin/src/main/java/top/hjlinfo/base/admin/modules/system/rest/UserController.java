package top.hjlinfo.base.admin.modules.system.rest;

import cn.hutool.crypto.digest.DigestUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.hjlinfo.base.admin.config.DataScope;
import top.hjlinfo.base.admin.modules.logging.aop.log.Log;
import top.hjlinfo.base.admin.modules.system.domain.SysUser;
import top.hjlinfo.base.admin.modules.system.service.DeptService;
import top.hjlinfo.base.admin.modules.system.service.RoleService;
import top.hjlinfo.base.admin.modules.system.service.UserService;
import top.hjlinfo.base.admin.modules.system.service.dto.RoleSmallDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.UserQueryCriteria;
import top.hjlinfo.base.admin.modules.tools.domain.Picture;
import top.hjlinfo.base.admin.modules.tools.service.PictureService;
import top.hjlinfo.base.common.exception.BadRequestException;
import top.hjlinfo.base.common.utils.PageUtil;
import top.hjlinfo.base.common.utils.SecurityUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author sting
 * @date 2018-11-23
 */
@RestController
@RequestMapping("api")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final DataScope dataScope;

    private final DeptService deptService;

    private final RoleService roleService;

    private final PictureService pictureService;

    private static final String ENTITY_NAME = "user";

    @Log("查询用户")
    @GetMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public ResponseEntity getUsers(UserQueryCriteria criteria, Pageable pageable) {
        Set<Long> deptSet = new HashSet<>();
        Set<Long> result = new HashSet<>();

        if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
            deptSet.add(criteria.getDeptId());
            deptSet.addAll(dataScope.getDeptChildren(deptService.findByPid(criteria.getDeptId())));
        }

        // 数据权限
        Set<Long> deptIds = dataScope.getDeptIds();

        // 查询条件不为空并且数据权限不为空则取交集
        if (!CollectionUtils.isEmpty(deptIds) && !CollectionUtils.isEmpty(deptSet)) {

            // 取交集
            result.addAll(deptSet);
            result.retainAll(deptIds);

            // 若无交集，则代表无数据权限
            criteria.setDeptIds(result);
            if (result.size() == 0) {
                return new ResponseEntity<>(PageUtil.toPage(null, 0), HttpStatus.OK);
            } else return new ResponseEntity<>(userService.queryAll(criteria, pageable), HttpStatus.OK);
            // 否则取并集
        } else {
            result.addAll(deptSet);
            result.addAll(deptIds);
            criteria.setDeptIds(result);
            return new ResponseEntity<>(userService.queryAll(criteria, pageable), HttpStatus.OK);
        }
    }

    @Log("新增用户")
    @PostMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody SysUser resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        checkLevel(resources);
        return new ResponseEntity<>(userService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改用户")
    @PutMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_EDIT')")
    public ResponseEntity update(@RequestBody SysUser resources) {
        checkLevel(resources);
        userService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @DeleteMapping(value = "/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        Integer currentLevel = Collections.min(roleService.findByUsers_Id(SecurityUtil.getUserId()).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList()));
        Integer optLevel = Collections.min(roleService.findByUsers_Id(id).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList()));

        if (currentLevel > optLevel) {
            throw new BadRequestException("角色权限不足");
        }
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 验证密码
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/users/validPass")
    public ResponseEntity validPass(@RequestBody SysUser user) {
        UserDetails userDetails = SecurityUtil.getUserDetails();
        Map map = new HashMap();
        map.put("status", 200);
        if (!userDetails.getPassword().equals(DigestUtil.md5Hex(user.getPassword()))) {
            map.put("status", 400);
        }
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/users/updatePass")
    public ResponseEntity updatePass(@RequestBody SysUser user) {
        UserDetails userDetails = SecurityUtil.getUserDetails();
        if (userDetails.getPassword().equals(DigestUtil.md5Hex(user.getPassword()))) {
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        userService.updatePass(userDetails.getUsername(), DigestUtil.md5Hex(user.getPassword()));
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改头像
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/users/updateAvatar")
    public ResponseEntity updateAvatar(@RequestParam MultipartFile file) {
        Picture picture = pictureService.upload(file, SecurityUtil.getUsername());
        userService.updateAvatar(SecurityUtil.getUsername(), picture.getUrl());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改邮箱
     *
     * @param user
     * @param user
     * @return
     */
    @Log("修改邮箱")
    @PostMapping(value = "/users/updateEmail/{code}")
    public ResponseEntity updateEmail(@PathVariable String code, @RequestBody SysUser user) {
        UserDetails userDetails = SecurityUtil.getUserDetails();
        if (!userDetails.getPassword().equals(DigestUtil.md5Hex(user.getPassword()))) {
            throw new BadRequestException("密码错误");
        }
        userService.updateEmail(userDetails.getUsername(), user.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
     *
     * @param resources
     */
    private void checkLevel(SysUser resources) {
        Integer currentLevel = Collections.min(roleService.findByUsers_Id(SecurityUtil.getUserId()).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList()));
        Integer optLevel = roleService.findByRoles(resources.getRoles());
        if (currentLevel > optLevel) {
            throw new BadRequestException("角色权限不足");
        }
    }
}
