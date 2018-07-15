package com.s3ConfigReader.sync;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
public class S3ConfigReaderImp implements S3ConfigReaderInterface {

    public JSONObject getConfig(String bucketName,String objectKey){
        try {
            DefaultAWSCredentialsProviderChain defaultAWSCredentialsProviderChain = new DefaultAWSCredentialsProviderChain();

            AmazonS3Client s3Client = new AmazonS3Client(defaultAWSCredentialsProviderChain);

            S3Object s3Object = s3Client.getObject(bucketName, objectKey);

            InputStream objectData = s3Object.getObjectContent();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(objectData));

            StringBuilder out = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                out.append(line);
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(out.toString());

            System.out.println("CONFIG::: " + jsonObject);
            bufferedReader.close();

            return jsonObject;

        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Error in getting config from s3 bucket.");
        return null;
    }
}
