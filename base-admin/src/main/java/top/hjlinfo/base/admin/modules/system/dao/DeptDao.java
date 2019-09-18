package top.hjlinfo.base.admin.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.hjlinfo.base.admin.modules.system.domain.SysDept;

import java.util.List;

public interface DeptDao extends BaseMapper<SysDept> {
    String findNameById(@Param("id") Long id);
    List<Long> findIdByRoleId(@Param("roleId") Long roleId);

}
