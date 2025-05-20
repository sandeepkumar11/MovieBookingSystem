package com.movie.booking.movie_service.service;

import com.movie.booking.movie_service.model.request.MovieRequest;
import com.movie.booking.movie_service.model.response.MovieResponse;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MovieService {
    Optional<MovieResponse> createMovie(MovieRequest movieRequest);

    Optional<MovieResponse> getMovieById(String movieId);

    List<MovieResponse> getAllMovies();

    List<MovieResponse> getActiveMovies();

    List<MovieResponse> getInactiveMovies();

    Optional<MovieResponse> updateMovie(String movieId, MovieRequest movieRequest);

    boolean deleteMovie(String movieId);

    List<MovieResponse> searchMovies(String query);

    List<MovieResponse> filterMoviesByGenre(String genre);

    List<MovieResponse> filterMoviesByLanguage(String language);

    List<MovieResponse> filterMoviesByReleaseDate(Instant startDate, Instant endDate);
}
