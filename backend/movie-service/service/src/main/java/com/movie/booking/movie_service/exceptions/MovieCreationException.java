package com.movie.booking.movie_service.exceptions;

public class MovieCreationException extends RuntimeException {
    public MovieCreationException(String message) {
        super(message);
    }

    public MovieCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieCreationException(Throwable cause) {
        super(cause);
    }
}
