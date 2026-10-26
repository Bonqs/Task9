package bootcamp.hibernate_practical.exception;

public class InvalidBookStatusException extends RuntimeException {
    public InvalidBookStatusException(String message) {
        super(message);
    }
}
