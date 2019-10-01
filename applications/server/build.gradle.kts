import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(project(":modules:http-errors"))
    implementation(project(":modules:logging"))
    implementation(project(":modules:metrics"))
    implementation("io.projectreactor.netty:reactor-netty:${Versions.reactorNetty}")

    testImplementation(project(":modules:test-support"))
    testImplementation("org.jetbrains:annotations:${Versions.jetbrainsAnnotations}")
    testCompile("org.junit.jupiter:junit-jupiter-api:${Versions.junitJupiter}")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junitJupiter}")
}

application {
    mainClassName = "com.github.trevorwhitney.skeleton.Main"
}

val devEnvironment = System.getenv().plus(mapOf(
    "PORT" to "8080"
))

tasks.withType<JavaExec> {
    environment = devEnvironment
}

tasks {
    "test"(Test::class) {
        useJUnitPlatform()
        exclude("module-info.class")
        testLogging {
            events("passed", "skipped", "failed")
            exceptionFormat = TestExceptionFormat.FULL
        }
    }
}
