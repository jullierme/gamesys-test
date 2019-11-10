package uk.co.gamesys.gamesystest.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("DateISO8601Validator class test suite")
class DateISO8601ValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"2018/10/10", "2018-15-10", "2018-10-90", "DD-MM-YYYY", ""})
    void givenInvalidDate_whenIsValid_thenShouldHaveViolation(String date) {
        DateHelperTest dateTest = new DateHelperTest(date);

        Set<ConstraintViolation<DateHelperTest>> constraintViolations =
                validator.validate(dateTest);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "Date field must be in ISO 8601 format",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"2018-10-10", "1815-12-10", "1912-06-23", "1910-06-22"})
    void givenValidDate_whenIsValid_thenShouldHaveNoViolation(String date) {
        DateHelperTest dateTest = new DateHelperTest(date);

        Set<ConstraintViolation<DateHelperTest>> constraintViolations =
                validator.validate(dateTest);

        assertEquals(0, constraintViolations.size());
    }
}


