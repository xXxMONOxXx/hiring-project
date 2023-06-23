package by.mishastoma.customerservice.exception;

public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException(Long id) {
        super(String.format("Application %d not found", id));
    }
}
