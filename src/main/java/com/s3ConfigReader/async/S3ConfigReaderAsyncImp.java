package com.s3ConfigReader.async;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by ankit07 on 7/15/18.
 */
public class S3ConfigReaderAsyncImp implements S3ConfigReaderInterface{

    final static Logger logger = Logger.getLogger(S3configReaderAsync.class);

    public void getConfig(String s3Bucket, String s3ConfigKey, Vertx vertx, Handler<JsonObject> handler) {

        logger.info("Reading Config From Bucket -> " + s3Bucket + " ,Key -> " + s3ConfigKey);

        vertx.executeBlocking(future -> {
            try {
                DefaultAWSCredentialsProviderChain defaultAWSCredentialsProviderChain = new DefaultAWSCredentialsProviderChain();

                AmazonS3Client s3Client = new AmazonS3Client(defaultAWSCredentialsProviderChain);

                S3Object s3Object = s3Client.getObject(s3Bucket, s3ConfigKey);

                InputStream objectData = s3Object.getObjectContent();

                int i = objectData.available();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(objectData));

                StringBuilder out = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    out.append(line);
                }

                JsonObject jsonObject = new JsonObject(out.toString());
                bufferedReader.close();

                future.complete(jsonObject.toString());

            } catch (Exception e) {
//                e.printStackTrace();
                logger.error("Error Reading Config -> Cause : " + e.getMessage());
                handler.handle(new JsonObject());
            }
        }, res -> {
            logger.info("Config Read -> " + new JsonObject(res.result().toString()).toString());
            handler.handle(new JsonObject(res.result().toString()));
        });

        System.out.println("------- Do Other task here ------------ ");

    }
}
