package uk.co.gamesys.gamesystest.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateISO8601Validator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateISO8601 {

    String message() default "Date field must be in ISO 8601 format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
