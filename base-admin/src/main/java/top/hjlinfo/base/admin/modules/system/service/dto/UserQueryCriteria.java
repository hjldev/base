package top.hjlinfo.base.admin.modules.system.service.dto;

import lombok.Data;
import top.hjlinfo.base.common.annotation.Query;

import java.io.Serializable;
import java.util.Set;

/**
 * @author sting
 * @date 2018-11-23
 */
@Data
public class UserQueryCriteria implements Serializable {

    @Query
    private Long id;

    @Query(propName = "u.dept_id", type = Query.Type.IN, joinName = "dept")
    private Set<Long> deptIds;

    @Query(type = Query.Type.INNER_LIKE)
    private String username;

    @Query(type = Query.Type.INNER_LIKE)
    private String email;

    @Query
    private Boolean enabled;

    private Long deptId;
}
