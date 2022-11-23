package com.maizhiyu.yzt.utils.ossKit;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;

@Component
public class AliOssUtil {

    @Resource
    private OSSConfig ossConfig;

    /**
     * 根据指定 InputStream 上传
     *
     * @param inputStream
     * @param fileName
     * @return
     */
    public String uploadInputStream(InputStream inputStream, String path, String fileName, boolean isPrivate) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        try {
            ossClient.putObject(getBucket(isPrivate), path + fileName, inputStream);
            if (isPrivate) {
                return generatePresignedUrl(ossClient, path + fileName, 1800);
            } else {
                return getPublicUrl(path + fileName);
            }
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 上传文件
     *
     * @param path
     * @param file
     * @param isPrivate
     * @throws Exception
     */
    public String uploadFile(String path, File file, boolean isPrivate) {
        try {
            uploadInputStream(new FileInputStream(file), path, file.getName(), isPrivate);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return getPublicUrl(path+file.getName());
    }

    /**
     * 获取公有存储访问地址
     *
     * @param filename
     * @return
     */
    public String getPublicUrl(String filename) {

        if (!filename.startsWith("/")) {
            filename = "/" + filename;
        }
        return ossConfig.getPublicBucketUrl() + filename;
    }

    /**
     * 获取附带图片处理后的私有存储
     *
     * @param objectName 文件路径
     * @return 访问地址
     */
    public String generateWithMark(String objectName) {
        return generateByStyle(objectName, ossConfig.getStyleWatermark(), ossConfig.getExpiration());
    }

    /**
     * 获取私有图片缩略图
     *
     * @param objectName 文件路径
     * @return 访问地址
     */
    public String generateByMicro(String objectName) {
        return generateByStyle(objectName, ossConfig.getStyleMicro(), ossConfig.getExpiration());
    }

    /**
     * 获取附带处理的私有图片
     *
     * @param objectName 文件路径
     * @param style      处理别名
     * @param expireTime 访问有效期（秒）
     * @return 访问地址
     */
    public String generateByStyle(String objectName, String style, int expireTime) {

        String bucketName = getBucket(true);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName);
        // 过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, expireTime);

        request.setExpiration(calendar.getTime());
        request.setMethod(HttpMethod.GET);
        request.setProcess("style/" + style);

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), getCredentialProvider(), clientConfiguration);
        URL url = ossClient.generatePresignedUrl(request);

        return ossConfig.getPrivateBucketUrl() + url.getFile();
    }

    /**
     * 获取私有链接
     *
     * @param objectName
     * @param expiration
     * @return
     */
    public String generatePresignedUrl(String objectName, int expiration) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        OSSClient ossClient = new OSSClient(ossConfig.getEndpoint(), getCredentialProvider(), clientConfiguration);

        try {
            return generatePresignedUrl(ossClient, objectName, expiration);
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 根据URL 获取文件路径（不包含第一个/)
     *
     * @param url
     * @return
     */
    public static String getPath(String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
            URL urlPath = new URL(url);
            url = urlPath.getFile();
        } catch (IOException ex) {
        }

        if (url.startsWith("/")) {
            url = url.substring(1);
        }
        if (url.lastIndexOf("?") > 0) {
            url = url.substring(0, url.lastIndexOf("?"));
        }
        return url;
    }

    /**
     * 获取Bucket名称
     *
     * @param isPrivate
     * @return
     */
    private String getBucket(boolean isPrivate) {
        String bucketName;
        if (isPrivate) {
            bucketName = ossConfig.getPrivateBucket();
        } else {
            bucketName = ossConfig.getPublicBucket();
        }

        return bucketName;
    }

    /**
     * 获取私有链接
     *
     * @param objectName
     * @return
     */
    public String generatePresignedUrl(String objectName) {
        return generatePresignedUrl(objectName, ossConfig.getExpiration());
    }

    /**
     * 获取私有链接
     *
     * @param ossClient
     * @param objectName
     * @param expiration
     * @return
     */
    private String generatePresignedUrl(OSS ossClient, String objectName, int expiration) {
        // 过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.SECOND, expiration);
        String bucketName = getBucket(true);
        objectName = getPath(objectName);
        URL url = ossClient.generatePresignedUrl(bucketName, objectName, calendar.getTime());
        return ossConfig.getPrivateBucketUrl() + url.getFile();
    }

    public CredentialsProvider getCredentialProvider() {
        return new DefaultCredentialProvider(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
    }

    /**
     * 兼容腾讯云图片地址
     *
     * @param url
     * @return
     */
    public String compatibleUrl(String url) {
        if (StringUtils.isBlank(url)) return null;
        if (url.contains("shanghai.myqcloud.com")) return url;
        return generatePresignedUrl(url);
    }
}
