plugins {
    `java-library`
}

repositories {
    jcenter()
}

dependencies {
    api("io.prometheus:simpleclient:${Versions.prometheusSimpleClient}")
    api("io.prometheus:simpleclient_hotspot:${Versions.prometheusSimpleClient}")
    api("io.prometheus:simpleclient_httpserver:${Versions.prometheusSimpleClient}")

    implementation("io.projectreactor.netty:reactor-netty:${Versions.reactorNetty}")
}