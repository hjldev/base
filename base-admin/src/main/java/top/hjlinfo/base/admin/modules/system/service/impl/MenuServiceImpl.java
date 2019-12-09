package top.hjlinfo.base.admin.modules.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.hjlinfo.base.admin.modules.system.dao.MenuDao;
import top.hjlinfo.base.admin.modules.system.domain.SysMenu;
import top.hjlinfo.base.admin.modules.system.domain.vo.MenuMetaVo;
import top.hjlinfo.base.admin.modules.system.domain.vo.MenuVo;
import top.hjlinfo.base.admin.modules.system.service.MenuService;
import top.hjlinfo.base.admin.modules.system.service.dto.CommonQueryCriteria;
import top.hjlinfo.base.admin.modules.system.service.dto.MenuDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.RoleSmallDTO;
import top.hjlinfo.base.admin.modules.system.service.mapper.MenuMapper;
import top.hjlinfo.base.common.exception.BadRequestException;
import top.hjlinfo.base.common.exception.EntityExistException;
import top.hjlinfo.base.common.utils.QueryHelp;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    private final MenuDao menuDao;

    @Override
    public List<MenuDTO> queryAll(CommonQueryCriteria criteria) {
        List<SysMenu> data = menuDao.selectList(QueryHelp.<SysMenu, CommonQueryCriteria>buildWrapper(criteria));
        return menuMapper.toDto(data);
    }

    @Override
    public MenuDTO findById(long id) {
        SysMenu menu = menuDao.selectById(id);
        return menuMapper.toDto(menu);
    }

    @Override
    public List<MenuDTO> findByRoles(List<RoleSmallDTO> roles) {
        Set<SysMenu> menus = new LinkedHashSet<>();
        for (RoleSmallDTO role : roles) {
            List<SysMenu> menus1 = menuDao.selectList(new QueryWrapper<SysMenu>().in("id", menuDao.findIdByRoleId(role.getId())).orderByAsc("sort"));
            menus.addAll(menus1);
        }
        return menus.stream().map(menuMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<MenuDTO> findByRole(RoleSmallDTO role) {
        List<Long> menuIds = menuDao.findIdByRoleId(role.getId());
        if (CollectionUtil.isEmpty(menuIds)) {
            return new ArrayList<>();
        }
        List<SysMenu> menus = menuDao.selectList(new QueryWrapper<SysMenu>().in("id", menuIds).orderByAsc("sort"));
        return menus.stream().map(menuMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public MenuDTO create(SysMenu resources) {
        if(resources.getIFrame()){
            if (!(resources.getPath().toLowerCase().startsWith("http://")||resources.getPath().toLowerCase().startsWith("https://"))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        menuDao.insert(resources);
        return menuMapper.toDto(resources);
    }

    @Override
    public void update(SysMenu resources) {
        if(resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("上级不能为自己");
        }
        if(resources.getIFrame()){
            if (!(resources.getPath().toLowerCase().startsWith("http://")||resources.getPath().toLowerCase().startsWith("https://"))) {
                throw new BadRequestException("外链必须以http://或者https://开头");
            }
        }
        SysMenu menu = menuDao.selectById(resources.getId());
        SysMenu menu1 = menuDao.selectOne(new QueryWrapper<SysMenu>().eq("name", resources.getName()));

        if(menu1 != null && !menu1.getId().equals(menu.getId())){
            throw new EntityExistException(SysMenu.class,"name",resources.getName());
        }
        menu.setName(resources.getName());
        menu.setComponent(resources.getComponent());
        menu.setPath(resources.getPath());
        menu.setIcon(resources.getIcon());
        menu.setIFrame(resources.getIFrame());
        menu.setPid(resources.getPid());
        menu.setSort(resources.getSort());
        menuDao.updateById(menu);
    }

    @Override
    public void delete(Long id) {
        menuDao.deleteById(id);
    }

    @Override
    public Object getMenuTree(List<SysMenu> menus) {
        List<Map<String,Object>> list = new LinkedList<>();
        menus.forEach(menu -> {
                    if (menu!=null){
                        List<SysMenu> menuList = menuDao.selectList(new QueryWrapper<SysMenu>().eq("pid", menu.getId()));
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",menu.getId());
                        map.put("label",menu.getName());
                        if(menuList!=null && menuList.size()!=0){
                            map.put("children",getMenuTree(menuList));
                        }
                        list.add(map);
                    }
                }
        );
        return list;
    }

    @Override
    public List<SysMenu> findByPid(long pid) {
        return menuDao.selectList(new QueryWrapper<SysMenu>().eq("pid", pid));
    }

    @Override
    public Map buildTree(List<MenuDTO> menuDTOS) {
        List<MenuDTO> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (MenuDTO menuDTO : menuDTOS) {
            if (menuDTO.getPid() == 0) {
                trees.add(menuDTO);
            }
            for (MenuDTO it : menuDTOS) {
                if (it.getPid().equals(menuDTO.getId())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        if(trees.size() == 0){
            trees = menuDTOS.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        map.put("content",trees);
        map.put("totalElements",menuDTOS!=null?menuDTOS.size():0);
        return map;
    }

    @Override
    public List<MenuVo> buildMenus(List<MenuDTO> menuDTOS) {
        List<MenuVo> list = new LinkedList<>();
        menuDTOS.forEach(menuDTO -> {
            if (menuDTO!=null){
                List<MenuDTO> menuDTOList = menuDTO.getChildren();
                MenuVo menuVo = new MenuVo();
                menuVo.setName(menuDTO.getName());
                menuVo.setPath(menuDTO.getPath());

                // 如果不是外链
                if(!menuDTO.getIFrame()){
                    if(menuDTO.getPid().equals(0L)){
                        //一级目录需要加斜杠，不然访问 会跳转404页面
                        menuVo.setPath("/" + menuDTO.getPath());
                        menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent())?"Layout":menuDTO.getComponent());
                    }else if(!StrUtil.isEmpty(menuDTO.getComponent())){
                        menuVo.setComponent(menuDTO.getComponent());
                    }
                }
                menuVo.setMeta(new MenuMetaVo(menuDTO.getName(),menuDTO.getIcon()));
                if(menuDTOList!=null && menuDTOList.size()!=0){
                    menuVo.setAlwaysShow(true);
                    menuVo.setRedirect("noredirect");
                    menuVo.setChildren(buildMenus(menuDTOList));
                    // 处理是一级菜单并且没有子菜单的情况
                } else if(menuDTO.getPid().equals(0L)){
                    MenuVo menuVo1 = new MenuVo();
                    menuVo1.setMeta(menuVo.getMeta());
                    // 非外链
                    if(!menuDTO.getIFrame()){
                        menuVo1.setPath("index");
                        menuVo1.setName(menuVo.getName());
                        menuVo1.setComponent(menuVo.getComponent());
                    } else {
                        menuVo1.setPath(menuDTO.getPath());
                    }
                    menuVo.setName(null);
                    menuVo.setMeta(null);
                    menuVo.setComponent("Layout");
                    List<MenuVo> list1 = new ArrayList<MenuVo>();
                    list1.add(menuVo1);
                    menuVo.setChildren(list1);
                }
                list.add(menuVo);
            }
        }
        );
        return list;
    }

    @Override
    public SysMenu findOne(Long id) {
        return menuDao.selectById(id);
    }
}
