package top.hjlinfo.base.admin.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysRolesDepts implements Serializable {

    @TableField(value = "role_id")
    private Long roleId;

    @TableField(value = "dept_id")
    private Long deptId;
}
