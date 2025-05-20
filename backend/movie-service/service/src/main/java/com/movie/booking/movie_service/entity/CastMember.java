package com.movie.booking.movie_service.entity;

import com.movie.booking.movie_service.enums.CastRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CastMember {
    @NotNull(message = "ID cannot be blank")
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String imageUrl;
    private String characterName;  // Only for actors and actresses
    @NotNull(message = "Role cannot be blank")
    private CastRole role; // actor, actress, director, producer
    private String description;
}
