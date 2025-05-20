package com.movie.booking.movie_service.mapper;

import com.movie.booking.movie_service.entity.CastMember;
import com.movie.booking.movie_service.model.response.CastResponse;

public class CastMapper {
    public static CastResponse toResponse(CastMember castMember) {
        return new CastResponse(
                castMember.getId(),
                castMember.getName(),
                castMember.getImageUrl(),
                castMember.getCharacterName(),
                castMember.getRole(),
                castMember.getDescription()
        );
    }
}
