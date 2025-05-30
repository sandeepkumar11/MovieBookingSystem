package com.movie.booking.movie_service.controller;

import com.movie.booking.movie_service.exceptions.ResourceNotFoundException;
import com.movie.booking.movie_service.model.request.MovieRequest;
import com.movie.booking.movie_service.model.response.ApiResponse;
import com.movie.booking.movie_service.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createMovie(@Valid @RequestBody MovieRequest movieRequest) {
        var movie = movieService.createMovie(movieRequest);
        ApiResponse response = new ApiResponse("Movie created successfully.", LocalDateTime.now(), movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<ApiResponse> getMovieById(@PathVariable String movieId) {
        var movie = movieService.getMovieById(movieId);
        ApiResponse response = new ApiResponse("Movie retrieved successfully.", LocalDateTime.now(), movie);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllMovies() {
        ApiResponse response = new ApiResponse("All movies retrieved successfully.", LocalDateTime.now(), movieService.getAllMovies());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> getActiveMovies() {
        ApiResponse response = new ApiResponse("Active movies retrieved successfully.", LocalDateTime.now(), movieService.getActiveMovies());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse> getInactiveMovies() {
        ApiResponse response = new ApiResponse("Inactive movies retrieved successfully.", LocalDateTime.now(), movieService.getInactiveMovies());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<ApiResponse> updateMovie(@PathVariable String movieId, @Valid @RequestBody MovieRequest movieRequest) {
        var movie = movieService.updateMovie(movieId, movieRequest);
        ApiResponse response = new ApiResponse("Movie updated successfully.", LocalDateTime.now(), movie);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String movieId) {
        return movieService.deleteMovie(movieId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/cast/{movieId}")
    public ResponseEntity<ApiResponse> getMovieCast(@PathVariable String movieId) {
        var movieResponse = movieService.getMovieById(movieId);
        ApiResponse response = new ApiResponse("Movie cast successfully.", LocalDateTime.now(), movieResponse.cast());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cast/{movieId}/{castId}")
    public ResponseEntity<ApiResponse> getMovieCastById(@PathVariable String movieId, @PathVariable Long castId) {
        var movieResponse = movieService.getMovieById(movieId);
        var castResponse = movieResponse.cast().stream()
                .filter(cast -> cast.getId().equals(castId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cast not found with ID: " + castId));
        ApiResponse response = new ApiResponse("Movie cast retrieved successfully.", LocalDateTime.now(), castResponse);
        return ResponseEntity.ok(response);
    }
}
