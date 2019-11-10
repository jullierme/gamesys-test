package uk.co.gamesys.gamesystest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class PasswordValidator implements ConstraintValidator<Password, char[]> {

    private static final Pattern PASSWORD_PATTERN =
            compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$");

    @Override
    public void initialize(Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(char[] password,
                           ConstraintValidatorContext constraintValidatorContext) {

        return password != null
                && password.length >= 4
                && PASSWORD_PATTERN.matcher(new String(password)).matches();
    }
}
