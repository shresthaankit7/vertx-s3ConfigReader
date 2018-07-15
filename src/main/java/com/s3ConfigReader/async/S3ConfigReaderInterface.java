package com.s3ConfigReader.async;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

/**
 * Created by ankit07 on 7/15/18.
 */
public interface S3ConfigReaderInterface {
    void getConfig(String s3Bucket, String s3ConfigKey, Vertx vertx, Handler<JsonObject> handler);
}
