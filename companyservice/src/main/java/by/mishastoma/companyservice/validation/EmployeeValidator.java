package by.mishastoma.companyservice.validation;

import by.mishastoma.companyservice.repository.EmployeeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeValidator implements ConstraintValidator<EmployeeExistsConstraint, Long> {

    private final EmployeeRepository employeeRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return employeeRepository.existsById(value);
    }
}
