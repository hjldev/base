package top.hjlinfo.base.admin.modules.generator.controller;

import lombok.RequiredArgsConstructor;
import top.hjlinfo.base.admin.modules.generator.domain.GenConfig;
import top.hjlinfo.base.admin.modules.generator.service.GenConfigService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author sting
 * @date 2019-01-14
 */
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class GenConfigController {

    private final GenConfigService genConfigService;

    /**
     * 查询生成器配置
     * @return
     */
    @GetMapping(value = "/genConfig")
    public ResponseEntity get(){
        return new ResponseEntity(genConfigService.find(), HttpStatus.OK);
    }

    @PutMapping(value = "/genConfig")
    public ResponseEntity genConfig(@Validated @RequestBody GenConfig genConfig){
        return new ResponseEntity(genConfigService.update(genConfig),HttpStatus.OK);
    }
}
