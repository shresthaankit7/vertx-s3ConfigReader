package com.s3ConfigReader.async;

import io.vertx.core.Vertx;

/**
 * Created by ankit07 on 7/15/18.
 */
public class S3configReaderAsync {
    public static void main(String[] args) {
        String bucket = "";
        String keyName = "";

        Vertx vertx = Vertx.vertx();

        S3ConfigReaderAsyncImp s3ConfigReaderAsyncImp = new S3ConfigReaderAsyncImp();

        s3ConfigReaderAsyncImp.getConfig("","",vertx,jsonResult->{
            if(jsonResult.isEmpty()){
                System.out.println("Error in getting s3 config from bucket : " + bucket + "\nKey : " + keyName);
            }else{
                System.out.println("Config Read from S3 : " + jsonResult);
            }
        });

    }
}
