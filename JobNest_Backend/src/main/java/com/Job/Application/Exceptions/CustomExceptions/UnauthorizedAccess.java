package com.Job.Application.Exceptions.CustomExceptions;

public class UnauthorizedAccess extends RuntimeException {
  public UnauthorizedAccess(String message) {
    super(message);
  }
}
