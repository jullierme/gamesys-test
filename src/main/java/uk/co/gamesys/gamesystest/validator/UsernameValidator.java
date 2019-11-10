package uk.co.gamesys.gamesystest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class UsernameValidator implements ConstraintValidator<Username, String> {

    private static final Pattern USERNAME_PATTERN =
            compile("^[a-zA-Z0-9]*$");

    @Override
    public void initialize(Username constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username,
                           ConstraintValidatorContext constraintValidatorContext) {

        return username != null
                && USERNAME_PATTERN.matcher(username).matches();
    }
}
