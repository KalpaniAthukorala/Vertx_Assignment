package com.demo.vertx.verticle;

import com.demo.vertx.util.EventBusAddress;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

public class ThirdPartyVerticle extends AbstractVerticle {
    private WebClient webClient;
    @Override
    public void start() {
        webClient = WebClient.create(vertx);
        vertx.eventBus().consumer(EventBusAddress.NAME_SAVE_ADDRESS, message -> {
            JsonObject student = (JsonObject) message.body();
            webClient.post("http://localhost:8081/demo/service")//TODO : Keep in property file
                    .sendJsonObject(student, ar -> {
                        if (ar.succeeded()) {
                            System.out.println("Success");
                        } else {
                            // Handle the failure if the request failed
                            System.out.println("Failed");
                        }
                    });
        });

    }
}
