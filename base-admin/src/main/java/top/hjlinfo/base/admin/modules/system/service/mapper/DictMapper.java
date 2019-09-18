package top.hjlinfo.base.admin.modules.system.service.mapper;

import top.hjlinfo.base.common.mapper.EntityMapper;
import top.hjlinfo.base.admin.modules.system.domain.SysDict;
import top.hjlinfo.base.admin.modules.system.service.dto.DictDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author sting
* @date 2019-04-10
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictMapper extends EntityMapper<DictDTO, SysDict> {

}