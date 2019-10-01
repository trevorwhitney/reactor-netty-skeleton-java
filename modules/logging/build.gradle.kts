plugins {
    `java-library`
}

repositories {
    jcenter()
}

dependencies {
    api("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${Versions.jackson}")
    api("com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}")
    api("org.apache.logging.log4j:log4j-core:${Versions.log4j}")
    api("org.apache.logging.log4j:log4j-slf4j-impl:${Versions.log4j}")
}