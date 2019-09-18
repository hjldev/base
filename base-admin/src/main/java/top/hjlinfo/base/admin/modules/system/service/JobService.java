package top.hjlinfo.base.admin.modules.system.service;

import org.springframework.data.domain.Pageable;
import top.hjlinfo.base.admin.modules.system.domain.SysJob;
import top.hjlinfo.base.admin.modules.system.service.dto.JobDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import top.hjlinfo.base.admin.modules.system.service.dto.JobQueryCriteria;

/**
* @author sting
* @date 2019-03-29
*/
@CacheConfig(cacheNames = "job")
public interface JobService {

    /**
     * queryAll
     * @param criteria
     * @param pageable
     * @return
     */
    Object queryAll(JobQueryCriteria criteria, Pageable pageable);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    JobDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    JobDTO create(SysJob resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(SysJob resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}