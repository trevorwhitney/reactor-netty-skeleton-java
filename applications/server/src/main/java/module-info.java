module com.github.trevorwhitney.skeleton {
    exports com.github.trevorwhitney.skeleton;

    requires com.github.trevorwhitney.skeleton.http.errors;
    requires com.github.trevorwhitney.skeleton.logging;
    requires com.github.trevorwhitney.skeleton.metrics;

    requires transitive org.apache.logging.log4j;
    requires reactor.netty;
    requires reactor.core;
    requires org.reactivestreams;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires io.netty.buffer;
}