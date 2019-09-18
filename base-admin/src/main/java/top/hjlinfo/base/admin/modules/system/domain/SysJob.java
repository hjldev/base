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
 * @date 2019-03-29
 */
@Data
@TableName(value = "sys_job")
public class SysJob implements Serializable {

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
    private Long sort;

    /**
     * 状态
     */
    @NotNull
    private Boolean enabled;

    @TableField(value = "dept_id")
    private Long deptId;

    /**
     * 创建日期
     */
    @TableField(value = "create_time")
    private Date createTime;

}