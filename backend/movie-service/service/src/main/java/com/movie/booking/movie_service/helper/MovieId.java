package com.movie.booking.movie_service.helper;

import java.util.StringJoiner;
import java.util.UUID;

public class MovieId {
    public static String generateId() {
        return new StringJoiner(":")
                .add(UUID.randomUUID().toString())
                .add(String.valueOf(System.currentTimeMillis()))
                .toString();
    }
}
