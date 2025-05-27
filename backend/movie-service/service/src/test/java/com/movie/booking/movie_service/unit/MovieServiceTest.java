package com.movie.booking.movie_service.unit;

import com.movie.booking.movie_service.entity.Movie;
import com.movie.booking.movie_service.exceptions.MovieCreationException;
import com.movie.booking.movie_service.exceptions.ResourceNotFoundException;
import com.movie.booking.movie_service.mapper.MovieMapper;
import com.movie.booking.movie_service.model.request.MovieRequest;
import com.movie.booking.movie_service.model.response.MovieResponse;
import com.movie.booking.movie_service.repository.MovieRepository;
import com.movie.booking.movie_service.service.Impl.MovieServiceImpl;
import com.movie.booking.movie_service.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MovieServiceTest {
    private MovieRepository movieRepository;
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        movieRepository = mock(MovieRepository.class);
        movieService = new MovieServiceImpl(movieRepository);
    }

    @Test
    void createMovie_shouldReturnResponse() {
        MovieRequest request = new MovieRequest();
        request.setName("Test Movie");
        request.setIsActive("true");
        Movie movie = MovieMapper.toEntity(request);
        movie.setId("1");

        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        MovieResponse response = movieService.createMovie(request);

        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo("Test Movie");
    }

//    @Test
//    void createMovie_shouldThrowException_whenSaveFails() {
//        MovieRequest request = new MovieRequest();
//        when(movieRepository.save(any(Movie.class))).thenReturn(null);
//
//        assertThatThrownBy(() -> movieService.createMovie(request))
//                .isInstanceOf(MovieCreationException.class);
//    }
//
    @Test
    void getMovieById_shouldReturnResponse() {
        Movie movie = new Movie();
        movie.setId("1");
        movie.setName("Test");
        when(movieRepository.findById("1")).thenReturn(Optional.of(movie));

        MovieResponse response = movieService.getMovieById("1");
        assertThat(response.id()).isEqualTo("1");
    }

    @Test
    void getMovieById_shouldThrow_whenNotFound() {
        when(movieRepository.findById("1")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> movieService.getMovieById("1"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getAllMovies_shouldReturnList() {
        Movie movie = new Movie();
        movie.setId("1");
        movie.setName("Test");
        when(movieRepository.findAll()).thenReturn(List.of(movie));

        List<MovieResponse> list = movieService.getAllMovies();
        assertThat(list).hasSize(1);
    }

    @Test
    void getAllMovies_shouldThrow_whenEmpty() {
        when(movieRepository.findAll()).thenReturn(List.of());
        assertThatThrownBy(() -> movieService.getAllMovies())
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateMovie_shouldUpdateAndReturn() {
        Movie movie = new Movie();
        movie.setId("1");
        movie.setName("Old");
        MovieRequest request = new MovieRequest();
        request.setName("New");

        when(movieRepository.findById("1")).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        MovieResponse response = movieService.updateMovie("1", request);
        assertThat(response.name()).isEqualTo("New");
    }

    @Test
    void updateMovie_shouldThrow_whenNotFound() {
        MovieRequest request = new MovieRequest();
        when(movieRepository.findById("1")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> movieService.updateMovie("1", request))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteMovie_shouldSoftDeleteAndReturnTrue() {
        Movie movie = new Movie();
        movie.setId("1");
        movie.setActive(true);
        when(movieRepository.findById("1")).thenReturn(Optional.of(movie));
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        boolean result = movieService.deleteMovie("1");

        assertThat(result).isTrue();
        assertThat(movie.isActive()).isFalse();
        verify(movieRepository).save(movie);
    }

    @Test
    void deleteMovie_shouldThrow_whenNotFound() {
        when(movieRepository.findById("1")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> movieService.deleteMovie("1"))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
