package com.vn.repository;

import java.io.IOException;

public class ITunesSearchException extends IOException {
    public ITunesSearchException(String message) {
        super(message);
    }

    public ITunesSearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
