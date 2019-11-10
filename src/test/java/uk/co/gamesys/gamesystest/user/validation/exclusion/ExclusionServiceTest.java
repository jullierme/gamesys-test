package uk.co.gamesys.gamesystest.user.validation.exclusion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.co.gamesys.gamesystest.domain.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.Collections.synchronizedSet;
import static java.util.stream.Stream.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("ExclusionService class test suite")
class ExclusionServiceTest {
    private ExclusionService service;
    private Set<User> usersExcludedCollection;

    @BeforeEach
    void setUp() {
        usersExcludedCollection = synchronizedSet(new HashSet<>());
        service = new ExclusionServiceImpl(usersExcludedCollection);
    }

    @Test
    @DisplayName("Should return true when user is not excluded")
    void givenNonExcludedUser_whenValidate_thenShouldReturnTrue() {
        //given
        LocalDate dateOfBirth = LocalDate.of(1991, 9, 5);
        String ssn = "123123";

        //when
        boolean validate =
                service.validate(dateOfBirth.format(ISO_LOCAL_DATE), ssn);

        //then
        assertTrue(validate);
    }

    @Test
    @DisplayName("Should return false when user is excluded")
    void givenExcludedUser_whenValidate_thenShouldReturnFalse() {
        //given
        LocalDate dateOfBirth = LocalDate.of(1991, 9, 5);
        String ssn = "123123";

        User user = User
                .builder()
                .dateOfBirth(dateOfBirth)
                .ssn(ssn)
                .build();

        usersExcludedCollection.add(user);

        //when
        boolean validate =
                service.validate(dateOfBirth.format(ISO_LOCAL_DATE), ssn);

        //then
        assertFalse(validate);
    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    @DisplayName("Should not accept invalid parameters")
    void givenInvalidDateOfBirthAndSsn_whenValidate_thenShouldThrowException(
            String dateOfBirth,
            String ssn) {
        //given parameters
        //when
        Executable executable = () -> service.validate(dateOfBirth, ssn);

        //then
        assertThrows(IllegalArgumentException.class, executable);
    }

    private static Stream<Arguments> invalidParameters() {
        return of(
                arguments("1991-09-05", null),
                arguments(null, "12312312"),
                arguments(null, null)
        );
    }
}
