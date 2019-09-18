package top.hjlinfo.base.admin.modules.system.service;

import org.springframework.data.domain.Pageable;
import top.hjlinfo.base.admin.modules.system.domain.SysMenu;
import top.hjlinfo.base.admin.modules.system.domain.SysRole;
import top.hjlinfo.base.admin.modules.system.service.dto.CommonQueryCriteria;
import top.hjlinfo.base.admin.modules.system.service.dto.RoleDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.RoleSmallDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Set;

/**
 * @author sting
 * @date 2018-12-03
 */
@CacheConfig(cacheNames = "role")
public interface RoleService {

    /**
     * queryAll
     * @param pageable
     * @return
     */
    Object queryAll(Pageable pageable);

    /**
     * queryAll
     * @param pageable
     * @param criteria
     * @return
     */
    Object queryAll(CommonQueryCriteria criteria, Pageable pageable);

    /**
     * get
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    RoleDTO findById(long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    RoleDTO create(SysRole resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(SysRole resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * key的名称如有修改，请同步修改 UserServiceImpl 中的 update 方法
     * findByUsers_Id
     * @param id
     * @return
     */
    @Cacheable(key = "'findByUsers_Id:' + #p0")
    List<RoleSmallDTO> findByUsers_Id(Long id);

    @Cacheable(keyGenerator = "keyGenerator")
    Integer findByRoles(List<SysRole> roles);

    /**
     * updatePermission
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void updatePermission(SysRole resources);

    /**
     * updateMenu
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void updateMenu(SysRole resources);

    @CacheEvict(allEntries = true)
    void untiedMenu(SysMenu menu);
}
