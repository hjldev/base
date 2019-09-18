package top.hjlinfo.base.admin.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author sting
 * @date 2018-11-22
 */
@Data
@TableName(value = "sys_user")
public class SysUser implements Serializable {

    @TableId
    private Long id;

    @NotBlank
    private String username;

    private String avatar;

    @NotBlank
    @Pattern(regexp = "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}", message = "格式错误")
    private String email;

    @NotBlank
    private String phone;

    @NotNull
    private Boolean enabled;

    private String password;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "last_password_reset_time")
    private Date lastPasswordResetTime;

    @TableField(value = "dept_id")
    private Long deptId;

    @TableField(value = "job_id")
    private Long jobId;

    @TableField(exist = false)
    private String jobName;

    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private List<SysRole> roles;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", lastPasswordResetTime=" + lastPasswordResetTime +
                '}';
    }

}