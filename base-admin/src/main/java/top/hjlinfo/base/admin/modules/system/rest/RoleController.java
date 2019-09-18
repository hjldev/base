package top.hjlinfo.base.admin.modules.system.rest;

import cn.hutool.core.lang.Dict;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.hjlinfo.base.admin.modules.logging.aop.log.Log;
import top.hjlinfo.base.admin.modules.system.domain.SysRole;
import top.hjlinfo.base.admin.modules.system.service.RoleService;
import top.hjlinfo.base.admin.modules.system.service.dto.CommonQueryCriteria;
import top.hjlinfo.base.admin.modules.system.service.dto.RoleSmallDTO;
import top.hjlinfo.base.common.exception.BadRequestException;
import top.hjlinfo.base.common.utils.SecurityUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sting
 * @date 2018-12-03
 */
@RestController
@RequestMapping("api")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    private static final String ENTITY_NAME = "role";

    /**
     * 获取单个role
     * @param id
     * @return
     */
    @GetMapping(value = "/roles/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_SELECT')")
    public ResponseEntity getRoles(@PathVariable Long id){
        return new ResponseEntity<>(roleService.findById(id), HttpStatus.OK);
    }

    /**
     * 返回全部的角色，新增用户时下拉选择
     * @return
     */
    @GetMapping(value = "/roles/all")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','USER_ALL','USER_CREATE','USER_EDIT')")
    public ResponseEntity getAll(@PageableDefault(value = 2000, sort = {"level"}, direction = Sort.Direction.ASC) Pageable pageable){
        return new ResponseEntity<>(roleService.queryAll(pageable),HttpStatus.OK);
    }

    @Log("查询角色")
    @GetMapping(value = "/roles")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_SELECT')")
    public ResponseEntity getRoles(CommonQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(roleService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping(value = "/roles/level")
    public ResponseEntity getLevel(){
        List<Integer> levels = roleService.findByUsers_Id(SecurityUtils.getUserId()).stream().map(RoleSmallDTO::getLevel).collect(Collectors.toList());
        return new ResponseEntity<>(Dict.create().set("level", Collections.min(levels)),HttpStatus.OK);
    }

    @Log("新增角色")
    @PostMapping(value = "/roles")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_CREATE')")
    public ResponseEntity create(@Validated @RequestBody SysRole resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity<>(roleService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改角色")
    @PutMapping(value = "/roles")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_EDIT')")
    public ResponseEntity update(@RequestBody SysRole resources){
        roleService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("修改角色权限")
    @PutMapping(value = "/roles/permission")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_EDIT')")
    public ResponseEntity updatePermission(@RequestBody SysRole resources){
        roleService.updatePermission(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("修改角色菜单")
    @PutMapping(value = "/roles/menu")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_EDIT')")
    public ResponseEntity updateMenu(@RequestBody SysRole resources){
        roleService.updateMenu(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除角色")
    @DeleteMapping(value = "/roles/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ROLES_ALL','ROLES_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        roleService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/rolesByUserId/{userId}")
    public ResponseEntity getRolesByUserId(@PathVariable Long userId){
        List<RoleSmallDTO> data = roleService.findByUsers_Id(userId);
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
}
