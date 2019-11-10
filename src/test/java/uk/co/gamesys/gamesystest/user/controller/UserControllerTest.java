package uk.co.gamesys.gamesystest.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.reactive.server.WebTestClient;
import uk.co.gamesys.gamesystest.request.UserRequest;
import uk.co.gamesys.gamesystest.user.validation.duplication.DuplicationService;
import uk.co.gamesys.gamesystest.user.validation.exclusion.ExclusionService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@WebFluxTest
@ComponentScan("uk.co.gamesys.gamesystest")
@DisplayName("UserController class test suite")
class UserControllerTest {
    @Autowired
    private WebTestClient client;

    @MockBean
    private ExclusionService exclusionService;

    @MockBean
    private DuplicationService duplicationService;


    @Test
    @DisplayName("Should register valid user successfully")
    void givenUserRequest_whenPost_thenShouldRegisterUserSuccessfully() {
        //given
        UserRequest request = dummyRequest();

        String headerLocationExpected = "/api/user/" + request.getDateOfBirth() + "/" + request.getSsn();

        mockDuplicationService(true);
        mockExclusionService(true);

        //when
        client.post()
                .uri("/api/user")
                .body(fromValue(request))
                .accept(APPLICATION_JSON)
                .exchange()
                //then
                .expectStatus()
                    .isCreated()
                .expectHeader()
                    .valueEquals("Location", headerLocationExpected);
    }

    @Test
    @DisplayName("Should not create an existing user")
    void givenDuplicatedUser_whenPost_thenShouldNotCreate() {
        //given
        UserRequest request = dummyRequest();

        mockDuplicationService(false);
        mockExclusionService(true);

        //when
        client.post()
                .uri("/api/user")
                .body(fromValue(request))
                .accept(APPLICATION_JSON)
                .exchange()
                //then
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.message")
                .isEqualTo("User already exists");
    }

    @Test
    @DisplayName("Should not create an excluded user")
    void givenExcludedUser_whenPost_thenShouldNotCreate() {
        //given
        UserRequest request = dummyRequest();

        mockDuplicationService(true);
        mockExclusionService(false);

        //when
        client.post()
                .uri("/api/user")
                .body(fromValue(request))
                .accept(APPLICATION_JSON)
                .exchange()
                //then
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.message")
                .isEqualTo("User is blocked");
    }

    @ParameterizedTest
    @ValueSource(strings = {"with space", "@nonalphanumerical"})
    @DisplayName("Should have username as alphanumerical and no spaces")
    void givenUserRequest_whenPost_thenShouldValidateUsername(String username) {
        //given parameters
        mockDuplicationService(true);
        mockExclusionService(true);
        String expectedMessage = "Username must be alphanumerical and has no spaces";

        //when
        UserRequest request = UserRequest.builder()
                .username(username)
                .ssn("123123")
                .password("Sha1".toCharArray())
                .dateOfBirth("1815-12-10")
                .build();

        //when
        client.post()
                .uri("/api/user")
                .body(fromValue(request))
                .accept(APPLICATION_JSON)
                .exchange()
                //then
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.message")
                .value(message ->
                        assertTrue(message.toString().contains(expectedMessage)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"L0l", "lowercase1", "UPPERCASE1", "toSimple"})
    @DisplayName("Should have password with "
            + "at least four characters, "
            + "at least one upper case character, "
            + "at least one lower case character, "
            + "at least one number")
    void givenUserRequest_whenPost_thenShouldValidatePassword(String password) {
        //given parameters
        mockDuplicationService(true);
        mockExclusionService(true);

        String expectedMessage = "Password must have "
                + "at least four characters, "
                + "at least one upper case character, "
                + "at least one lower case character and "
                + "at least one number";

        //when
        UserRequest request = UserRequest.builder()
                .username("username")
                .ssn("123123")
                .password(password.toCharArray())
                .dateOfBirth("1815-12-10")
                .build();

        //when
        client.post()
                .uri("/api/user")
                .body(fromValue(request))
                .accept(APPLICATION_JSON)
                .exchange()
                //then
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.message")
                .value(message ->
                        assertTrue(message.toString().contains(expectedMessage)));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "05/09/1991", "05-09-1991", "1999-13-12", "5th Sep", "5 September 1991"
    })
    @DisplayName("Should have dateOfBirth in ISO 8601")
    void givenRegisterUser_whenPost_thenShouldValidateDateOfBirth(String date) {
        //given parameters
        mockDuplicationService(true);
        mockExclusionService(true);
        String expectedMessage = "Date field must be in ISO 8601 format";

        //when
        UserRequest request = UserRequest.builder()
                .username("username")
                .ssn("123123")
                .password("Sha1".toCharArray())
                .dateOfBirth(date)
                .build();

        //when
        client.post()
                .uri("/api/user")
                .body(fromValue(request))
                .accept(APPLICATION_JSON)
                .exchange()
                //then
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.message")
                .value(message ->
                        assertTrue(message.toString().contains(expectedMessage)));
    }

    @Test
    @DisplayName("Should validate mandatory fields")
    void givenNullFields_whenPost_thenShouldValidateMandatoryFields() {
        //given parameters
        mockDuplicationService(true);
        mockExclusionService(true);

        //when
        UserRequest request = UserRequest.builder()
                .username(null)
                .ssn(null)
                .password(null)
                .dateOfBirth(null)
                .build();

        //when
        client.post()
                .uri("/api/user")
                .body(fromValue(request))
                .accept(APPLICATION_JSON)
                .exchange()
                //then
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.message")
                .value(message ->
                        assertTrue(message.toString().contains("must not be null")));
    }

    private void mockDuplicationService(boolean toBeReturned) {
        doReturn(toBeReturned)
                .when(duplicationService)
                .validate(any(), any());
    }

    private void mockExclusionService(boolean toBeReturned) {
        doReturn(toBeReturned)
                .when(exclusionService)
                .validate(any(), any());
    }

    private UserRequest dummyRequest() {
        return UserRequest.builder()
                .username("user")
                .ssn("123123223")
                .password("Sha1".toCharArray())
                .dateOfBirth("1815-12-10")
                .build();
    }
}
