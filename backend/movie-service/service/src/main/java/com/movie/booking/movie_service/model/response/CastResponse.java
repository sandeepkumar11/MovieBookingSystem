package com.movie.booking.movie_service.model.response;

import com.movie.booking.movie_service.enums.CastRole;
import lombok.Value;

@Value
public class CastResponse {
    Long id;
    String name;
    String imageUrl;
    String characterName;
    CastRole role;
    String description;
}
