package uk.co.gamesys.gamesystest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.format.DateTimeParseException;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class DateISO8601Validator implements ConstraintValidator<DateISO8601, String> {

    @Override
    public void initialize(DateISO8601 constraintAnnotation) {
    }

    @Override
    public boolean isValid(String dateOfBirth,
                           ConstraintValidatorContext constraintValidatorContext) {
        try {
            return dateOfBirth != null && parse(dateOfBirth, ISO_LOCAL_DATE) != null;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
