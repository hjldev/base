package top.hjlinfo.base.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.hjlinfo.base.admin.modules.system.dao.DeptDao;
import top.hjlinfo.base.admin.modules.system.dao.JobDao;
import top.hjlinfo.base.admin.modules.system.domain.SysDept;
import top.hjlinfo.base.admin.modules.system.domain.SysJob;
import top.hjlinfo.base.admin.modules.system.service.JobService;
import top.hjlinfo.base.admin.modules.system.service.dto.DeptDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.JobDTO;
import top.hjlinfo.base.admin.modules.system.service.dto.JobQueryCriteria;
import top.hjlinfo.base.admin.modules.system.service.mapper.DeptMapper;
import top.hjlinfo.base.admin.modules.system.service.mapper.JobMapper;
import top.hjlinfo.base.common.utils.PageUtil;
import top.hjlinfo.base.common.utils.QueryHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sting
 * @date 2019-03-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
@AllArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobMapper jobMapper;
    private final DeptMapper deptMapper;

    private final JobDao jobDao;
    private final DeptDao deptDao;

    @Override
    public Object queryAll(JobQueryCriteria criteria, Pageable pageable) {
        Page<SysJob> page = new Page<>(pageable.getPageNumber() + 1, pageable.getPageSize());
        IPage<SysJob> data = jobDao.selectPage(page, QueryHelp.<SysJob, JobQueryCriteria>buildWrapper(criteria));

        List<JobDTO> jobs = new ArrayList<>();
        for (SysJob job : data.getRecords()) {
            SysDept dept = deptDao.selectById(job.getDeptId());
            DeptDTO deptDTO = deptMapper.toDto(dept);
            JobDTO jobDTO = jobMapper.toDto(job, deptDao.findNameById(job.getDeptId()));
            jobDTO.setDept(deptDTO);
            jobs.add(jobDTO);
        }
        return PageUtil.toPage(jobs, data.getTotal());
    }

    @Override
    public JobDTO findById(Long id) {
        SysJob job = jobDao.selectById(id);
        return jobMapper.toDto(job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JobDTO create(SysJob resources) {
        jobDao.insert(resources);
        return jobMapper.toDto(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysJob resources) {
        jobDao.updateById(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        jobDao.deleteById(id);
    }
}