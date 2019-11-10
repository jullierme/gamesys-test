package uk.co.gamesys.gamesystest.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "Password must have at least four characters, "
            + "at least one upper case character, "
            + "at least one lower case character and "
            + "at least one number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
