package com.github.trevorwhitney.skeleton;

import com.fasterxml.jackson.core.JsonParseException;
import com.github.trevorwhitney.http.errors.RequestProcessingError;
import com.github.trevorwhitney.metrics.MetricsHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;
import reactor.netty.http.server.HttpServerRoutes;

import java.util.function.BiFunction;

class Router {
    private static Logger logger = LogManager.getLogger();

    /**
     * Register routes on the provided {@link reactor.netty.http.server.HttpServerRoutes}
     *
     * @param routeBuilder
     */
    static void registerRoutes(HttpServerRoutes routeBuilder) {
        var metricsHandler = new MetricsHandler();
        routeBuilder
            .get("/", (request, response) -> response.status(204).send())
            .get("/metrics", handleErrors(metricsHandler::handleRequest))
        ;
    }

    private static BiFunction<HttpServerRequest, HttpServerResponse, Flux<Void>> handleErrors(
        BiFunction<HttpServerRequest, HttpServerResponse, Publisher<Void>> handler
    ) {
        return (request, response) -> Flux.from(handler.apply(request, response))
            .onErrorResume(e -> {
                if (logger.isTraceEnabled()) e.printStackTrace();
                if (e instanceof RequestProcessingError) {
                    return response.status(((RequestProcessingError) e).code()).sendString(Mono.just(e.getMessage()));
                } else if (e instanceof JsonParseException) {
                    return response.status(400).sendString(Mono.just("JSON syntax error"));
                } else {
                    return response.status(500).send();
                }
            });
    }
}
