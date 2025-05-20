package com.movie.booking.movie_service.model.request;

import com.movie.booking.movie_service.enums.CastRole;

/**
 * @param id Optional: present if updating an existing cast member
 */
public record CastRequest(Long id, String name, String imageUrl, String characterName, CastRole role,
                          String description) {
}
