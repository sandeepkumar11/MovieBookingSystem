package com.movie.booking.movie_service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CastRole {
    ACTOR("Actor"),
    ACTRESS("Actress"),
    DIRECTOR("Director"),
    PRODUCER("Producer"),
    WRITER("Writer"),
    MUSIC_DIRECTOR("Music Director"),
    SINGER("Singer"),
    DIALOGUE_WRITER("Dialogue Writer"),
    SCREENPLAY_WRITER("Screenplay Writer"),
    STORY_WRITER("Story Writer");

    private final String role;

    CastRole(String role) {
        this.role = role;
    }

    @JsonValue
    public String getRole() {
        return role;
    }

    @JsonCreator
    public static CastRole fromValue(String value) {
        for (CastRole role : CastRole.values()) {
            if (role.getRole().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}
