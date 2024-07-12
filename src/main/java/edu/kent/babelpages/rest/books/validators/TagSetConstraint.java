package edu.kent.babelpages.rest.books.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TagSetValidator.class)
@Target( { ElementType.FIELD , ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TagSetConstraint {
    String message() default "One or more tags are invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
