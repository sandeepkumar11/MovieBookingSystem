package com.movie.booking.movie_service.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class MovieRequest {
    private String name;
    private List<String> genres;
    private List<String> languages;
    private int duration;
    private List<CastRequest> directors;
    private List<CastRequest> producers;
    private List<CastRequest> cast;
    private String description;
    private String posterUrl;
    private double rating;
    private String isActive;
    private String trailerUrl;
    private String movieUrl;
    private String country;
    private Instant releaseDate;
}
