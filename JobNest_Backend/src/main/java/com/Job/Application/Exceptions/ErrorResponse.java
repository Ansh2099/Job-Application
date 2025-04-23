package com.Job.Application.Exceptions;

import java.time.LocalDateTime;

public class ErrorResponse {

    int status;
    String message;
    LocalDateTime timestamp;

    public ErrorResponse(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
