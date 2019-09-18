package top.hjlinfo.base.admin.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.hjlinfo.base.admin.modules.system.domain.SysPermission;

import java.util.List;

public interface PermissionDao extends BaseMapper<SysPermission> {
    List<String> findNameByRoleId(@Param("roleIds") List<Long> roleIds);
    List<Long> findIdsByRoleId(@Param("roleId")Long roleId);
}
