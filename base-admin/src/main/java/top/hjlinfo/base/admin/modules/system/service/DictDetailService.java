package top.hjlinfo.base.admin.modules.system.service;

import org.springframework.data.domain.Pageable;
import top.hjlinfo.base.admin.modules.system.domain.SysDictDetail;
import top.hjlinfo.base.admin.modules.system.service.dto.DictDetailDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import top.hjlinfo.base.admin.modules.system.service.dto.DictDetailQueryCriteria;

/**
* @author sting
* @date 2019-04-10
*/
@CacheConfig(cacheNames = "dictDetail")
public interface DictDetailService {

    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(DictDetailQueryCriteria criteria, Pageable pageable);

    /**
     * findById
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    DictDetailDTO findById(Long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    DictDetailDTO create(SysDictDetail resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(SysDictDetail resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);
}