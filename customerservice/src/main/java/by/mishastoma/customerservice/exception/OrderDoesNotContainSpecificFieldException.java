package by.mishastoma.customerservice.exception;

public class OrderDoesNotContainSpecificFieldException extends RuntimeException {
    public OrderDoesNotContainSpecificFieldException(String message) {
        super(message);
    }
}
