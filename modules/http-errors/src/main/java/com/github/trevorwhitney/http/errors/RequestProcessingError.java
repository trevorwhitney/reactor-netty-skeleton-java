package com.github.trevorwhitney.http.errors;

public abstract class RequestProcessingError extends IllegalArgumentException {
    RequestProcessingError(String message) {
        super(message);
    }

    abstract public int code();
}
