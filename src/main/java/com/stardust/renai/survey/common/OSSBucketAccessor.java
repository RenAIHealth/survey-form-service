package com.stardust.renai.survey.common;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;

import java.io.InputStream;

public class OSSBucketAccessor implements StorageAccessor {
    private String endpoint = "oss-cn-beijing.aliyuncs.com";
    private String accessKeyId = "LTAIZmvMvLVPapcK";
    private String accessKeySecret = "48qcu53ChOFDmpjA4lCsGIgtNkobk8";
    private OSSClient client;

    public OSSBucketAccessor() {
        client = new OSSClient("http://" + endpoint, accessKeyId, accessKeySecret);
    }

    @Override
    public String put(String bucketName, String fileKey, InputStream inputStream) {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("application/octet-stream");
        client.putObject(bucketName, fileKey, inputStream, meta);
        return "http://" + bucketName + "." + endpoint + "/" + fileKey;
    }

    @Override
    public void delete() {

    }
}
