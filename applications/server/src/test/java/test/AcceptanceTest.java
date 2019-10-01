package test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static test.Support.makeRequest;

class AcceptanceTest {
    private static Logger logger = LogManager.getLogger();
    private static Process server;
    private static HttpClient httpClient = HttpClient.newHttpClient();

    @BeforeAll
    static void startServer() throws IOException, InterruptedException {
        var environment = new HashMap<String, String>() {{
            put("LOG_LEVEL", "debug");
            put("PORT", "8080");
        }};
        server = Support.startServer(environment);

        HttpRequest pingRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/"))
            .timeout(Duration.ofSeconds(30))
            .GET()
            .build();

        Awaitility.await().atMost(2, TimeUnit.MINUTES).untilAsserted(() -> {
            HttpResponse<String> response = makeRequest(httpClient, pingRequest);
            if (response == null) throw new AssertionError("Failed to get response from server");
            assertThat(response.statusCode()).isEqualTo(204);
        });
    }

    @AfterAll
    static void tearDown() {
        server.destroyForcibly();
    }

    @DisplayName("happy path")
    @Test
    void happyPath() throws IOException, InterruptedException {
        HttpRequest getMetrics = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/metrics"))
            .timeout(Duration.ofSeconds(30))
            .GET()
            .headers("Accept", "text/plain")
            .build();

        HttpResponse<String> response = makeRequest(httpClient, getMetrics);
        if (response == null) throw new AssertionError("Failed to get response from server");
        assertThat(response.statusCode()).isEqualTo(200);
    }
}
