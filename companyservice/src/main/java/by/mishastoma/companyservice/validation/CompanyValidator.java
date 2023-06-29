package by.mishastoma.companyservice.validation;

import by.mishastoma.companyservice.repository.CompanyRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyValidator implements ConstraintValidator<CompanyExistsConstraint, Long> {

    private final CompanyRepository companyRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return companyRepository.existsById(value);
    }
}
