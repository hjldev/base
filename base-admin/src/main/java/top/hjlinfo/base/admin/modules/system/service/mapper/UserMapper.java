package top.hjlinfo.base.admin.modules.system.service.mapper;

import top.hjlinfo.base.common.mapper.EntityMapper;
import top.hjlinfo.base.admin.modules.system.domain.SysUser;
import top.hjlinfo.base.admin.modules.system.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author sting
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring",uses = {RoleMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDTO, SysUser> {

}
