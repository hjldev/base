package top.hjlinfo.base.admin.modules.logging.service.mapper;

import top.hjlinfo.base.admin.modules.logging.domain.SysLog;
import top.hjlinfo.base.common.mapper.EntityMapper;
import top.hjlinfo.base.admin.modules.logging.service.dto.LogErrorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author sting
 * @date 2019-5-22
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogErrorMapper extends EntityMapper<LogErrorDTO, SysLog> {

}