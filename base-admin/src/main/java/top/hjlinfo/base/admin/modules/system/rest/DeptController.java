package top.hjlinfo.base.admin.modules.system.rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.hjlinfo.base.admin.config.DataScope;
import top.hjlinfo.base.admin.modules.logging.aop.log.Log;
import top.hjlinfo.base.admin.modules.system.domain.SysDept;
import top.hjlinfo.base.admin.modules.system.service.DeptService;
import top.hjlinfo.base.admin.modules.system.service.dto.DeptDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.DeptQueryCriteria;
import top.hjlinfo.base.common.exception.BadRequestException;

import java.util.List;

/**
* @author sting
* @date 2019-03-25
*/
@RestController
@RequestMapping("api")
@AllArgsConstructor
public class DeptController {

    private final DeptService deptService;
    private final DataScope dataScope;

    private static final String ENTITY_NAME = "dept";

    @Log("查询部门")
    @GetMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity getDepts(DeptQueryCriteria criteria){
        // 数据权限
        criteria.setIds(dataScope.getDeptIds());
        List<DeptDTO> deptDTOS = deptService.queryAll(criteria);
        return new ResponseEntity(deptService.buildTree(deptDTOS),HttpStatus.OK);
    }

    @Log("新增部门")
    @PostMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_CREATE')")
    public ResponseEntity create(@Validated @RequestBody SysDept resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        return new ResponseEntity(deptService.create(resources),HttpStatus.CREATED);
    }

    @Log("修改部门")
    @PutMapping(value = "/dept")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_EDIT')")
    public ResponseEntity update(@RequestBody SysDept resources){
        deptService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除部门")
    @DeleteMapping(value = "/dept/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        deptService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/deptIds/{roleId}")
    @PreAuthorize("hasAnyRole('ADMIN','DEPT_ALL','DEPT_SELECT')")
    public ResponseEntity getDeptIdsByRoleId(@PathVariable Long roleId) {
        List<Long> deptIds = deptService.findIdByRoleId(roleId);
        return new ResponseEntity<>(deptIds, HttpStatus.OK);
    }
}