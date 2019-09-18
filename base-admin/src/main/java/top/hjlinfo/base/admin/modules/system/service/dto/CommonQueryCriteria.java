package top.hjlinfo.base.admin.modules.system.service.dto;

import lombok.Data;
import top.hjlinfo.base.common.annotation.Query;

/**
 * 公共查询类
 */
@Data
public class CommonQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String name;
}
