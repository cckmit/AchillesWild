package com.achilles.wild.server.tool.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class FileUtil {

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

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
    public static InputStream toFile(InputStream inputStream,String path){

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

        return inputStream;
    }
}
