package top.hjlinfo.base.admin.modules.generator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.hjlinfo.base.admin.modules.generator.dao.GenConfigDao;
import top.hjlinfo.base.admin.modules.generator.domain.GenConfig;
import top.hjlinfo.base.admin.modules.generator.service.GenConfigService;

/**
 * @author sting
 * @date 2019-01-14
 */
@Service
@RequiredArgsConstructor
public class GenConfigServiceImpl implements GenConfigService {

    private final GenConfigDao genConfigDao;

    @Override
    public GenConfig find() {
        return genConfigDao.selectById(1L);
    }

    @Override
    public GenConfig update(GenConfig genConfig) {
        genConfig.setId(1L);
        genConfigDao.updateById(genConfig);
        return genConfig;
    }
}
