package com.stardust.renai.survey.controllers;

import com.stardust.renai.survey.common.OSSBucketAccessor;
import com.stardust.renai.survey.common.StorageAccessor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@CrossOrigin("*")
@RestController
@RequestMapping({"/api/pictures"})
@EnableAutoConfiguration
public class PictureController {
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.OPTIONS})
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        if(!file.isEmpty()) {
            StorageAccessor accessor = new OSSBucketAccessor();
            String fileType=file.getOriginalFilename();
            if (fileType.contains(".")) {
                String[] parts = fileType.split("\\.");
                fileType = parts[parts.length - 1];
            }
            return accessor.put("survey-attachments",  UUID.randomUUID() + "." + fileType ,file.getInputStream());
        }
        return null;
    }
}
