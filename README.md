# Reactor Netty Skeleton - Java

A skeleton repository for starting a new Java project with reactor netty.

## Building and Running

This application was build against `JDK 11` with gradle version `5`.

Building and running tests: `./gradlew clean build`
Running the server: `./gradlew run`

## API

| route | description | example request|
|-------|-------------|----------------|
| `GET /` | returns 204 if server is up | `curl -X GET localhost:8080/` |
| `GET /metrics` | returns prometheus style metrics | `curl -X GET localhost:8080/metrics` |