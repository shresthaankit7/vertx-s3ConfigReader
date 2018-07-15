package com.s3ConfigReader.async;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.io.*;

/**
 * Created by ankit07 on 7/15/18.
 */
public class S3ConfigReaderAsyncImp implements S3ConfigReaderInterface{

    public void getConfig(String s3Bucket, String s3ConfigKey, Vertx vertx, Handler<JsonObject> handler) {

        vertx.executeBlocking(future -> {
            try {
//                Thread.sleep(5000);         // Checking Blocking code.

                DefaultAWSCredentialsProviderChain defaultAWSCredentialsProviderChain = new DefaultAWSCredentialsProviderChain();

                AmazonS3Client s3Client = new AmazonS3Client(defaultAWSCredentialsProviderChain);

                S3Object s3Object = s3Client.getObject(s3Bucket, s3ConfigKey);

                InputStream objectData = s3Object.getObjectContent();

//                InputStream objectData = new FileInputStream(new File("/home/ankit07/TEST/config_test.json"));

                int i = objectData.available();
                System.out.println("Value of available :: " + i);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(objectData));

                StringBuilder out = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    out.append(line);
                }

                JsonObject jsonObject = new JsonObject(out.toString());
                bufferedReader.close();

                System.out.println("I AM HERE !!!!!!");
                handler.handle(jsonObject);

                future.complete("S3 Read completed.");
            } catch (Exception e) {
                e.printStackTrace();
                handler.handle(new JsonObject());
            }
        }, res -> {
            System.out.println("Result -> " + res.result());
        });

        System.out.println("------- Do Other task here ------------ ");

    }
}
