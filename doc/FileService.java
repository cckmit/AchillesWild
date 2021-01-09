package com.taobao.cfp.oss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * description
 *
 * @author duanweichao.dwc
 * @date 2020/03/01
 */
public class FileService {
    private Logger logger = LoggerFactory.getLogger(FileService.class);

    @Resource
    private OssClient ossClient;

    /**
     * �ϴ� File
     * @param fileName
     * @param file
     * @return
     */
    //public String export(String fileName, File file) {
    //    try {
    //        String eTag = ossClient.putWithPrivate(fileName, file, null);
    //        if (eTag != null) {
    //            final String downloadUrl = ossClient.getDownloadUrl(fileName);
    //            logger.info("export output is : fileName:{}, downloadUrl: {} ", fileName, downloadUrl);
    //            return downloadUrl;
    //        }
    //    } catch (Exception e) {
    //        logger.error("exportToExcel exception : ", e);
    //        return null;
    //    }
    //
    //    return null;
    //}

    /**
     * �ϴ� InputStream
     * @param fileName
     * @param inputStream
     * @return
     */
    public String export(String fileName, InputStream inputStream) {
        try {
            String eTag = ossClient.putWithPrivate(fileName, inputStream, null);
            if (eTag != null) {
                final String downloadUrl = ossClient.getDownloadUrl(fileName);
                logger.info("export output is : fileName:{}, downloadUrl: {} ", fileName, downloadUrl);
                return downloadUrl;
            }
        } catch (Exception e) {
            logger.error("exportToExcel exception : ", e);
            return null;
        }

        return null;
    }

}
