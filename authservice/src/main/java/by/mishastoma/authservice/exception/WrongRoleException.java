package by.mishastoma.authservice.exception;

public class WrongRoleException extends RuntimeException {

    public WrongRoleException(String message) {
        super(message);
    }
}
