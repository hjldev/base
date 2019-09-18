package top.hjlinfo.base.admin.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
* @author sting
* @date 2019-03-25
*/
@Data
@TableName(value = "sys_dept")
public class SysDept implements Serializable {

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 名称
     */
    @NotBlank
    private String name;

    @NotNull
    private Boolean enabled;

    /**
     * 上级部门
     */
    @NotNull
    private Long pid;

    @TableField(value = "create_time")
    private Date createTime;

}