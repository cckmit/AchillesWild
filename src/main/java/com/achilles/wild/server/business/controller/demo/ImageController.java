package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.common.aop.log.annotation.IgnoreParams;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(value = "/image")
public class ImageController {

    private final static Logger LOG = LoggerFactory.getLogger(ImageController.class);

    @PostMapping("/upload")
    @IgnoreParams
    public String upload(MultipartFile file){

        if (file.getSize()>30*1024*1024){
            return "太大";
        }

        if (file.getSize()<=500*1024){
            return "太小，不用压缩";
        }

        File uploadFile=new File("D:/image/");

        String path = thumbnails( uploadFile, file, 200);

        return path;
    }


    private String thumbnails(File uploadFile,MultipartFile file,int width){
        if(!uploadFile.exists()) {
            uploadFile.mkdirs();
        }

        String des=uploadFile+file.getOriginalFilename();

        try {
            Thumbnails.of(file.getInputStream()).width(width).outputFormat("jpg").toFile(des);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return des;
    }

    private String thumbnails(File uploadFile,MultipartFile file,int scale,int quality){
        if(!uploadFile.exists()) {
            uploadFile.mkdirs();
        }

        String des=uploadFile+file.getOriginalFilename();

        try {
            Thumbnails.of(file.getInputStream()).scale(scale).outputQuality(quality).outputFormat("jpg").toFile(des);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return des;
    }

}
