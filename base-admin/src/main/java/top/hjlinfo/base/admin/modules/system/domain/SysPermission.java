package top.hjlinfo.base.admin.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author sting
 * @date 2018-12-03
 */
@Data
@Table(name = "sys_permission")
public class SysPermission implements Serializable{

	@TableId
	private Long id;

	@NotBlank
	private String name;

	/**
	 * 上级类目
	 */
	@NotNull
	private Long pid;

	@NotBlank
	private String alias;

	@TableField(value = "create_time")
	private Date createTime;

	@Override
	public String toString() {
		return "Permission{" +
				"id=" + id +
				", name='" + name + '\'' +
				", pid=" + pid +
				", alias='" + alias + '\'' +
				", createTime=" + createTime +
				'}';
	}

}
