package com.example.Spring.Reddit.Clone.exceptions;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exMessage) {
        super(exMessage);
    }

    public SpringRedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }
}
