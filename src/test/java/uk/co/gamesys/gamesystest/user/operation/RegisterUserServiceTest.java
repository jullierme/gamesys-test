package uk.co.gamesys.gamesystest.user.operation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import uk.co.gamesys.gamesystest.domain.User;
import uk.co.gamesys.gamesystest.user.validation.duplication.DuplicationService;
import uk.co.gamesys.gamesystest.user.validation.exclusion.ExclusionService;

import java.util.HashSet;
import java.util.Set;

import static java.time.LocalDate.now;
import static java.util.Collections.synchronizedSet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@DisplayName("RegisterUserService class suite test")
class RegisterUserServiceTest {
    private RegisterUserService service;
    private Set<User> usersCollection;

    @Mock
    private ExclusionService exclusionService;

    @Mock
    private DuplicationService duplicationService;

    @BeforeEach
    void setUp() {
        usersCollection = synchronizedSet(new HashSet<>());
        service = new RegisterUserServiceImpl(usersCollection, exclusionService, duplicationService);
    }

    @Test
    @DisplayName("Should create new user if does not exist")
    void givenNewUser_whenCreate_thenShouldAddUserToCollection() {
        //given
        mockExclusionService(true);
        mockDuplicationService(true);

        User newUser = User.builder()
                .username("test")
                .password("SHA-1".toCharArray())
                .dateOfBirth(now())
                .ssn("123123")
                .build();

        usersCollection.remove(newUser);

        //when
        Mono<User> create = service.create(newUser);

        //then
        StepVerifier
                .create(create)
                .assertNext(user -> {
                    assertEquals(user, newUser);
                    assertTrue(usersCollection.contains(newUser));
                })
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Should throw exception if user already exists when saving")
    void givenExistingMember_whenCreate_thenShouldThrowException() {
        //given
        mockExclusionService(true);
        mockDuplicationService(false);

        User existingUser = User.builder()
                .username("test")
                .password("SHA-1".toCharArray())
                .dateOfBirth(now())
                .ssn("12231223")
                .build();

        //when
        Mono<User> create = service.create(existingUser);

        //then
        StepVerifier
                .create(create)
                .expectErrorSatisfies(error -> {
                    assertTrue(error instanceof IllegalStateException);
                    assertEquals("User already exists", error.getMessage());
                })
                .verify();
    }

    @Test
    @DisplayName("Should throw exception if user is on the excluded list when creating")
    void givenExcludedUser_whenCreate_thenShouldThrowException() {
        //given
        mockExclusionService(false);

        User existingUser = User.builder()
                .username("test")
                .password("SHA-1".toCharArray())
                .dateOfBirth(now())
                .ssn("12231223")
                .build();

        //when
        Mono<User> create = service.create(existingUser);

        //then
        StepVerifier
                .create(create)
                .expectErrorSatisfies(error -> {
                    assertTrue(error instanceof IllegalStateException);
                    assertEquals("User is blocked", error.getMessage());
                })
                .verify();
    }

    @Test
    @DisplayName("Should NOT accept invalid user when saving")
    void givenInvalidUser_whenCreate_thenShouldThrowException() {
        //given
        User invalidUser = null;

        //when
        Executable executable = () -> service.create(invalidUser);

        //then
        assertThrows(IllegalArgumentException.class, executable);
    }

    private void mockExclusionService(boolean result) {
        doReturn(result).when(exclusionService).validate(any(), any());
    }

    private void mockDuplicationService(boolean result) {
        doReturn(result).when(duplicationService).validate(any(), any());
    }
}
