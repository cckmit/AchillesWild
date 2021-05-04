package com.achilles.wild.server.tool.file;

import com.achilles.wild.server.tool.generate.encrypt.MD5Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import java.io.*;
import java.util.Base64;

public class FileUtil {

    static String srcPath = "C:\\Users\\Achilles\\Desktop\\photo\\10028.jpg";
    static String destPath = "C:\\Users\\Achilles\\Desktop\\test1.jpg";

    public static void main(String[] args) {
//        copyFile("C:\\Users\\Achilles\\Desktop\\z.jpg","C:\\Users\\Achilles\\Desktop\\z3453.jpg");

        //readAndWrite("C:\\Users\\Achilles\\Desktop\\z.jpg","C:\\Users\\Achilles\\Desktop\\66.jpg");
        //String base64 = toBase64("C:\\Users\\Achilles\\Desktop\\z.jpg");
        //Zbyte[] bytes = toBytes(srcPath);
//        byte[] bytes = getBytes(path);
//        for (int i = 0; i < 10000; i++) {
//            String str = getString(path);
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        }
//        String base64 = toBase64(srcPath);
//        base64ToFile(base64,destPath);
        for (int i = 0; i < 10; i++) {
            String str = getUniqueIdentification(getInputStream(destPath));
            System.out.println(str);
        }

    }


    /**
     * getUniqueIdentification
     *
     * @param inputStream
     * @return
     */
    public static String getUniqueIdentification(InputStream inputStream) {

        String encryption =  MD5Util.get(inputStream,MD5Util.SALT);

        return encryption;
    }


    /**
     * getOutputStream
     *
     * @param inputStream
     * @param outputStream
     */
    public static void getOutputStream(InputStream inputStream,OutputStream outputStream){
        try {
            IOUtils.copy(inputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * getInputStream
     *
     * @param byteArrayOutputStream
     * @return
     */
    public static ByteArrayInputStream getInputStream(ByteArrayOutputStream byteArrayOutputStream){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;
    }

    /**
     * getInputStream
     *
     * @param path
     * @return
     */
    public static InputStream getInputStream(String path){

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return inputStream;
    }

    /**
     * getString
     *
     * @param path
     * @return
     */
    public static String getString(String path){

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String content = null;
        try {
            content = IOUtils.toString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
        return content;
    }

    public static byte[] toBytes(String path){

        InputStream inputStream = getInputStream(path);

        byte[] bytes = null;
        try {
            bytes  = new byte[inputStream.available()];
            inputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

        return bytes;
    }

    /**
     * getBytes
     *
     * @param path
     * @return
     */
    public static byte[] getBytes(String path){

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            IOUtils.copy(inputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            1.先打开的后关闭，后打开的先关闭
//            2.看依赖关系，如果流a依赖流b，应该先关闭流a，再关闭流b
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
        }

        return outputStream.toByteArray();
    }


    /**
     * readToBytes
     *
     * @param path
     * @return
     */
    public static byte[] readToBytes(String path){

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[8096];
        int len = -1;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outSteam != null) {
                    outSteam.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return outSteam.toByteArray();
    }

        /**
         * readAndWrite
         *
         * @param sourcePath
         * @param targetPath
         */
    public static void readAndWrite(String sourcePath,String targetPath) {

        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(sourcePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(targetPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] b=new byte[1024];
        int len=0;
        try {
            while(-1!= (len = bufferedInputStream.read(b, 0, b.length))) {
                bufferedOutputStream.write(b, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (bufferedInputStream != null){
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedOutputStream != null){
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * copyFile
     *
     * @param path          "C:\\Users\\Achilles\\Desktop\\z.jpg";
     * @param targetPath    "C:\\Users\\Achilles\\Desktop\\z.jpg";
     */
    public static void copyFile(String path,String targetPath) {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(targetPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            IOUtils.copy(fileInputStream,fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * cloneInputStream
     *
     * @param inputStream
     * @param size
     * @return
     */
    public static ByteArrayInputStream[] cloneInputStream(InputStream inputStream,int size) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ByteArrayInputStream[0];
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ByteArrayInputStream[] byteArrayInputStreams = new ByteArrayInputStream[size];
        for (int i = 0; i < size; i++) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            byteArrayInputStreams[i] = byteArrayInputStream;
        }
        return byteArrayInputStreams;
    }

    /**
     * base64ToFile
     *
     * @param base64
     * @param destPath
     */
    public static void base64ToFile(String base64,String destPath){

        byte[] imageBytes = Base64Utils.decodeFromString(base64);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);

        File targetFile = new File(destPath);
        try {
            FileUtils.copyInputStreamToFile(inputStream, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getBase64(String srcPath){
        byte[] imageBytes = toBytes(srcPath);
        String base64 = Base64Utils.encodeToString(imageBytes);
        return base64;
    }

    /**
     * toBase64
     *
     * @param path
     * @return
     */
    public static String toBase64(String path){

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String encoded = toBase64(fileInputStream);

        return encoded;
    }

    /**
     * toBase64
     *
     * @param inputStream
     * @return
     */
    public static String toBase64(InputStream inputStream){

        byte[] bytes;
        try {
            bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String encoded = Base64.getEncoder().encodeToString(bytes);

        return encoded;
    }

    /**
     * toInputStream
     *
     * @param path "C:\\Users\\Achilles\\Desktop\\z.jpg";
     * @return
     */
    public static InputStream toInputStream(String path){

        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("path can not be null !");
        }

        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return inputStream;
    }

    /**
     * toFile
     *
     * @param inputStream
     * @param path "C:\\Users\\Achilles\\Desktop\\z.jpg";
     * @return
     */
    public static void toFile(InputStream inputStream,String path){

        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream can not be null !");
        }
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("path can not be null !");
        }

        File targetFile = new File(path);
        try {
            FileUtils.copyInputStreamToFile(inputStream, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
