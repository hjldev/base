package top.hjlinfo.base.admin.modules.system.controller;

import lombok.AllArgsConstructor;
import top.hjlinfo.base.common.annotation.Log;
import top.hjlinfo.base.admin.modules.system.service.dto.CommonQueryCriteria;
import top.hjlinfo.base.admin.modules.system.service.dto.RoleSmallDTO;
import top.hjlinfo.base.common.exception.BadRequestException;
import top.hjlinfo.base.admin.modules.system.domain.SysMenu;
import top.hjlinfo.base.admin.modules.system.service.MenuService;
import top.hjlinfo.base.admin.modules.system.service.RoleService;
import top.hjlinfo.base.admin.modules.system.service.UserService;
import top.hjlinfo.base.admin.modules.system.service.dto.MenuDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.UserDTO;
import top.hjlinfo.base.admin.modules.system.service.mapper.MenuMapper;
import top.hjlinfo.base.common.utils.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sting
 * @date 2018-12-03
 */
@RestController
@RequestMapping("api")
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;

    private final UserService userService;

    private final RoleService roleService;

    private final MenuMapper menuMapper;

    private static final String ENTITY_NAME = "menu";

    /**
     * 构建前端路由所需要的菜单
     * @return
     */
    @GetMapping(value = "/menus/build")
    public ResponseEntity buildMenus(){
        UserDTO user = userService.findByName(SecurityUtil.getUsername());
        List<MenuDTO> menuDTOList = menuService.findByRoles(roleService.findByUsers_Id(user.getId()));
        List<MenuDTO> menuDTOTree = (List<MenuDTO>) menuService.buildTree(menuDTOList).get("content");
        return new ResponseEntity<>(menuService.buildMenus(menuDTOTree),HttpStatus.OK);
    }

    /**
     * 返回全部的菜单
     * @return
     */
    @GetMapping(value = "/menus/tree")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE','MENU_EDIT','ROLES_SELECT','ROLES_ALL')")
    public ResponseEntity getMenuTree(){
        return new ResponseEntity<>(menuService.getMenuTree(menuService.findByPid(0L)),HttpStatus.OK);
    }

    @Log("查询菜单")
    @GetMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity getMenus(CommonQueryCriteria criteria){
        List<MenuDTO> menuDTOList = menuService.queryAll(criteria);
        return new ResponseEntity<>(menuService.buildTree(menuDTOList),HttpStatus.OK);
    }

    @Log("新增菜单")
    @PostMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_CREATE')")
    public ResponseEntity create(@Validated @RequestBody SysMenu resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(menuService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @PutMapping(value = "/menus")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_EDIT')")
    public ResponseEntity update(@RequestBody SysMenu resources){
        menuService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @DeleteMapping(value = "/menus/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        List<SysMenu> menuList = menuService.findByPid(id);

        // 特殊情况，对级联删除进行处理
        for (SysMenu menu : menuList) {
            roleService.untiedMenu(menu);
            menuService.delete(menu.getId());
        }
        roleService.untiedMenu(menuService.findOne(id));
        menuService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/menusByRole")
    @PreAuthorize("hasAnyRole('ADMIN','MENU_ALL','MENU_SELECT')")
    public ResponseEntity getMenusByRole(@RequestBody RoleSmallDTO roleSmallDTO) {
        List<MenuDTO> menuDTOList = menuService.findByRole(roleSmallDTO);
        return new ResponseEntity<>(menuDTOList, HttpStatus.OK);
    }
}
