package top.hjlinfo.base.admin.modules.tools.service.dto;

import lombok.Data;
import top.hjlinfo.base.common.annotation.Query;

/**
 * @author sting
 * @date 2019-6-4 09:54:37
 */
@Data
public class QiniuQueryCriteria{

    @Query(type = Query.Type.INNER_LIKE)
    private String key;
}
