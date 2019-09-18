package top.hjlinfo.base.admin.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.hjlinfo.base.admin.modules.system.domain.SysMenu;

import java.util.List;

public interface MenuDao extends BaseMapper<SysMenu> {
    List<Long> findIdByRoleId(@Param("roleId")Long roleId);

}
