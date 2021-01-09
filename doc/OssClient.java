package com.taobao.cfp.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.taobao.common.keycenter.security.Cryptograph;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * 阿里云oss客户端.
 */
public class OssClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OssClient.class);
    /**
     * OSS Access key.
     */
    private String accessKey;
    /**
     * OSS Access secret.
     */
    private String accessSecret;
    /**
     * OSS Bucket key.
     */
    private String bucketName;
    /**
     * OSS End point.
     */
    private String endPoint;
    /**
     * OSS End point.
     */
    private String host;

    private static OSSClient singleOssClient;

    public static final String keyName = "oss_key_encrypt_name";

    @Resource
    private Cryptograph cryptographImpl;

    private synchronized void initOssClient() {

        String decryptAccessKey = cryptographImpl.decrypt(accessKey, keyName);
        singleOssClient = new OSSClient(endPoint, decryptAccessKey, accessSecret);
    }

    /**
     * 获取oss客户端.
     *
     * @return the oss client
     */
    private OSSClient getOssClient() {
        if (singleOssClient == null) {
            initOssClient();
        }
        return singleOssClient;
    }

    /**
     * 获取web下载地址.
     *
     * @param key 对象key
     * @return 下载路径
     */
    public String getDownloadUrl(String key) {
        return getDownloadUrl(key, 30);
    }

    /**
     * 获取web下载地址.
     *
     * @param key 对象key
     * @return 下载路径
     */
    public String getDownloadUrl(String key, int minute) {
        String result = null;
        // 检查对象是否存在
        boolean found = exist(key);
        if (found) {
            Date expiration = DateUtils.addMinutes(new Date(), minute);
            // 获取oss对象访问url，注意超时时间
            URL generatePresignedUrl = getOssClient().generatePresignedUrl(bucketName, key, expiration);
            result = generatePresignedUrl.toString();
        } else {
            LOGGER.warn("file not found, key:{}", key);
        }
        return result;
    }

    /**
     * 获取上传令牌.
     *
     * @param dir 上传目录
     * @return the packUpload token
     */
    //public Map<String, String> getUploadToken(String dir) {
    //    Map<String, String> respMap = new LinkedHashMap<>();
    //    Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
    //    PolicyConditions policyConditions = new PolicyConditions();
    //    policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0L, 1048576 * 50);
    //    policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
    //    String postPolicy = getOssClient().generatePostPolicy(expiration, policyConditions);
    //    byte[] binaryData = new byte[0];
    //    try {
    //        binaryData = postPolicy.getBytes("utf-8");
    //    } catch (UnsupportedEncodingException e) {
    //        LOGGER.error("UnsupportedEncodingException", e);
    //    }
    //    String encodedPolicy = BinaryUtil.toBase64String(binaryData);
    //    String postSignature = getOssClient().calculatePostSignature(postPolicy);
    //    respMap.put("ossaccessKeyId", accessKey);
    //    respMap.put("policy", encodedPolicy);
    //    respMap.put("signature", postSignature);
    //    respMap.put("fileDir", dir);
    //    respMap.put("host", host);
    //    respMap.put("expire", String.valueOf(expiration.getTime() / 1000));
    //    return respMap;
    //}

    /**
     * 上传文件到oss.
     *
     * @param key      对象key
     * @param file     本地文件
     * @param metadata oss对象信息
     * @return oss的ETag.
     */
    public String put(String key, File file, ObjectMetadata metadata) {
        LOGGER.info("bucketName {},key {}", bucketName, key);
        PutObjectRequest request = new PutObjectRequest(bucketName, key, file);
        request.setMetadata(metadata);
        PutObjectResult putObjectResult = getOssClient().putObject(request);
        return putObjectResult.getETag();
    }

    /**
     * 上传文件到oss.
     *
     * @param key      对象key
     * @param file     本地文件
     * @param metadata oss对象信息
     * @return oss的ETag.
     */
    public String putWithPrivate(String key, File file, ObjectMetadata metadata) {
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        metadata.setObjectAcl(CannedAccessControlList.Private);
        LOGGER.info("bucketName {},key {}", bucketName, key);
        return put(key, file, metadata);
    }

    /**
     * 上传数据流到oss.
     *
     * @param key      对象key
     * @param input    数据流
     * @param metadata oss对象信息
     * @return oss的ETag.
     */
    public String put(String key, InputStream input, ObjectMetadata metadata) {
        PutObjectResult putObjectResult = getOssClient().putObject(bucketName, key, input, metadata);
        return putObjectResult.getETag();
    }

    /**
     * 上传文件到oss.
     *
     * @param key      对象key
     * @param input    数据流
     * @param metadata oss对象信息
     * @return oss的ETag.
     */
    public String putWithPrivate(String key, InputStream input, ObjectMetadata metadata) {
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        metadata.setObjectAcl(CannedAccessControlList.Private);
        return put(key, input, metadata);
    }

    /**
     * 上传数据流到oss.并设置为私有
     *
     * @param key      对象key
     * @param input    数据流
     * @param metadata oss对象信息
     * @return oss的ETag.
     */
    //public String putWithPrivat(String key, InputStream input, ObjectMetadata metadata) {
    //    // 设置文件的访问权限为公共读。
    //    PutObjectResult putObjectResult = getOssClient().putObject(bucketName, key, input, metadata);
    //    getOssClient().setObjectAcl(bucketName, key, CannedAccessControlList.Private);
    //    return putObjectResult.getETag();
    //}

    /**
     * 检查对象是否存在.
     *
     * @param key 对象key
     * @return 是否存在.
     */
    public boolean exist(String key) {
        return getOssClient().doesObjectExist(bucketName, key);
    }

    /**
     * 下载远程对象到数据流.
     *
     * @param key 对象key
     * @return 数据流.
     */
    public InputStream get(String key) {
        OSSObject ossObject = getOssClient().getObject(bucketName, key);
        if (ossObject != null) {
            return ossObject.getObjectContent();
        } else {
            return null;
        }
    }

    /**
     * 下载远程对象到指定文件.
     *
     * @param key  对象key
     * @param file 本地下载目标路径
     * @return 对象信息.
     */
    public void get(String key, File file) {
        getOssClient().getObject(new GetObjectRequest(bucketName, key), file);
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
