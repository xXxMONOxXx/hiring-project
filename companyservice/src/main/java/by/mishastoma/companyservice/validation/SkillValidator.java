package by.mishastoma.companyservice.validation;

import by.mishastoma.companyservice.repository.SkillRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SkillValidator implements ConstraintValidator<SkillExistsConstraint, Long> {

    private final SkillRepository skillRepository;

    @Override
    public void initialize(SkillExistsConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        boolean valid = skillRepository.existsById(value);
        if (!valid) {
            ((ConstraintValidatorContextImpl) context).addMessageParameter("id", value);
        }
        return valid;
    }
}
