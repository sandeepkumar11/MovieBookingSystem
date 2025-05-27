package com.movie.booking.movie_service.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.booking.movie_service.controller.MovieController;
import com.movie.booking.movie_service.model.request.MovieRequest;
import com.movie.booking.movie_service.model.response.MovieResponse;
import com.movie.booking.movie_service.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("removal")
    @MockBean
    private MovieService movieService;

    @Test
    void createMovie_shouldReturnCreated() throws Exception {
        String requestJson = Files.readString(Paths.get("src/test/resources/movie-request.json"));
        String responseJson = Files.readString(Paths.get("src/test/resources/movie-response.json"));

        MovieRequest movieRequest = objectMapper.readValue(requestJson, MovieRequest.class);
        MovieResponse movieResponse = objectMapper.readValue(responseJson, MovieResponse.class);

        Mockito.when(movieService.createMovie(Mockito.any())).thenReturn(movieResponse);

        mockMvc.perform(post("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Movie created successfully."))
                .andExpect(jsonPath("$.data.name").value("Inception"));
    }

    @Test
    void getMovieById_shouldReturnOk() throws Exception {
        String movieId = "1";
        String responseJson = Files.readString(Paths.get("src/test/resources/movie-response.json"));
        MovieResponse movieResponse = objectMapper.readValue(responseJson, MovieResponse.class);

        Mockito.when(movieService.getMovieById(movieId)).thenReturn(movieResponse);

        mockMvc.perform(get("/api/v1/movies/{movieId}", movieId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Movie retrieved successfully."))
                .andExpect(jsonPath("$.data.name").value("Inception"));
    }

    @Test
    void getAllMovies_shouldReturnOk() throws Exception {
        Mockito.when(movieService.getAllMovies()).thenReturn(java.util.Collections.emptyList());
        mockMvc.perform(get("/api/v1/movies/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getActiveMovies_shouldReturnOk() throws Exception {
        Mockito.when(movieService.getActiveMovies()).thenReturn(java.util.Collections.emptyList());
        mockMvc.perform(get("/api/v1/movies/active")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Active movies retrieved successfully."));
    }

    @Test
    void getInactiveMovies_shouldReturnOk() throws Exception {
        Mockito.when(movieService.getInactiveMovies()).thenReturn(java.util.Collections.emptyList());
        mockMvc.perform(get("/api/v1/movies/inactive")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Inactive movies retrieved successfully."));
    }

    @Test
    void updateMovie_shouldReturnOk() throws Exception {
        String movieId = "1";
        String requestJson = Files.readString(Paths.get("src/test/resources/movie-request.json"));
        String responseJson = Files.readString(Paths.get("src/test/resources/movie-response.json"));

        MovieRequest movieRequest = objectMapper.readValue(requestJson, MovieRequest.class);
        MovieResponse movieResponse = objectMapper.readValue(responseJson, MovieResponse.class);

        Mockito.when(movieService.updateMovie(Mockito.eq(movieId), Mockito.any())).thenReturn(movieResponse);

        mockMvc.perform(put("/api/v1/movies/{movieId}", movieId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Movie updated successfully."))
                .andExpect(jsonPath("$.data.name").value("Inception"));
    }

    @Test
    void deleteMovie_shouldReturnNoContent() throws Exception {
        String movieId = "1";
        Mockito.when(movieService.deleteMovie(movieId)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/movies/{movieId}", movieId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteMovie_shouldReturnNotFound() throws Exception {
        String movieId = "999";
        Mockito.when(movieService.deleteMovie(movieId)).thenReturn(false);

        mockMvc.perform(delete("/api/v1/movies/{movieId}", movieId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getMovieCast_shouldReturnOk() throws Exception {
        String movieId = "1";
        String responseJson = Files.readString(Paths.get("src/test/resources/movie-response.json"));
        MovieResponse movieResponse = objectMapper.readValue(responseJson, MovieResponse.class);

        Mockito.when(movieService.getMovieById(movieId)).thenReturn(movieResponse);

        mockMvc.perform(get("/api/v1/movies/cast/{movieId}", movieId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Movie cast successfully."))
                .andExpect(jsonPath("$.data[0].name").value("Leonardo DiCaprio"));
    }

    @Test
    void getMovieCastById_shouldReturnOk() throws Exception {
        String movieId = "1";
        Long castId = 3L;
        String responseJson = Files.readString(Paths.get("src/test/resources/movie-response.json"));
        MovieResponse movieResponse = objectMapper.readValue(responseJson, MovieResponse.class);

        Mockito.when(movieService.getMovieById(movieId)).thenReturn(movieResponse);

        mockMvc.perform(get("/api/v1/movies/cast/{movieId}/{castId}", movieId, castId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Movie cast retrieved successfully."))
                .andExpect(jsonPath("$.data.name").value("Leonardo DiCaprio"));
    }
}
