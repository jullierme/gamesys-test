package uk.co.gamesys.gamesystest.user.validation.duplication;

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

import static java.util.Collections.synchronizedSet;
import static java.util.stream.Stream.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("DuplicationService class test suite")
class DuplicationServiceTest {
    private DuplicationService service;
    private Set<User> usersCollection;

    @BeforeEach
    void setUp() {
        usersCollection = synchronizedSet(new HashSet<>());
        service = new DuplicationServiceImpl(usersCollection);
    }

    @Test
    @DisplayName("Should return true when user is not duplicated")
    void givenNonDuplicateUser_whenValidate_thenShouldReturnTrue() {
        //given
        LocalDate dateOfBirth = LocalDate.of(1991, 9, 5);
        String ssn = "11223364";

        //when
        boolean validate =
                service.validate(dateOfBirth, ssn);

        //then
        assertTrue(validate);
    }

    @Test
    @DisplayName("Should return false when user is duplicated")
    void givenExcludedUser_whenValidate_thenShouldReturnFalse() {
        //given
        LocalDate dateOfBirth = LocalDate.of(1991, 9, 5);
        String ssn = "123123";

        User user = User
                .builder()
                .dateOfBirth(dateOfBirth)
                .ssn(ssn)
                .build();

        usersCollection.add(user);

        //when
        boolean validate =
                service.validate(dateOfBirth, ssn);

        //then
        assertFalse(validate);
    }

    @ParameterizedTest
    @MethodSource("invalidParameters")
    @DisplayName("Should not accept invalid parameters")
    void givenInvalidDateOfBirthAndSsn_whenValidate_thenShouldThrowException(
            LocalDate dateOfBirth,
            String ssn) {
        //given parameters
        //when
        Executable executable = () -> service.validate(dateOfBirth, ssn);

        //then
        assertThrows(IllegalArgumentException.class, executable);
    }

    private static Stream<Arguments> invalidParameters() {
        return of(
                arguments(LocalDate.of(1991, 9, 5), null),
                arguments(null, "12312312"),
                arguments(null, null)
        );
    }
}
