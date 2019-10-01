package com.github.trevorwhitney.metrics;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import io.prometheus.client.hotspot.DefaultExports;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerRequest;
import reactor.netty.http.server.HttpServerResponse;

import java.io.StringWriter;

public class MetricsHandler {
    public MetricsHandler() {
        DefaultExports.initialize();
    }

    public Publisher<Void> handleRequest(HttpServerRequest request, HttpServerResponse response) {
        Mono<String> metrics = Mono.fromCallable(() -> {
            var metricFamilySamples = CollectorRegistry.defaultRegistry.metricFamilySamples();
            var writer = new StringWriter();
            TextFormat.write004(writer, metricFamilySamples);
            return writer.toString();
        });

        return response.status(200)
            .header(HttpHeaderNames.CONTENT_TYPE, "text/plain")
            .sendString(metrics);
    }
}
