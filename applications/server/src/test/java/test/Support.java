package test;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Support {
    private static Logger logger = LogManager.getLogger();

    static Process startServer(Map<String, String> environment) throws IOException, InterruptedException {
        var repoRoot = getProjectRoot();
        var trace = logger.isDebugEnabled();
        //TODO: make application path more generic, or infer from location
        var command = Arrays.asList(
            "./gradlew", ":applications:server:processTestResources", ":applications:server:installDist");
        ProcessBuilder buildProjectProcess = new ProcessBuilder()
            .directory(new File(repoRoot))
            .command(command);

        if (trace) buildProjectProcess.inheritIO();

        var projectHasBuilt = buildProjectProcess
            .start()
            .waitFor(5, TimeUnit.MINUTES);

        if (!projectHasBuilt) throw new RuntimeException("failed to build project in under 5 minutes");


        //TODO: make application name more generic, or infer from location
        String startServerCommand = String.format("%s/applications/server/build/install/server/bin/server", getProjectRoot());
        ProcessBuilder startServerPb = new ProcessBuilder(startServerCommand);
        if (trace) {
            startServerPb
                .redirectErrorStream(true)
                .inheritIO();
        }

        var env = startServerPb.environment();
        env.put("LOG_LEVEL", logger.getLevel().toString());
        env.putAll(environment);

        return startServerPb.start();
    }

    @Nullable
    static HttpResponse<String> makeRequest(HttpClient httpClient, HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (ConnectException e) {
            logger.error(String.format("Could not connect to server: \"%s\". Retrying", e.getMessage()));
            if (logger.isTraceEnabled()) e.printStackTrace();
            Thread.sleep(1000);
        }
        return response;
    }

    private static String getProjectRoot() throws IOException, InterruptedException {
        var p = new ProcessBuilder("git", "rev-parse", "--show-toplevel").start();
        p.waitFor(5, TimeUnit.MINUTES);

        var lines = IOUtils.readLines(p.getInputStream(), StandardCharsets.UTF_8);
        if (lines.size() == 0) {
            throw new IOException("git rev-parse returned no lines");
        }

        return String.join("", lines);
    }
}
