package by.mishastoma.authservice.exception;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException(String username) {
        super("Company not found, username: " + username);
    }
}
