package com.achilles.wild.server.tool.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Base64;

public class FileUtil {

    public static void main(String[] args) {
//        copyFile("C:\\Users\\Achilles\\Desktop\\z.jpg","C:\\Users\\Achilles\\Desktop\\z3453.jpg");
        String base64 = toBase64("C:\\Users\\Achilles\\Desktop\\z.jpg");
        System.out.println();
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
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return new ByteArrayInputStream[0];
        }

        ByteArrayInputStream[] byteArrayInputStreams = new ByteArrayInputStream[size];
        for (int i = 0; i < size; i++) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            byteArrayInputStreams[i] = byteArrayInputStream;
        }
        return byteArrayInputStreams;
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
