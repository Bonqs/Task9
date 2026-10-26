package bootcamp.hibernate_practical.exception;

import java.time.Instant;

public record ApiError(int status, String error, String message, Instant timestamp) {
}
