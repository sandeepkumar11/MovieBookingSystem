package com.movie.booking.movie_service.config;

import com.movie.booking.movie_service.entity.Movie;
import com.movie.booking.movie_service.helper.MovieId;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * This class is a callback that is triggered before a Movie entity is converted to a MongoDB document.
 * It ensures that the Movie entity has a valid ID before it is saved to the database.
 * If the ID is null or empty, it generates a new ID using the MovieId helper class.
 * @author Sandeep Kumar
 * @version 1.0
 */
@Component
public class MovieBeforeConvertCallback implements BeforeConvertCallback<Movie> {
    @Override
    public @NonNull Movie onBeforeConvert(@NonNull Movie movie, @NonNull String collection) {
        if (movie.getId() == null || movie.getId().isEmpty()) {
            movie.setId(MovieId.generateId());
        }
        return movie;
    }
}
