package com.movie.booking.movie_service.integration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.booking.movie_service.model.request.MovieRequest;
import com.movie.booking.movie_service.model.response.MovieResponse;
import com.movie.booking.movie_service.repository.MovieRepository;
import com.movie.booking.movie_service.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDatabase() {
        movieRepository.deleteAll(); // Ensure clean state before each test
    }

    @Test
    void createMovie_shouldPersistAndReturnMovieResponse() throws IOException {
        String requestJson = Files.readString(Paths.get("src/test/resources/movie-request.json"));

        MovieRequest movieRequest = objectMapper.readValue(requestJson, MovieRequest.class);
        MovieResponse movieResponse = movieService.createMovie(movieRequest);

        assertThat(movieResponse).isNotNull();
        assertThat(movieResponse.name()).isEqualTo(movieRequest.getName());
        assertThat(movieResponse.rating()).isEqualTo(movieRequest.getRating());
        assertThat(movieRepository.findAll()).hasSize(1);

    }

    @Test
    void getMovieById_shouldReturnMovieResponse() throws Exception {
        String requestJson = Files.readString(Paths.get("src/test/resources/movie-request.json"));
        MovieRequest movieRequest = objectMapper.readValue(requestJson, MovieRequest.class);

        MovieResponse created = movieService.createMovie(movieRequest);

        MovieResponse found = movieService.getMovieById(created.id());
        assertThat(found).isNotNull();
        assertThat(found.id()).isEqualTo(created.id());
        assertThat(found.name()).isEqualTo(movieRequest.getName());
    }

    @Test
    void getAllMovies_shouldReturnListOfMovies() throws Exception {
        movieRepository.deleteAll(); // Ensure clean state

        String requestJson = Files.readString(Paths.get("src/test/resources/movie-request.json"));
        MovieRequest movieRequest = objectMapper.readValue(requestJson, MovieRequest.class);

        movieService.createMovie(movieRequest);

        List<MovieResponse> movies = movieService.getAllMovies();
        assertThat(movies).isNotEmpty();
        assertThat(movies.get(0).name()).isEqualTo(movieRequest.getName());
    }

    @Test
    void getActiveMovies_shouldReturnOnlyActiveMovies() throws Exception {
        String requestJson = Files.readString(Paths.get("src/test/resources/movie-request.json"));
        MovieRequest activeRequest = objectMapper.readValue(requestJson, MovieRequest.class);

        // Create active movie
        movieService.createMovie(activeRequest);

        // Create inactive movie
        MovieRequest inactiveRequest = objectMapper.readValue(requestJson, MovieRequest.class);
        inactiveRequest.setIsActive("false");
        movieService.createMovie(inactiveRequest);

        List<MovieResponse> activeMovies = movieService.getActiveMovies();
        assertThat(activeMovies).hasSize(1);
        assertThat(activeMovies.get(0).isActive()).isTrue();
    }

    @Test
    void getInactiveMovies_shouldReturnOnlyInactiveMovies() throws Exception {
        String requestJson = Files.readString(Paths.get("src/test/resources/movie-request.json"));
        MovieRequest activeRequest = objectMapper.readValue(requestJson, MovieRequest.class);

        // Create active movie
        movieService.createMovie(activeRequest);

        // Create inactive movie
        MovieRequest inactiveRequest = objectMapper.readValue(requestJson, MovieRequest.class);
        inactiveRequest.setIsActive("false");
        movieService.createMovie(inactiveRequest);

        List<MovieResponse> inactiveMovies = movieService.getInactiveMovies();
        assertThat(inactiveMovies).hasSize(1);
        assertThat(inactiveMovies.get(0).isActive()).isFalse();
    }

    @Test
    void updateMovie_shouldUpdateAndReturnMovieResponse() throws Exception {
        String requestJson = Files.readString(Paths.get("src/test/resources/movie-request.json"));
        MovieRequest movieRequest = objectMapper.readValue(requestJson, MovieRequest.class);

        // Create movie
        MovieResponse created = movieService.createMovie(movieRequest);

        // Update movie name
        movieRequest.setName("Updated Movie Name");
        MovieResponse updated = movieService.updateMovie(created.id(), movieRequest);

        assertThat(updated).isNotNull();
        assertThat(updated.id()).isEqualTo(created.id());
        assertThat(updated.name()).isEqualTo("Updated Movie Name");
    }

    @Test
    void deleteMovie_shouldSoftDeleteMovie() throws Exception {
        String requestJson = Files.readString(Paths.get("src/test/resources/movie-request.json"));
        MovieRequest movieRequest = objectMapper.readValue(requestJson, MovieRequest.class);

        // Create movie
        MovieResponse created = movieService.createMovie(movieRequest);

        // Soft delete movie
        boolean deleted = movieService.deleteMovie(created.id());
        assertThat(deleted).isTrue();

        // The movie should still exist, but isActive should be false
        var movieOpt = movieRepository.findById(created.id());
        assertThat(movieOpt).isPresent();
        assertThat(movieOpt.get().isActive()).isFalse();
    }
}
