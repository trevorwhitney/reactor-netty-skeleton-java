package com.github.trevorwhitney.http.errors;

/**
 * Throw this Exception to cause a 422 HTTP response
 */
public class UnprocessableEntity extends RequestProcessingError {
    public UnprocessableEntity(String message) {
        super(message);
    }

    @Override
    public int code() {
        return 422;
    }
}
