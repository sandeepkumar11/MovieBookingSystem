package com.movie.booking.movie_service.exceptions;

import com.movie.booking.movie_service.model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GenericException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse response = new ApiResponse(ex.getMessage(), LocalDateTime.now(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MovieCreationException.class)
    public ResponseEntity<ApiResponse> handleMovieCreationException(MovieCreationException ex) {
        ApiResponse response = new ApiResponse(ex.getMessage(), LocalDateTime.now(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
