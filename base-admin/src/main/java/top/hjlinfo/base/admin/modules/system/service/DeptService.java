package top.hjlinfo.base.admin.modules.system.service;

import top.hjlinfo.base.admin.modules.system.domain.SysDept;
import top.hjlinfo.base.admin.modules.system.service.dto.DeptDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import top.hjlinfo.base.admin.modules.system.service.dto.DeptQueryCriteria;

import java.util.List;
import java.util.Set;

/**
* @author sting
* @date 2019-03-25
*/
@CacheConfig(cacheNames = "dept")
public interface DeptService {

    /**
     * queryAll
     * @param criteria
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    List<DeptDTO> queryAll(DeptQueryCriteria criteria);
    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    DeptDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    DeptDTO create(SysDept resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(SysDept resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * buildTree
     * @param deptDTOS
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    Object buildTree(List<DeptDTO> deptDTOS);

    /**
     * findByPid
     * @param pid
     * @return
     */
    @Cacheable(keyGenerator = "keyGenerator")
    List<SysDept> findByPid(long pid);

    List<SysDept> findByRoleIds(Long id);

    List<Long> findIdByRoleId(Long roleId);
}