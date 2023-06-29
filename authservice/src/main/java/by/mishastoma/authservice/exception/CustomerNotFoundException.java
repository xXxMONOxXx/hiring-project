package by.mishastoma.authservice.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String username) {
        super("Customer not found, username: " + username);
    }
}
