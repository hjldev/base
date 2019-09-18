package ${package}.rest;

import top.hjlinfo.base.admin.modules.logging.aop.log.Log;
import lombok.RequiredArgsConstructor;
import ${package}.domain.${className};
import ${package}.service.${className}Service;
import ${package}.service.dto.${className}QueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
* @author ${author}
* @date ${date}
*/
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ${className}Controller {

    private final ${className}Service ${changeClassName}Service;

    @Log("查询${className}")
    @GetMapping(value = "/${changeClassName}")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_SELECT')")
    public ResponseEntity get${className}s(${className}QueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(${changeClassName}Service.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("新增${className}")
    @PostMapping(value = "/${changeClassName}")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_CREATE')")
    public ResponseEntity create(@Validated @RequestBody ${className} resources){
        return new ResponseEntity<>(${changeClassName}Service.create(resources),HttpStatus.CREATED);
    }

    @Log("修改${className}")
    @PutMapping(value = "/${changeClassName}")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_EDIT')")
    public ResponseEntity update(@Validated @RequestBody ${className} resources){
        ${changeClassName}Service.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除${className}")
    @DeleteMapping(value = "/${changeClassName}/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','${upperCaseClassName}_ALL','${upperCaseClassName}_DELETE')")
    public ResponseEntity delete(@PathVariable Long id){
        ${changeClassName}Service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}