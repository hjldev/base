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
 * @date 2018-12-17
 */
@Data
@TableName(value = "sys_menu")
public class SysMenu implements Serializable {

    @TableId
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Long sort;

    private String path;

    private String component;

    private String icon;

    /**
     * 上级菜单ID
     */
    private Long pid;

    /**
     * 是否为外链 true/false
     */
    @TableField(value = "i_frame")
    private Boolean iFrame;

    @TableField(value = "create_time")
    private Date createTime;

}
