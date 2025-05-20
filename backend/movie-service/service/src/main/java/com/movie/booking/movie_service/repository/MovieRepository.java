package com.movie.booking.movie_service.repository;

import com.movie.booking.movie_service.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.Instant;
import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    @Query("{'isActive': ?0}")
    List<Movie> findAllByIsActive(boolean isActive);

    @Query("{'genres': ?0}")
    List<Movie> findAllByGenresContaining(String genre);

    @Query("{'language': ?0}")
    List<Movie> findAllByLanguage(String language);

    @Query("{'releaseDate': {$gte: ?0, $lte: ?1}}")
    List<Movie> findAllByReleaseDateBetween(Instant startDate, Instant endDate);
}
