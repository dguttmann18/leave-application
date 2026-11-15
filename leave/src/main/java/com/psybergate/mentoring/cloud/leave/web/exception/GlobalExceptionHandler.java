package com.psybergate.mentoring.cloud.leave.web.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ProblemDetail handleNotFound(EntityNotFoundException ex) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ProblemDetail handleBadRequest(IllegalArgumentException ex) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(IllegalStateException.class)
  public ProblemDetail handleConflict(IllegalStateException ex) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(RestClientResponseException.class)
  public ProblemDetail handleTimeSheetsError(RestClientResponseException ex) {
    String detail = "Time-sheets service returned an error";
    try {
      ProblemDetail downstream = ex.getResponseBodyAs(ProblemDetail.class);
      if (downstream != null && downstream.getDetail() != null) {
        detail = "Time-sheets: " + downstream.getDetail();
      }
    } catch (Exception ignored) {}
    return ProblemDetail.forStatusAndDetail(ex.getStatusCode(), detail);
  }

  @ExceptionHandler(ResourceAccessException.class)
  public ProblemDetail handleTimeSheetsUnavailable(ResourceAccessException ex) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE,
            "Time-sheets service is unavailable. Please try again later.");
  }
}
