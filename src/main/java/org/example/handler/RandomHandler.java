package org.example.handler;

import com.codahale.metrics.MetricRegistry;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

import java.util.concurrent.atomic.AtomicLong;

public class RandomHandler implements Handler<RoutingContext> {

    private static final String CONSTANT_STRING = "Hello from RandomHandler!";
    private final AtomicLong requestCounter = new AtomicLong(0);
    private MetricRegistry metricRegistry;

    @Override
    public void handle(RoutingContext routingContext) {
        if (metricRegistry == null) {
            metricRegistry = new MetricRegistry();
        }
        long startTime = System.currentTimeMillis();
        long requestNum = requestCounter.incrementAndGet();
        metricRegistry.counter("random_handler.requests").inc();

        Vertx.currentContext().executeBlocking(() -> {
            try {
                Thread.sleep(2000);
                return "42";
            } catch (InterruptedException e) {
                System.err.println("Blocking operation was interrupted." + e.getMessage());
                throw new RuntimeException(e);
            }
        }, false);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        String responseBody = CONSTANT_STRING +
                "\nRequest number: " + requestNum +
                "\nProcessing time: " + duration + " ms";

        routingContext.response()
                .putHeader("content-type", "text/plain")
                .end(responseBody);
    }
}