package com.movie.booking.movie_service.mapper;

import com.movie.booking.movie_service.entity.CastMember;
import com.movie.booking.movie_service.entity.Movie;
import com.movie.booking.movie_service.model.request.CastRequest;
import com.movie.booking.movie_service.model.request.MovieRequest;
import com.movie.booking.movie_service.model.response.MovieResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MovieMapper {
    public static Movie toEntity(MovieRequest movieRequest) {
        return Movie.builder()
                .name(movieRequest.getName())
                .genres(movieRequest.getGenres())
                .languages(movieRequest.getLanguages())
                .duration(movieRequest.getDuration())
                .directors(toCastMembers(movieRequest.getDirectors()))
                .producers(toCastMembers(movieRequest.getProducers()))
                .cast(toCastMembers(movieRequest.getCast()))
                .description(movieRequest.getDescription())
                .posterUrl(movieRequest.getPosterUrl())
                .rating(movieRequest.getRating())
                .isActive(Boolean.parseBoolean(movieRequest.getIsActive()))
                .trailerUrl(movieRequest.getTrailerUrl())
                .movieUrl(movieRequest.getMovieUrl())
                .country(movieRequest.getCountry())
                .releaseDate(movieRequest.getReleaseDate())
                .build();
    }

    public static MovieResponse toResponse(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getName(),
                movie.getGenres(),
                movie.getLanguages(),
                movie.getDuration(),
                movie.getDirectors().stream().map(CastMapper::toResponse).collect(Collectors.toList()),
                movie.getProducers().stream().map(CastMapper::toResponse).collect(Collectors.toList()),
                movie.getCast().stream().map(CastMapper::toResponse).collect(Collectors.toList()),
                movie.getDescription(),
                movie.getPosterUrl(),
                movie.getRating(),
                movie.isActive(),
                movie.getTrailerUrl(),
                movie.getMovieUrl(),
                movie.getCountry(),
                movie.getReleaseDate(),
                movie.getCreatedAt(),
                movie.getUpdatedAt()
        );
    }

    public static void updateEntity(Movie movie, MovieRequest movieRequest) {
        if (movieRequest.getName() != null) movie.setName(movieRequest.getName());
        if (movieRequest.getGenres() != null) movie.setGenres(movieRequest.getGenres());
        if (movieRequest.getLanguages() != null) movie.setLanguages(movieRequest.getLanguages());
        if (movieRequest.getDuration() != 0) movie.setDuration(movieRequest.getDuration());
        if (movieRequest.getDirectors() != null) movie.setDirectors(toCastMembers(movieRequest.getDirectors()));
        if (movieRequest.getProducers() != null) movie.setProducers(toCastMembers(movieRequest.getProducers()));
        if (movieRequest.getCast() != null) movie.setCast(toCastMembers(movieRequest.getCast()));
        if (movieRequest.getDescription() != null) movie.setDescription(movieRequest.getDescription());
        if (movieRequest.getPosterUrl() != null) movie.setPosterUrl(movieRequest.getPosterUrl());
        if (movieRequest.getRating() != 0) movie.setRating(movieRequest.getRating());
        if (movieRequest.getIsActive()!=null) movie.setActive(Boolean.parseBoolean(movieRequest.getIsActive()));
        if (movieRequest.getTrailerUrl() != null) movie.setTrailerUrl(movieRequest.getTrailerUrl());
        if (movieRequest.getMovieUrl() != null) movie.setMovieUrl(movieRequest.getMovieUrl());
        if (movieRequest.getCountry() != null) movie.setCountry(movieRequest.getCountry());
        if (movieRequest.getReleaseDate() != null) movie.setReleaseDate(movieRequest.getReleaseDate());
    }

    private static List<CastMember> toCastMembers(List<CastRequest> castRequests) {
        if (castRequests == null) return null;

        return castRequests.stream()
                .map(r -> new CastMember(
                        r.getId() != null ? r.getId() : UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE,
                        r.getName(),
                        r.getImageUrl(),
                        r.getCharacterName(),
                        r.getRole(),
                        r.getDescription()
                )).collect(Collectors.toList());
    }
}
