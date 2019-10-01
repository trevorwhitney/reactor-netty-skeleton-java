package com.github.trevorwhitney.skeleton;

import com.github.trevorwhitney.skeleton.context.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.netty.http.server.HttpServer;

public class Main {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        var config = Config.getInstance();
        logger.info("Staring Server API on port " + config.port());
        HttpServer
            .create()
            .port(config.port())
            .route(Router::registerRoutes)
            .bindNow()
            .onDispose()
            .block();

        logger.info("Server finished, shutting down");
    }
}
