package top.hjlinfo.base.admin.modules.system.service.mapper;

import top.hjlinfo.base.common.mapper.EntityMapper;
import top.hjlinfo.base.admin.modules.system.domain.SysRole;
import top.hjlinfo.base.admin.modules.system.service.dto.RoleSmallDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author sting
 * @date 2019-5-23
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleSmallMapper extends EntityMapper<RoleSmallDTO, SysRole> {

}
