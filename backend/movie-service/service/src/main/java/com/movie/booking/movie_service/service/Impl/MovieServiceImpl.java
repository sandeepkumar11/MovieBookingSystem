package com.movie.booking.movie_service.service.Impl;

import com.movie.booking.movie_service.entity.Movie;
import com.movie.booking.movie_service.mapper.MovieMapper;
import com.movie.booking.movie_service.model.request.MovieRequest;
import com.movie.booking.movie_service.model.response.MovieResponse;
import com.movie.booking.movie_service.repository.MovieRepository;
import com.movie.booking.movie_service.service.MovieService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Optional<MovieResponse> createMovie(MovieRequest movieRequest) {
        Movie movie = MovieMapper.toEntity(movieRequest);
        movie = movieRepository.save(movie);
        return Optional.of(MovieMapper.toResponse(movie));
    }

    @Override
    public Optional<MovieResponse> getMovieById(String movieId) {
        return movieRepository.findById(movieId)
                .map(MovieMapper::toResponse);
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(MovieMapper::toResponse)
                .toList();
    }

    @Override
    public List<MovieResponse> getActiveMovies() {
        return movieRepository.findAllByIsActive(true).stream()
                .map(MovieMapper::toResponse)
                .toList();
    }

    @Override
    public List<MovieResponse> getInactiveMovies() {
        return movieRepository.findAllByIsActive(false).stream()
                .map(MovieMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<MovieResponse> updateMovie(String movieId, MovieRequest movieRequest) {
        return movieRepository.findById(movieId)
                .map(movie -> {
                    MovieMapper.updateEntity(movie, movieRequest);
                    movie = movieRepository.save(movie);
                    return MovieMapper.toResponse(movie);
                });
    }

    @Override
    public boolean deleteMovie(String movieId) {
        return movieRepository.findById(movieId).map(movie -> {
            movie.setActive(false);
            movieRepository.save(movie);
            return true;
        }).orElse(false);
    }

    @Override
    public List<MovieResponse> searchMovies(String query) {
        return movieRepository.findAll().stream()
                .filter(movie -> movie.getName().toLowerCase().contains(query.toLowerCase()))
                .map(MovieMapper::toResponse)
                .toList();
    }

    @Override
    public List<MovieResponse> filterMoviesByGenre(String genre) {
        return movieRepository.findAllByGenresContaining(genre).stream()
                .map(MovieMapper::toResponse)
                .toList();
    }

    @Override
    public List<MovieResponse> filterMoviesByLanguage(String language) {
        return movieRepository.findAllByLanguage(language).stream()
                .map(MovieMapper::toResponse)
                .toList();
    }

    @Override
    public List<MovieResponse> filterMoviesByReleaseDate(Instant startDate, Instant endDate) {
        return movieRepository.findAllByReleaseDateBetween(startDate, endDate).stream()
                .map(MovieMapper::toResponse)
                .toList();
    }
}
