package com.zl.sneakerweb.controller;

import com.zl.sneakerserver.config.UploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Paths;

/**
 * @Auther: le
 * @Date: 2018/8/29 14:39
 * @Description:
 */
@Controller
@RequestMapping("/image")
public class UploadFile {

    private final ResourceLoader resourceLoader;

    @Autowired
    public UploadFile(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    @Autowired
    private UploadProperties uploadProperties;

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename){
        try {
            String path =Paths.get(uploadProperties.getBasePath()+filename).toString();

            Resource image =resourceLoader.getResource("file:"+path);
            return ResponseEntity.ok(image);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
