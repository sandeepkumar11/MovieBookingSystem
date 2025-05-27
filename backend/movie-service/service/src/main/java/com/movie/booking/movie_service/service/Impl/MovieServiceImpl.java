package com.movie.booking.movie_service.service.Impl;

import com.movie.booking.movie_service.entity.Movie;
import com.movie.booking.movie_service.exceptions.MovieCreationException;
import com.movie.booking.movie_service.exceptions.ResourceNotFoundException;
import com.movie.booking.movie_service.mapper.MovieMapper;
import com.movie.booking.movie_service.model.request.MovieRequest;
import com.movie.booking.movie_service.model.response.MovieResponse;
import com.movie.booking.movie_service.repository.MovieRepository;
import com.movie.booking.movie_service.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

// TODO: Add Pagination and Sorting

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieResponse createMovie(MovieRequest movieRequest) {
        Movie movie = MovieMapper.toEntity(movieRequest);
        Movie savedMovie = movieRepository.save(movie);
        try {
            log.info("Movie created successfully with Name: {}", savedMovie.getName());
        } catch (Exception e) {
            log.error("Failed to create movie: {}", movieRequest, e);
            throw new MovieCreationException("Failed to create movie.", e);
        }
        return MovieMapper.toResponse(savedMovie);
    }

    @Override
    public MovieResponse getMovieById(String movieId) {
        return movieRepository.findById(movieId)
                .map(MovieMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + movieId));
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        log.warn("Add pagination and sorting to getAllMovies method.");
        return Optional.of(movieRepository.findAll().stream()
                        .map(MovieMapper::toResponse)
                        .toList())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No movies found."));
    }

    @Override
    public List<MovieResponse> getActiveMovies() {
        return Optional.of(movieRepository.findAllByIsActive(true).stream()
                        .map(MovieMapper::toResponse)
                        .toList())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No active movies found."));
    }

    @Override
    public List<MovieResponse> getInactiveMovies() {
        return Optional.of(movieRepository.findAllByIsActive(false).stream()
                        .map(MovieMapper::toResponse)
                        .toList())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No inactive movies found."));
    }

    @Override
    public MovieResponse updateMovie(String movieId, MovieRequest movieRequest) {
        log.info("Get update movie request for movieId: {}", movieId);
        return movieRepository.findById(movieId)
                .map(movie -> {
                    MovieMapper.updateEntity(movie, movieRequest);
                    movie = movieRepository.save(movie);
                    return MovieMapper.toResponse(movie);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + movieId));
    }

    @Override
    public boolean deleteMovie(String movieId) {
        log.info("Get delete movie request for movieId: {}", movieId);
        return movieRepository.findById(movieId)
                .map(movie -> {
                    movie.setActive(false);
                    movieRepository.save(movie);
                    return true;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + movieId));
    }

    @Override
    public List<MovieResponse> searchMovies(String query) {
        return Optional.of(movieRepository.findAll().stream()
                        .filter(movie -> movie.getName().toLowerCase().contains(query.toLowerCase()))
                        .map(MovieMapper::toResponse)
                        .toList())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with query: " + query));
    }

    @Override
    public List<MovieResponse> filterMoviesByGenre(String genre) {
        return Optional.of(movieRepository.findAllByGenresContaining(genre).stream()
                        .map(MovieMapper::toResponse)
                        .toList())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No movies found with genre: " + genre));
    }

    @Override
    public List<MovieResponse> filterMoviesByLanguage(String language) {
        return Optional.of(movieRepository.findAllByLanguage(language).stream()
                        .map(MovieMapper::toResponse)
                        .toList())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No movies found with language: " + language));
    }

    @Override
    public List<MovieResponse> filterMoviesByReleaseDate(Instant startDate, Instant endDate) {
        return Optional.of(movieRepository.findAllByReleaseDateBetween(startDate, endDate).stream()
                        .map(MovieMapper::toResponse)
                        .toList())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No movies found between release dates: " + startDate + " and " + endDate));
    }
}
