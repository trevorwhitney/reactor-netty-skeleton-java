package com.github.trevorwhitney.skeleton.context;

import java.util.Map;

public class Config {
    private static Config singleton;
    private Map<String, String> env;

    private Config(Map<String, String> env) {
        this.env = env;
    }

    public static Config getInstance() {
        if (singleton == null) singleton = new Config(System.getenv());
        return singleton;
    }

    public Integer port() {
        return Integer.valueOf(env.get("PORT"));
    }
}
