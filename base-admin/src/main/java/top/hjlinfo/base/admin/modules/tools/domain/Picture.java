package top.hjlinfo.base.admin.modules.tools.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * sm.ms图床
 *
 * @author sting
 * @date 2018-12-27
 */
@Data
@Table(name = "picture")
public class Picture implements Serializable {

    @TableId
    private Long id;

    private String filename;

    private String url;

    private String size;

    private String height;

    private String width;

    /**
     * delete URl
     */
    @TableField(value = "delete_url")
    private String deleteUrl;

    private String username;

    @TableField(value = "create_time")
    private Date createTime;

    @Override
    public String toString() {
        return "Picture{" +
                "filename='" + filename + '\'' +
                '}';
    }
}
