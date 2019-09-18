package top.hjlinfo.base.admin.modules.logging.service.dto;

import lombok.Data;
import top.hjlinfo.base.common.annotation.Query;

/**
 * 日志查询类
 * @author sting
 * @date 2019-6-4 09:23:07
 */
@Data
public class LogQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String username;

    @Query(propName = "log_type")
    private String logType;
}
