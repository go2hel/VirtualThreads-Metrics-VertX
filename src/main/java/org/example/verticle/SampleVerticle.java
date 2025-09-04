package org.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import org.example.handler.RandomHandler;

public class SampleVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        Router router = Router.router(vertx);

        router.get("/hello/test").handler(new RandomHandler());

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(2711);
    }
}
