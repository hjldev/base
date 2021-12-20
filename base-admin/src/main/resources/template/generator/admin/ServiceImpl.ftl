package ${package}.service.impl;

import ${package}.domain.${className};
<#if columns??>
    <#list columns as column>
        <#if column.columnKey = 'UNI'>
            <#if column_index = 1>
import me.zhengjie.exception.EntityExistException;
            </#if>
        </#if>
    </#list>
</#if>
import top.hjlinfo.base.common.utils.ValidationUtil;
import ${package}.dao.${className}Dao;
import ${package}.service.${className}Service;
import ${package}.service.dto.${className}DTO;
import ${package}.service.dto.${className}QueryCriteria;
import ${package}.service.mapper.${className}Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
<#if !auto && pkColumnType = 'Long'>
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
</#if>
<#if !auto && pkColumnType = 'String'>
import cn.hutool.core.util.IdUtil;
</#if>
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.domain.Pageable;
import top.hjlinfo.base.common.utils.PageUtil;
import top.hjlinfo.base.common.utils.QueryHelp;
import lombok.RequiredArgsConstructor;

/**
* @author ${author}
* @date ${date}
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ${className}ServiceImpl implements ${className}Service {

    private final ${className}Dao ${changeClassName}Dao;

    private final ${className}Mapper ${changeClassName}Mapper;

    @Override
    public Object queryAll(${className}QueryCriteria criteria, Pageable pageable){
        Page<${className}> page = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<${className}> data = ${changeClassName}Dao.selectPage(page, QueryHelp.<${className}, ${className}QueryCriteria>buildWrapper(criteria));
        return PageUtil.toPage(data);
    }

    @Override
    public Object queryAll(${className}QueryCriteria criteria){
        return ${changeClassName}Mapper.toDto(${changeClassName}Dao.selectList(QueryHelp.<${className}, ${className}QueryCriteria>buildWrapper(criteria)));
    }

    @Override
    public ${className}DTO findById(${pkColumnType} Id) {
        ${className} ${changeClassName} = ${changeClassName}Dao.selectById(Id);
        return ${changeClassName}Mapper.toDto(${changeClassName});
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${className}DTO create(${className} resources) {
        ${changeClassName}Dao.insert(resources);
        return ${changeClassName}Mapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(${className} resources) {
        ${changeClassName}Dao.updateById(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        ${changeClassName}Dao.deleteById(id);
    }
}