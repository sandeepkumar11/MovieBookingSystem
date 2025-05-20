package com.movie.booking.movie_service.controller;

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
        return movieService.createMovie(movieRequest)
                .map(movieResponse -> {
                    ApiResponse response = new ApiResponse("Movie created successfully.", LocalDateTime.now(), movieResponse);
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                })
                .orElseGet(() -> {
                    ApiResponse response = new ApiResponse("Failed to create movie.", LocalDateTime.now(), null);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
                });
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<ApiResponse> getMovieById(@PathVariable String movieId) {
        return movieService.getMovieById(movieId)
                .map(movieResponse -> {
                    ApiResponse response = new ApiResponse("Movie retrieved successfully.", LocalDateTime.now(), movieResponse);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse response = new ApiResponse("Movie not found.", LocalDateTime.now(), null);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
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
        return movieService.updateMovie(movieId, movieRequest)
                .map(movieResponse -> {
                    ApiResponse response = new ApiResponse("Movie updated successfully.", LocalDateTime.now(), movieResponse);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse response = new ApiResponse("Movie not found.", LocalDateTime.now(), null);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String movieId) {
        return movieService.deleteMovie(movieId)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/cast/{movieId}")
    public ResponseEntity<ApiResponse> getMovieCast(@PathVariable String movieId) {
        return movieService.getMovieById(movieId)
                .map(movieResponse -> {
                    ApiResponse response = new ApiResponse("Movie cast retrieved successfully.", LocalDateTime.now(), movieResponse.getCast());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse response = new ApiResponse("Movie not found.", LocalDateTime.now(), null);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    @GetMapping("/cast/{movieId}/{castId}")
    public ResponseEntity<ApiResponse> getMovieCastById(@PathVariable String movieId, @PathVariable Long castId) {
        return movieService.getMovieById(movieId)
                .flatMap(movieResponse -> movieResponse.getCast().stream()
                        .filter(cast -> cast.getId().equals(castId))
                        .findFirst())
                .map(castResponse -> {
                    ApiResponse response = new ApiResponse("Movie cast retrieved successfully.", LocalDateTime.now(), castResponse);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse response = new ApiResponse("Movie or cast not found.", LocalDateTime.now(), null);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }
}
