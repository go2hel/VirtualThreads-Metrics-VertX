package org.example;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.ThreadingModel;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import org.example.verticle.SampleVerticle;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions()
                .setMetricsOptions(
                        new DropwizardMetricsOptions()
                                .setEnabled(true)
                                .setJmxEnabled(true)
                )
        );
        DeploymentOptions options = new DeploymentOptions().setThreadingModel(ThreadingModel.VIRTUAL_THREAD);
        vertx.deployVerticle(new SampleVerticle(), options);

        System.out.println("Application started. Press any key to exit.");
        try {
            System.in.read();
            vertx.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
