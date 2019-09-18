package top.hjlinfo.base.admin.modules.system.service.dto;

import lombok.Data;
import top.hjlinfo.base.common.annotation.Query;

import java.io.Serializable;

/**
* @author sting
* @date 2019-04-10
*/
@Data
public class DictDTO implements Serializable {

    private Long id;

    /**
     * 字典名称
     */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    /**
     * 描述
     */
    @Query(type = Query.Type.INNER_LIKE)
    private String remark;
}