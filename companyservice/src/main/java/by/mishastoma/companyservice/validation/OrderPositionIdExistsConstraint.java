package by.mishastoma.companyservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = OrderPositionValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderPositionIdExistsConstraint {
    String message() default "Order position '{id}' does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
