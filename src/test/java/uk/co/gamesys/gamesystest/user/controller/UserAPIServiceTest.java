package uk.co.gamesys.gamesystest.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import uk.co.gamesys.gamesystest.domain.User;
import uk.co.gamesys.gamesystest.request.UserRequest;
import uk.co.gamesys.gamesystest.user.mapper.UserMapper;
import uk.co.gamesys.gamesystest.user.operation.RegisterUserService;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.just;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserAPIService class suite test")
class UserAPIServiceTest {
    private UserAPIService userAPIService;

    @Mock
    private RegisterUserService registerUserService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userAPIService = new UserAPIServiceImpl(registerUserService, userMapper);
    }

    @Test
    @DisplayName("Should create new user from request")
    void givenUserRequest_whenRegister_thenShouldSucceed() {
        //given
        UserRequest request = dummyRequest();
        mockServices();

        //when
        Mono<ResponseEntity<Void>> create = userAPIService.create(request);

        //then
        StepVerifier
                .create(create)
                .assertNext(user -> {
                    assertNotNull(user);
                    verify(userMapper).toUser(eq(request));
                    verify(registerUserService).create(eq(userMapper.toUser(request)));
                })
                .expectComplete()
                .verify();
    }

    private void mockServices() {
        doReturn(dummyUser())
                .when(userMapper).toUser(any());
        /*doReturn(dummyResponse())
                .when(userMapper).toResponse(any());*/
        doReturn(just(dummyUser()))
                .when(registerUserService).create(any());
    }


    private User dummyUser() {
        return User.builder()
                .password("SHa1".toCharArray())
                .username("user")
                .dateOfBirth(parse("1991-09-05", ISO_LOCAL_DATE))
                .ssn("12312312")
                .build();
    }


    /*private UserResponse dummyResponse() {
        return UserResponse.builder()
                .dateOfBirth(now())
                .ssn("12312312")
                .build();
    }*/

    private UserRequest dummyRequest() {
        return UserRequest.builder()
                .password("SHa1".toCharArray())
                .username("user")
                .dateOfBirth("1991-09-05")
                .ssn("12312312")
                .build();
    }
}
