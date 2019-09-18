package top.hjlinfo.base.admin.modules.system.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import top.hjlinfo.base.admin.modules.system.domain.SysUser;

public interface UserDao extends BaseMapper<SysUser> {
    IPage<SysUser> selectUsers(Page page, @Param(Constants.WRAPPER) Wrapper wrapper);
    SysUser findUserInfoByUsername(@Param("username") String username);
    SysUser findUserInfoByEmail(@Param("email")String email);
}
