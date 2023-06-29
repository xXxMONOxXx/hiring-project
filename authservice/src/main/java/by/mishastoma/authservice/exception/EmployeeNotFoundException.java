package by.mishastoma.authservice.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String username) {
        super("Employee not found, username: " + username);
    }
}
