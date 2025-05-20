package com.movie.booking.movie_service.model.response;

import lombok.Value;

import java.time.Instant;
import java.util.List;

@Value
public class MovieResponse {
    String id;
    String name;
    List<String> genres;
    List<String> languages;
    int duration;
    List<CastResponse> directors;
    List<CastResponse> producers;
    List<CastResponse> cast;
    String description;
    String posterUrl;
    double rating;
    boolean isActive;
    String trailerUrl;
    String movieUrl;
    String country;
    Instant releaseDate;
    Instant createdAt;
    Instant updatedAt;
}
