package top.hjlinfo.base.admin.modules.system.service.dto;

import lombok.Data;
import top.hjlinfo.base.common.annotation.Query;

/**
* @author sting
* @date 2019-04-10
*/
@Data
public class DictDetailQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    @Query(propName = "dict_id")
    private String dictId;
}