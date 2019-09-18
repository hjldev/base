package ${package}.domain;

import lombok.Data;
import javax.persistence.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>
import java.util.Date;
import java.io.Serializable;

/**
* @author ${author}
* @date ${date}
*/
@Data
@TableName(value = "${tableName}")
public class ${className} implements Serializable {
<#if columns??>
    <#list columns as column>

    <#if column.columnComment != ''>
    /**
     * ${column.columnComment}
     */
    </#if>
    <#if column.columnKey = 'PRI'>
    @TableId
    </#if>
    @TableField(value = "${column.columnName}")
    private ${column.columnType} ${column.changeColumnName};
    </#list>
</#if>
}