package com.s3ConfigReader.sync;

import org.json.simple.JSONObject;


public interface S3ConfigReaderInterface {
    JSONObject getConfig(String bucketName, String objectKey);
}
