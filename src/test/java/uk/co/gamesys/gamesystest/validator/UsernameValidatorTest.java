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

@DisplayName("UsernameValidator class test suite")
class UsernameValidatorTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {" asdf645", "asdf%", "54545@"})
    void givenInvalidUsername_whenIsValid_thenShouldHaveViolation(String username) {
        UsernameHelperTest usernameHelperTest = new UsernameHelperTest(username);

        Set<ConstraintViolation<UsernameHelperTest>> constraintViolations =
                validator.validate(usernameHelperTest);

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "Username must be alphanumerical and has no spaces",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jhon", "Silva", "Test"})
    void givenValidUsername_whenIsValid_thenShouldHaveNoViolation(String username) {
        UsernameHelperTest usernameHelperTest = new UsernameHelperTest(username);

        Set<ConstraintViolation<UsernameHelperTest>> constraintViolations =
                validator.validate(usernameHelperTest);

        assertEquals(0, constraintViolations.size());
    }
}


