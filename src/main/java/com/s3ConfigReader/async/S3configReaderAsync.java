package com.s3ConfigReader.async;

import io.vertx.core.Vertx;
import org.apache.log4j.Logger;


/**
 * Created by ankit07 on 7/15/18.
 */
public class S3configReaderAsync {
    final static Logger logger = Logger.getLogger(S3configReaderAsync.class);

    public static void main(String[] args) {
        String bucket = args[0];
        String keyName = args[1];

        Vertx vertx = Vertx.vertx();

        S3ConfigReaderAsyncImp s3ConfigReaderAsyncImp = new S3ConfigReaderAsyncImp();

        s3ConfigReaderAsyncImp.getConfig(bucket,keyName,vertx,jsonResult->{
            if(jsonResult.isEmpty()){
                logger.error("Error in getting s3 config from bucket : " + bucket + " ,Key : " + keyName);
            }else{
                logger.error("Config Read from S3 : " + jsonResult);
            }
        });

    }
}
