package com.movie.booking.movie_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * MongoDB configuration class to enable auditing features.
 * This class is responsible for enabling the auditing features in MongoDB.
 * It uses the @EnableMongoAuditing annotation to enable auditing.
 */

@Configuration
@EnableMongoAuditing
public class MongoConfig {
}
