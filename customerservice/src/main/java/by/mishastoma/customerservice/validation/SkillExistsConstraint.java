package by.mishastoma.customerservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SkillValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SkillExistsConstraint {
    String message() default "Skill '{id}' not found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
