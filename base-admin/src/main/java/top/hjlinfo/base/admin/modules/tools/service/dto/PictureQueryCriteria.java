package top.hjlinfo.base.admin.modules.tools.service.dto;

import lombok.Data;
import top.hjlinfo.base.common.annotation.Query;

/**
 * sm.ms图床
 *
 * @author sting
 * @date 2019-6-4 09:52:09
 */
@Data
public class PictureQueryCriteria{

    @Query(type = Query.Type.INNER_LIKE)
    private String filename;
    
    @Query(type = Query.Type.INNER_LIKE)
    private String username;
}
