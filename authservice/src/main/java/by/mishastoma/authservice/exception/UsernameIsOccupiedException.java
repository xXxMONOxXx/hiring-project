package by.mishastoma.authservice.exception;

public class UsernameIsOccupiedException extends RuntimeException {

    public UsernameIsOccupiedException(String username) {
        super("Username: " + username + " is already in use");
    }
}
