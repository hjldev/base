package top.hjlinfo.base.admin.modules.system.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.hjlinfo.base.common.annotation.Query;

import java.util.Set;

/**
* @author sting
* @date 2019-6-4 14:49:34
*/
@Data
@NoArgsConstructor
public class JobQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query
    private Boolean enabled;

    @Query(propName = "dept_id")
    private Long deptId;

    @Query(propName = "dept_id", type = Query.Type.IN)
    private Set<Long> deptIds;
}