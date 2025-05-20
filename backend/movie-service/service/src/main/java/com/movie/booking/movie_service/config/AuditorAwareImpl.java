package com.movie.booking.movie_service.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * AuditorAwareImpl is a Spring component that provides the current auditor's information.
 * It implements the AuditorAware interface to return the current user's username or system user.
 */

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public @NonNull Optional<String> getCurrentAuditor() {
        // Return the current user or system user as the auditor
        return Optional.of("admin"); // TODO: Replace with actual user retrieval logic
    }
}
