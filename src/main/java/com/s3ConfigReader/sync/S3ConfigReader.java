package com.s3ConfigReader.sync;

import io.vertx.core.Handler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.xml.ws.AsyncHandler;
import java.io.*;
import java.util.List;

public class S3ConfigReader {

    public static void main(String[] args) {

        String s3BucketName = args[0];
        String s3KeyName = args[1];

        System.out.println("Getting Object from bucket : " + s3BucketName + "\nKeyName : " + s3KeyName);
        S3ConfigReaderImp s3ConfigReaderImp = new S3ConfigReaderImp();

        JSONObject config = s3ConfigReaderImp.getConfig(s3BucketName, s3KeyName);

        System.out.println("S3 config : " + config);
    }
}
