package top.hjlinfo.base.admin.modules.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author sting
 * @date 2019-04-10
 */
@Data
@TableName(value = "sys_dict")
public class SysDict implements Serializable {

    @TableId
    private Long id;

    /**
     * 字典名称
     */
    @NotBlank
    private String name;

    /**
     * 描述
     */
    private String remark;

}