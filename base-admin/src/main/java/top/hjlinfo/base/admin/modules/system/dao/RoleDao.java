package top.hjlinfo.base.admin.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.hjlinfo.base.admin.modules.system.domain.SysRole;

import java.util.List;

public interface RoleDao extends BaseMapper<SysRole> {
    List<Long> findByMenuId(@Param("menuId")Long menuId);
    List<SysRole> findByUserId(@Param("userId")Long userId);
}
