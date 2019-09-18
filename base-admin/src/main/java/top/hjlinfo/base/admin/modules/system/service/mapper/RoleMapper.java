package top.hjlinfo.base.admin.modules.system.service.mapper;

import top.hjlinfo.base.common.mapper.EntityMapper;
import top.hjlinfo.base.admin.modules.system.domain.SysRole;
import top.hjlinfo.base.admin.modules.system.service.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author sting
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring", uses = {PermissionMapper.class, MenuMapper.class, DeptMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends EntityMapper<RoleDTO, SysRole> {

}
