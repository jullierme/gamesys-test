package uk.co.gamesys.gamesystest.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Username {

    String message() default "Username must be alphanumerical and has no spaces";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
