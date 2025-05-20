package com.movie.booking.movie_service.model.response;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ApiResponse {
    String message;
    LocalDateTime timestamp;
    Object data;
}
