module com.github.trevorwhitney.skeleton.metrics {
    exports com.github.trevorwhitney.metrics;
    requires reactor.netty;
    requires reactor.core;
    requires simpleclient;
    requires org.reactivestreams;
    requires simpleclient.common;
    requires io.netty.codec.http;
    requires simpleclient.hotspot;
}