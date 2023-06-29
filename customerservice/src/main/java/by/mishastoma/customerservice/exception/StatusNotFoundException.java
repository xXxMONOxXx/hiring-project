package by.mishastoma.customerservice.exception;

public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException(Long id) {
        super(String.format("Status %d wasn't found", id));
    }
}
