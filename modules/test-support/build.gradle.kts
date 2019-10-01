plugins {
    `java-library`
}

repositories {
    jcenter()
}

dependencies {
    implementation(project(":modules:logging"))

    api("org.assertj:assertj-core:${Versions.assertj}")
    api("org.junit.jupiter:junit-jupiter-params:${Versions.junitJupiter}")
    api("commons-io:commons-io:${Versions.commonsIO}")
    api("org.awaitility:awaitility:${Versions.awaitility}")
}