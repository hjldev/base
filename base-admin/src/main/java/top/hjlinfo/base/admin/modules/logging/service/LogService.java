package top.hjlinfo.base.admin.modules.logging.service;

import org.springframework.data.domain.Pageable;
import top.hjlinfo.base.admin.modules.logging.domain.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;
import top.hjlinfo.base.admin.modules.logging.service.dto.LogQueryCriteria;

/**
 * @author sting
 * @date 2018-11-24
 */
public interface LogService {

    /**
     * queryAll
     * @param criteria
     * @param pageable
     * @return
     */
    Object queryAll(LogQueryCriteria criteria, Pageable pageable);


    /**
     * 新增日志
     * @param joinPoint
     * @param log
     */
    @Async
    void save(String username, String ip, ProceedingJoinPoint joinPoint, SysLog log);

    /**
     * 查询异常详情
     * @param id
     * @return
     */
    Object findByErrDetail(Long id);
}
