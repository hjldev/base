package top.hjlinfo.base.admin.modules.tools.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 上传成功后，存储结果
 * @author sting
 * @date 2018-12-31
 */
@Data
@Table(name = "qiniu_content")
public class QiniuContent implements Serializable {

    @TableId
    private Long id;

    /**
     * 文件名，如qiniu.jpg
     */
    @TableField(value = "name")
    private String key;

    /**
     * 空间名
     */
    private String bucket;

    /**
     * 大小
     */
    private String size;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 空间类型：公开/私有
     */
    private String type = "公开";

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
}
