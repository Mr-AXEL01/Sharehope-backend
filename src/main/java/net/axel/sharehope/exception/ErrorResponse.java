package net.axel.sharehope.exception;

import java.time.Instant;

public record ErrorResponse(
        int statusCode,
        Instant timestamp,
        String message,
        String description,
        Object errors
) {}
