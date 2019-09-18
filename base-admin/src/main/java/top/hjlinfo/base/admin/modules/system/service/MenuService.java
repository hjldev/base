package top.hjlinfo.base.admin.modules.system.service;

import top.hjlinfo.base.admin.modules.system.domain.SysMenu;
import top.hjlinfo.base.admin.modules.system.service.dto.CommonQueryCriteria;
import top.hjlinfo.base.admin.modules.system.service.dto.MenuDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.RoleSmallDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * @author sting
 * @date 2018-12-17
 */
@CacheConfig(cacheNames = "menu")
public interface MenuService {

    /**
     * queryAll
     * @param criteria
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    List<MenuDTO> queryAll(CommonQueryCriteria criteria);

    /**
     * get
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    MenuDTO findById(long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    MenuDTO create(SysMenu resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(SysMenu resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * permission tree
     * @return
     */
    @Cacheable(key = "'tree'")
    Object getMenuTree(List<SysMenu> menus);

    /**
     * findByPid
     * @param pid
     * @return
     */
    @Cacheable(key = "'pid:'+#p0")
    List<SysMenu> findByPid(long pid);

    /**
     * build Tree
     * @param menuDTOS
     * @return
     */
    Map buildTree(List<MenuDTO> menuDTOS);

    /**
     * findByRoles
     * @param roles
     * @return
     */
    List<MenuDTO> findByRoles(List<RoleSmallDTO> roles);

    List<MenuDTO> findByRole(RoleSmallDTO role);

    /**
     * buildMenus
     * @param byRoles
     * @return
     */
    Object buildMenus(List<MenuDTO> byRoles);

    SysMenu findOne(Long id);
}
