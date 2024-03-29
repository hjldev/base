package top.hjlinfo.base.admin.modules.tools.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.hjlinfo.base.common.annotation.Log;
import top.hjlinfo.base.admin.modules.tools.domain.Picture;
import top.hjlinfo.base.admin.modules.tools.service.PictureService;
import top.hjlinfo.base.admin.modules.tools.service.dto.PictureQueryCriteria;
import top.hjlinfo.base.admin.modules.security.utils.SecurityUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sting
 * @date 2018/09/20 14:13:32
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PictureController {

    private final PictureService pictureService;


    @Log("查询图片")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_SELECT')")
    @GetMapping(value = "/pictures")
    public ResponseEntity getRoles(PictureQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(pictureService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    /**
     * 上传图片
     * @param file
     * @return
     * @throws Exception
     */
    @Log("上传图片")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_UPLOAD')")
    @PostMapping(value = "/pictures")
    public ResponseEntity upload(@RequestParam MultipartFile file){
        String userName = SecurityUtil.getUsername();
        Picture picture = pictureService.upload(file,userName);
        Map<String, Object> map = new HashMap<>();
        map.put("errno",0);
        map.put("id",picture.getId());
        map.put("data",new String[]{picture.getUrl()});
        return new ResponseEntity(map,HttpStatus.OK);
    }

    /**
     * 删除图片
     * @param id
     * @return
     */
    @Log("删除图片")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_DELETE')")
    @DeleteMapping(value = "/pictures/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        pictureService.delete(pictureService.findById(id));
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 删除多张图片
     * @param ids
     * @return
     */
    @Log("删除图片")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_DELETE')")
    @DeleteMapping(value = "/pictures")
    public ResponseEntity deleteAll(@RequestBody Long[] ids) {
        pictureService.deleteAll(ids);
        return new ResponseEntity(HttpStatus.OK);
    }
}
