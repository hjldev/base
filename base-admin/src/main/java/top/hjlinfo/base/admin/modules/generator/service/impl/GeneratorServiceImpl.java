package top.hjlinfo.base.admin.modules.generator.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.hjlinfo.base.admin.modules.generator.domain.GenConfig;
import top.hjlinfo.base.admin.modules.generator.domain.vo.ColumnInfo;
import top.hjlinfo.base.admin.modules.generator.domain.vo.TableInfo;
import top.hjlinfo.base.admin.modules.generator.service.GeneratorService;
import top.hjlinfo.base.admin.modules.generator.utils.GenUtil;
import top.hjlinfo.base.common.exception.BadRequestException;
import top.hjlinfo.base.common.utils.PageUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sting
 * @date 2019-01-02
 */
@Service
@RequiredArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    @Override
    public Object getTables(String name, int[] startEnd) {
        StringBuilder sql = new StringBuilder("select table_name tableName,create_time createTime from information_schema.tables where table_schema = (select database()) ");
        if(!ObjectUtils.isEmpty(name)){
            sql.append("and table_name like '%").append(name).append("%' ");
        }
        sql.append("order by table_name");
        sql.append(" limit ");
        sql.append(startEnd[0]);
        sql.append(",");
        sql.append(startEnd[1]-startEnd[0]);

        List<Map<String, Object>> tables = SqlRunner.db().selectList(sql.toString());

        List<TableInfo> tableInfos = new ArrayList<>();
        for (Map<String, Object> table : tables) {
            tableInfos.add(new TableInfo(table.get("tableName"),table.get("createTime")));
        }

        Map<String, Object> mapCount = SqlRunner.db().selectOne("SELECT COUNT(*) as count from information_schema.tables where table_schema = (select database())");
        return PageUtil.toPage(tableInfos,mapCount.get("count"));
    }

    @Override
    public Object getColumns(String name) {
        StringBuilder sql = new StringBuilder("select column_name, is_nullable, data_type, column_comment, column_key, extra from information_schema.columns where ");
        if(!ObjectUtils.isEmpty(name)){
            sql.append("table_name = '").append(name).append("' ");
        }
        sql.append("and table_schema = (select database()) order by ordinal_position");

        List<Map<String, Object>> columns = SqlRunner.db().selectList(sql.toString());

        List<ColumnInfo> columnInfos = new ArrayList<>();
        for (Map<String, Object> column : columns) {
            columnInfos.add(new ColumnInfo(column.get("column_name"),column.get("is_nullable"),column.get("data_type"),column.get("column_comment"),column.get("column_key"),column.get("extra"),null,"true"));
        }
        return PageUtil.toPage(columnInfos,columnInfos.size());
    }

    @Override
    public void generator(List<ColumnInfo> columnInfos, GenConfig genConfig, String tableName) {
        if(genConfig.getId() == null){
            throw new BadRequestException("请先配置生成器");
        }
        try {
            GenUtil.generatorCode(columnInfos,genConfig,tableName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
