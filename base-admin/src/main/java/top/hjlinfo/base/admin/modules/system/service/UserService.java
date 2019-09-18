package top.hjlinfo.base.admin.modules.system.service;

import org.springframework.data.domain.Pageable;
import top.hjlinfo.base.admin.modules.system.domain.SysUser;
import top.hjlinfo.base.admin.modules.system.service.dto.UserDTO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import top.hjlinfo.base.admin.modules.system.service.dto.UserQueryCriteria;

/**
 * @author sting
 * @date 2018-11-23
 */
@CacheConfig(cacheNames = "user")
public interface UserService {

    @Cacheable(keyGenerator = "keyGenerator")
    Object queryAll(UserQueryCriteria criteria, Pageable pageable);

    /**
     * get
     * @param id
     * @return
     */
    @Cacheable(key = "#p0")
    UserDTO findById(long id);

    /**
     * create
     * @param resources
     * @return
     */
    @CacheEvict(allEntries = true)
    UserDTO create(SysUser resources);

    /**
     * update
     * @param resources
     */
    @CacheEvict(allEntries = true)
    void update(SysUser resources);

    /**
     * delete
     * @param id
     */
    @CacheEvict(allEntries = true)
    void delete(Long id);

    /**
     * findByName
     * @param userName
     * @return
     */
    @Cacheable(key = "'loadUserByUsername:'+#p0")
    UserDTO findByName(String userName);

    /**
     * 修改密码
     * @param username
     * @param encryptPassword
     */
    @CacheEvict(allEntries = true)
    void updatePass(String username, String encryptPassword);

    /**
     * 修改头像
     * @param username
     * @param url
     */
    @CacheEvict(allEntries = true)
    void updateAvatar(String username, String url);

    /**
     * 修改邮箱
     * @param username
     * @param email
     */
    @CacheEvict(allEntries = true)
    void updateEmail(String username, String email);
}
