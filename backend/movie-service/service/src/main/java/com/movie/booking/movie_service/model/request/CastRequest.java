package com.movie.booking.movie_service.model.request;

import com.movie.booking.movie_service.enums.CastRole;
import lombok.Value;

@Value
public class CastRequest {
    Long id; // Optional: present if updating an existing cast member
    String name;
    String imageUrl;
    String characterName;
    CastRole role;
    String description;
}
