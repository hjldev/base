package top.hjlinfo.base.admin.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 角色
 * @author sting
 * @date 2018-11-22
 */
@TableName(value = "sys_role")
@Data
public class SysRole implements Serializable {

    @TableId
    private Long id;

    @NotBlank
    private String name;

    // 数据权限类型 全部 、 本级 、 自定义
    @TableField(value = "data_scope")
    private String dataScope = "本级";

    // 数值越小，级别越大
    private Integer level = 3;

    private String remark;

    @TableField(exist = false)
    private List<SysPermission> permissions;

    @TableField(exist = false)
    private List<SysMenu> menus;

    @TableField(exist = false)
    private List<SysDept> depts;

    @TableField(value = "create_time")
    private Date createTime;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", createDateTime=" + createTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRole role = (SysRole) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
