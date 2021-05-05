package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.common.aop.log.annotation.IgnoreParams;
import com.achilles.wild.server.tool.file.FileUtil;
import com.achilles.wild.server.tool.file.ImageUtil;
import com.achilles.wild.server.tool.generate.encrypt.MD5Util;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/image")
public class ImageController {

    private final static Logger log = LoggerFactory.getLogger(ImageController.class);

    static String srcPath = "C:\\Users\\Achilles\\Desktop\\photo\\test\\10028.jpg";

    @PostMapping("/upload")
    @IgnoreParams
    public String upload(MultipartFile file){

        InputStream inputStream = null;
        String key = null;
        try {
            inputStream = file.getInputStream();
            ByteArrayInputStream[] byteArrayInputStreams = FileUtil.cloneInputStream(inputStream,2);
            log.info("before trimBySizeLimit");

            InputStream trimInputStream = ImageUtil.trimBySizeLimit(byteArrayInputStreams[0],0.9,500);
            log.info("after trimBySizeLimit");
            FileUtil.toFile(trimInputStream,"C:\\Users\\Achilles\\Desktop\\photo\\test\\"+ GenerateUniqueUtil.getUuId() +".jpg");
            log.info("after toFile");
            key = MD5Util.getAddSalt(byteArrayInputStreams[1]);
            log.info("after MD5Util");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }


//        if (file.getSize()>30*1024*1024){
//            return "太大";
//        }
//
//        if (file.getSize()<=500*1024){
//            return "太小，不用压缩";
//        }
//
//        File uploadFile=new File("D:/image/");
//
//        String path = thumbnails( uploadFile, file, 200);


//        file.transferTo();
        return key;
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
