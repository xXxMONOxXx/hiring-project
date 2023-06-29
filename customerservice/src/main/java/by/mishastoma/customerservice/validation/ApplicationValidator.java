package by.mishastoma.customerservice.validation;

import by.mishastoma.customerservice.repository.ApplicationRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationValidator implements ConstraintValidator<ApplicationExistsConstraint, Long> {

    private final ApplicationRepository applicationRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        boolean valid = applicationRepository.existsById(value);
        if (!valid) {
            ((ConstraintValidatorContextImpl) context).addMessageParameter("id", value);
        }
        return valid;
    }
}
