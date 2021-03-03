package top.hjlinfo.base.admin.modules.system.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.hjlinfo.base.admin.config.DataScope;
import top.hjlinfo.base.common.annotation.Log;
import top.hjlinfo.base.admin.modules.system.domain.SysJob;
import top.hjlinfo.base.admin.modules.system.service.JobService;
import top.hjlinfo.base.admin.modules.system.service.dto.JobQueryCriteria;
import top.hjlinfo.base.common.exception.BadRequestException;

/**
* @author sting
* @date 2019-03-29
*/
@RestController
@RequestMapping("api")
@AllArgsConstructor
public class JobController {

    private final JobService jobService;
    private final DataScope dataScope;

    private static final String ENTITY_NAME = "job";

    @Log("查询岗位")
    @GetMapping(value = "/job")
    @PreAuthorize("hasAnyRole('ADMIN','USERJOB_ALL','USERJOB_SELECT','USER_ALL','USER_SELECT')")
    public ResponseEntity getJobs(JobQueryCriteria criteria,
                                  Pageable pageable){
        // 数据权限
        criteria.setDeptIds(dataScope.getDeptIds());
        return new ResponseEntity<>(jobService.queryAll(criteria, pageable),HttpStatus.OK);
    }

    @Log("新增岗位")
    @PostMapping(value = "/job")
    @PreAuthorize("hasAnyRole('ADMIN','USERJOB_ALL','USERJOB_CREATE')")
    public ResponseEntity create(@Validated @RequestBody SysJob resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(jobService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改岗位")
    @PutMapping(value = "/job")
    @PreAuthorize("hasAnyRole('ADMIN','USERJOB_ALL','USERJOB_EDIT')")
    public ResponseEntity update(@RequestBody SysJob resources){
        jobService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除岗位")
    @DeleteMapping(value = "/job/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USERJOB_ALL','USERJOB_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        jobService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
