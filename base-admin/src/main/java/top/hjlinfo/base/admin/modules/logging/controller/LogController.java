package top.hjlinfo.base.admin.modules.logging.controller;

import lombok.RequiredArgsConstructor;
import top.hjlinfo.base.admin.modules.logging.service.LogService;
import top.hjlinfo.base.admin.modules.logging.service.dto.LogQueryCriteria;
import top.hjlinfo.base.common.utils.SecurityUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sting
 * @date 2018-11-24
 */
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping(value = "/logs")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getLogs(LogQueryCriteria criteria, Pageable pageable){
        criteria.setLogType("INFO");
        return new ResponseEntity(logService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/user")
    public ResponseEntity getUserLogs(LogQueryCriteria criteria, Pageable pageable){
        criteria.setLogType("INFO");
        criteria.setUsername(SecurityUtil.getUsername());
        return new ResponseEntity(logService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/error")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getErrorLogs(LogQueryCriteria criteria, Pageable pageable){
        criteria.setLogType("ERROR");
        return new ResponseEntity(logService.queryAll(criteria,pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/error/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity getErrorLogs(@PathVariable Long id){
        return new ResponseEntity(logService.findByErrDetail(id), HttpStatus.OK);
    }
}
