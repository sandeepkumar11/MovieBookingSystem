package com.movie.booking.movie_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "movies")
public class Movie implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @Indexed(unique = true)
    @Field("movie_name")
    private String name;
    @Indexed
    @Field("movie_genres")
    private List<String> genres;
    private List<String> languages;
    private int duration;
    private List<CastMember> directors;
    private List<CastMember> producers;
    private List<CastMember> cast;
    private String description;
    private String posterUrl;
    private double rating;
    @Indexed
    private boolean isActive;
    private String trailerUrl;
    private String movieUrl;
    private String country;
    @Indexed
    private Instant releaseDate;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String updatedBy;

    @Version
    private Long version;
}