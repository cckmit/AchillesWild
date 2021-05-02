package com.achilles.wild.server.tool.file;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;

public class ImageUtil {

    static String srcPath = "C:\\Users\\Achilles\\Desktop\\photo\\4609.jpg";
    static String destPath = "C:\\Users\\Achilles\\Desktop\\test.jpg";

    static String format = "jpg";

    public static void main(String[] args) {

//        trimByWidth(FileUtil.getInputStream(path),"jpg",50,desc);
//        trimByScale(FileUtil.getInputStream(path),"jpg",0.5,desc);

//        int size = trimByQuality(FileUtil.getInputStream(path).

//        trimByQuality(FileUtil.getInputStream(path),"jpg",0.1414213,1f,desc);

//        compressForScale(path,desc,220*1024L,0.8);

        trimBySizeLimit(srcPath,destPath,300);
    }

    /**
     * trimBySizeLimit
     *
     * @param srcPath
     * @param destPath
     * @param sizeLimit
     */
    public static void trimBySizeLimit(String srcPath,String destPath,int sizeLimit){

        File srcFile = new File(srcPath);
        int srcFileSize = (int)srcFile.length()/1024;
        if (srcFileSize <= sizeLimit) {
            return;
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(srcFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        inputStream = trimBySizeLimit(inputStream,sizeLimit);
        FileUtil.toFile(inputStream,destPath);
    }


    /**
     * trimBySizeLimit
     *
     * @param inputStream
     * @param sizeLimit
     * @return
     */
    public static InputStream trimBySizeLimit(InputStream inputStream,int sizeLimit){

        if (inputStream == null){
            throw new IllegalArgumentException("inputStream can not be null !");
        }

        int srcFileSize = 0;
        try {
            srcFileSize = inputStream.available()/1024;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (srcFileSize <= sizeLimit) {
            return inputStream;
        }

        double times = new BigDecimal(srcFileSize).divide(new BigDecimal(sizeLimit),10,BigDecimal.ROUND_HALF_UP).doubleValue();
        double scale = 1/Math.sqrt(times);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Thumbnails.of(inputStream).scale(scale).outputQuality(1).outputFormat(format).toOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        srcFileSize = outputStream.size()/1024;
        inputStream = FileUtil.getInputStream(outputStream);
        if (srcFileSize <= sizeLimit) {
            return inputStream;
        }

        return trimBySizeLimit(inputStream,sizeLimit);
    }

    /**
     *
     * @param srcPath 原图片地址
     * @param desPath 目标图片地址
     * @param desFileSize 指定图片大小,单位kb
     * @param accuracy 精度,递归压缩的比率,建议小于0.9
     * @return
     */
    public static String compressForScale(String srcPath,String desPath,long desFileSize , double accuracy){
        try {
            File srcFile = new File(srcPath);
            long srcFilesize = srcFile.length();
            System.out.println("原图片:"+srcPath + ",大小:" + srcFilesize/1024 + "kb");
            //递归压缩,直到目标文件大小小于desFileSize
            compressPicCycle(desPath, desFileSize, accuracy);

            File desFile = new File(desPath);
            System.out.println("目标图片:" + desPath + ",大小" + desFile.length()/1024 + "kb");
            System.out.println("图片压缩完成!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desPath;
    }

    public static void compressPicCycle(String desPath , long desFileSize, double accuracy) throws IOException{
        File imgFile = new File(desPath);
        long fileSize = imgFile.length();
        //判断大小,220,不压缩,如果大于等于220k,压缩
        if(fileSize <= desFileSize * 300){
            return;
        }
        //计算宽高
        BufferedImage bim = ImageIO.read(imgFile);
        int imgWidth = bim.getWidth();
        int imgHeight = bim.getHeight();
        int desWidth = new BigDecimal(imgWidth).multiply(new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(imgHeight).multiply(new BigDecimal(accuracy)).intValue();
        Thumbnails.of(desPath).size(desWidth, desHeight).outputQuality(accuracy).toFile(desPath);
        //如果不满足要求,递归直至满足小于1M的要求
        compressPicCycle(desPath, desFileSize, accuracy);
    }

    /**
     * trimByWidth
     *
     * @param inputStream
     * @param format
     * @param width
     * @param path
     */
    public static void trimByWidth(InputStream inputStream,String format, int width,String path){

        try {
            Thumbnails.of(inputStream).width(width).outputFormat(format).toFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void trimByScale(InputStream inputStream,String format,double scale,String path){

        try {
            Thumbnails.of(inputStream).scale(scale).outputFormat(format).toFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
