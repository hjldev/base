package top.hjlinfo.base.admin.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author sting
 * @date 2018-12-17
 */
@Data
public class MenuDTO implements Serializable {

    private Long id;

    private String name;

    private Long sort;

    private String path;

    private String component;

    private Long pid;

    private Boolean iFrame;

    private String icon;

    private List<MenuDTO> children;

    private Date createTime;
}
