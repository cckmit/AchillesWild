package com.achilles.wild.server.business.dao;

import com.achilles.wild.server.StarterApplicationTests;
import com.achilles.wild.server.business.dao.common.TempImageDao;
import com.achilles.wild.server.entity.common.TempImage;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Date;

public class TempImageDaoTest  extends StarterApplicationTests {


    @Autowired
    TempImageDao tempImageDao;

    @Test
    public void add() throws Exception{
        String path = "C:\\Users\\Achilles\\Desktop\\psc.jpg";
        FileInputStream inputStream = new FileInputStream(new File(path));
        TempImage tempImage = new TempImage();
        tempImage.setUuid(GenerateUniqueUtil.getUuId());
        tempImage.setBizUuid(GenerateUniqueUtil.getUuId());
        byte[] bytes = toByteArray(inputStream);
        tempImage.setImage(bytes);
        tempImage.setUrl("http");
        tempImage.setStatus(1);
        tempImage.setCreateDate(new Date());
        tempImage.setUpdateDate(new Date());
        tempImageDao.insertSelective(tempImage);
    }

    @Test
    public void download() throws Exception{
        TempImage tempImage = tempImageDao.selectByPrimaryKey(1L);
        saveFile(tempImage.getImage());
    }

    public static void saveFile(byte [] data){
        if(data != null){
            String filepath = "C:\\Users\\Achilles\\Desktop\\aaa.jpg";
            File file  = new File(filepath);
            if(file.exists()){
                file.delete();
            }
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data,0,data.length);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  byte[] toByteArray(InputStream input){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024*4];
        int n = 0;
        try {
            while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }

}
