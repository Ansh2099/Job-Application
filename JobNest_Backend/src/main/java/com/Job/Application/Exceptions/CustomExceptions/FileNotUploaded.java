package com.Job.Application.Exceptions.CustomExceptions;

public class FileNotUploaded extends RuntimeException {
    public FileNotUploaded(String message) {
        super(message);
    }
}
