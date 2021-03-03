package top.hjlinfo.base.admin.modules.logging.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.hjlinfo.base.common.annotation.Log;
import top.hjlinfo.base.admin.modules.logging.dao.LogDao;
import top.hjlinfo.base.admin.modules.logging.domain.SysLog;
import top.hjlinfo.base.admin.modules.logging.service.LogService;
import top.hjlinfo.base.admin.modules.logging.service.dto.LogQueryCriteria;
import top.hjlinfo.base.admin.modules.logging.service.mapper.LogErrorMapper;
import top.hjlinfo.base.admin.modules.logging.service.mapper.LogSmallMapper;
import top.hjlinfo.base.common.utils.PageUtil;
import top.hjlinfo.base.common.utils.QueryHelp;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author sting
 * @date 2018-11-24
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@AllArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogDao logDao;

    private final LogErrorMapper logErrorMapper;

    private final LogSmallMapper logSmallMapper;

    private final String LOGINPATH = "login";

    @Override
    public Object queryAll(LogQueryCriteria criteria, Pageable pageable){
        Page<SysLog> page = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        for (Sort.Order order : pageable.getSort()) {
            if (order.getDirection().isAscending()) {
                page.addOrder(OrderItem.asc(order.getProperty()));
            } else {
                page.addOrder(OrderItem.desc(order.getProperty()));
            }
        }
        IPage<SysLog> data = logDao.selectPage(page, QueryHelp.<SysLog, LogQueryCriteria>buildWrapper(criteria));
        return PageUtil.toPage(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String ip, ProceedingJoinPoint joinPoint, SysLog log){

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log aopLog = method.getAnnotation(Log.class);

        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";

        String params = "{";
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();


        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params += " " + argNames[i] + ": " + argValues[i];
            }
        }

        // 获取IP地址
        log.setRequestIp(ip);

        if(LOGINPATH.equals(signature.getName())){
            try {
                JSONObject jsonObject = new JSONObject(argValues[0]);
                username = jsonObject.get("username").toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params + " }");
        log.setCreateTime(new Date());
        logDao.insert(log);
    }

    @Override
    public Object findByErrDetail(Long id) {
        return Dict.create().set("exception",logDao.findExceptionById(id));
    }
}
