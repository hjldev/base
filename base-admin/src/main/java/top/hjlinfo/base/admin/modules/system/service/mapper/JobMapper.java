package top.hjlinfo.base.admin.modules.system.service.mapper;

import top.hjlinfo.base.common.mapper.EntityMapper;
import top.hjlinfo.base.admin.modules.system.domain.SysJob;
import top.hjlinfo.base.admin.modules.system.service.dto.JobDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
* @author sting
* @date 2019-03-29
*/
@Mapper(componentModel = "spring",uses = {DeptMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends EntityMapper<JobDTO, SysJob> {

    @Mapping(source = "deptSuperiorName", target = "deptSuperiorName")
    JobDTO toDto(SysJob job, String deptSuperiorName);
}