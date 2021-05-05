package com.achilles.wild.server.business.controller.demo;

import com.achilles.wild.server.common.aop.log.annotation.IgnoreParams;
import com.achilles.wild.server.model.request.demo.ImageRequest;
import com.achilles.wild.server.model.response.DataResult;
import com.achilles.wild.server.tool.file.FileUtil;
import com.achilles.wild.server.tool.file.ImageUtil;
import com.achilles.wild.server.tool.generate.encrypt.MD5Util;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping(value = "/image")
public class ImageController {

    private final static Logger log = LoggerFactory.getLogger(ImageController.class);

    static String srcPath = "C:\\Users\\Achilles\\Desktop\\photo\\test\\10028.jpg";

    @Autowired
    HttpServletResponse response;

    @ResponseBody
    @GetMapping("/downloadFromBase64")
    @IgnoreParams
    public void downloadFromBase64(@RequestBody ImageRequest request) {

        String fileName = null;
        try {
            fileName = URLEncoder.encode(GenerateUniqueUtil.getUuId(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", "attachment;filename="+fileName+".jpg");

        String base64 = request.getBase64();
        InputStream inputStream = FileUtil.base64ToInputStream(base64);
        OutputStream outputStream = null;
        try{
            outputStream = response.getOutputStream();
            byte[] buff = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }

    @PostMapping("/uploadBase64")
    @IgnoreParams
    public DataResult uploadBase64(@RequestBody ImageRequest request) {

        String base64 = request.getBase64();
        InputStream inputStream = FileUtil.base64ToInputStream(base64);

        ByteArrayInputStream[] byteArrayInputStreams = FileUtil.cloneInputStream(inputStream,2);
        InputStream trimInputStream = ImageUtil.trimBySizeLimit(byteArrayInputStreams[0],1,300);
        log.info("after trimBySizeLimit");
        FileUtil.toFile(trimInputStream,"C:\\Users\\Achilles\\Desktop\\photo\\base64\\"+ GenerateUniqueUtil.getUuId() +".jpg");
        log.info("after toFile");
        String key = MD5Util.getAddSalt(byteArrayInputStreams[1]);

        return DataResult.success(key);
    }

    @PostMapping("/upload")
    @IgnoreParams
    public String upload(MultipartFile file) {

        InputStream inputStream = null;
        String key = null;
        try {
            inputStream = file.getInputStream();
            ByteArrayInputStream[] byteArrayInputStreams = FileUtil.cloneInputStream(inputStream,2);
            log.info("before trimBySizeLimit");

            InputStream trimInputStream = ImageUtil.trimBySizeLimit(byteArrayInputStreams[0],1,300);
            log.info("after trimBySizeLimit");
            FileUtil.toFile(trimInputStream,"C:\\Users\\Achilles\\Desktop\\photo\\file\\"+ GenerateUniqueUtil.getUuId() +".jpg");
            log.info("after toFile");
            key = MD5Util.getAddSalt(byteArrayInputStreams[1]);
            log.info("after MD5Util");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        return key;
    }

    @PostMapping("/getKey")
    @IgnoreParams
    public String getKey(MultipartFile file){

        InputStream inputStream = null;
        String key = null;
        try {
            inputStream = file.getInputStream();
            key = MD5Util.getAddSalt(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        return key;
    }

    @PostMapping("/getBase64")
    @IgnoreParams
    public String getBase64(MultipartFile file){

        InputStream inputStream = null;
        String base64 = null;
        try {
            inputStream = file.getInputStream();
            base64 = FileUtil.getBase64(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        return base64;
    }


}
