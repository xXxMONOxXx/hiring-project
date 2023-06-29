package by.mishastoma.companyservice.validation;

import by.mishastoma.companyservice.repository.OrderPositionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPositionValidator implements ConstraintValidator<OrderPositionIdExistsConstraint, Long> {

    private final OrderPositionRepository orderPositionRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return orderPositionRepository.existsById(value);
    }
}
