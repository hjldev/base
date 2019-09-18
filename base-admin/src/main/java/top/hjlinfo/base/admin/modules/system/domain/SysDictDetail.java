package top.hjlinfo.base.admin.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
* @author sting
* @date 2019-04-10
*/
@Data
@TableName(value = "sys_dict_detail")
public class SysDictDetail implements Serializable {

    @TableId
    private Long id;

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典值
     */
    private String value;

    /**
     * 排序
     */
    private String sort = "999";

    @TableField(value = "dict_id")
    private Long dictId;

}