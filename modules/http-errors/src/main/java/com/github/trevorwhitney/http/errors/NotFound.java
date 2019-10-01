package com.github.trevorwhitney.http.errors;

/**
 * Throw this Exception to cause a 404 HTTP response
 */
public class NotFound extends RequestProcessingError {
    public NotFound(String message) {
        super(message);
    }

    @Override
    public int code() {
        return 404;
    }

}
