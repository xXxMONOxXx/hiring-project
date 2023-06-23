package by.mishastoma.customerservice.exception;

public class OrderPositionNotFoundException extends RuntimeException {
    public OrderPositionNotFoundException(Long id) {
        super(String.format("Order position %d not found", id));
    }
}
