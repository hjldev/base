package top.hjlinfo.base.admin.modules.logging.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.hjlinfo.base.admin.modules.logging.domain.SysLog;

public interface LogDao extends BaseMapper<SysLog> {

    Long findIp(@Param("date1") String date1, @Param("date2") String date2);

    String findExceptionById(@Param("id") Long id);

}
