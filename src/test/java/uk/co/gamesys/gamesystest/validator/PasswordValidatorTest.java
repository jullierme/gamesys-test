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

@DisplayName("PasswordValidator class test suite")
class PasswordValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"asd", "AAAA", "s1a2", "@adfA", "54456", "54456aaa", "54456AAA"})
    void givenInvalidPassword_whenIsValid_thenShouldHaveViolation(String password) {
        PasswordHelperTest passwordHelperTest = new PasswordHelperTest(password.toCharArray());

        Set<ConstraintViolation<PasswordHelperTest>> constraintViolations =
                validator.validate(passwordHelperTest);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "Password must have at least four characters, "
                        + "at least one upper case character, "
                        + "at least one lower case character and "
                        + "at least one number",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"Aa12", "@SAsD151", "a1A2"})
    void givenValidPassword_whenIsValid_thenShouldHaveNoViolation(String password) {
        PasswordHelperTest passwordHelperTest = new PasswordHelperTest(password.toCharArray());

        Set<ConstraintViolation<PasswordHelperTest>> constraintViolations =
                validator.validate(passwordHelperTest);

        assertEquals(0, constraintViolations.size());
    }
}
