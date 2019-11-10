package uk.co.gamesys.gamesystest.user.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import uk.co.gamesys.gamesystest.domain.User;
import uk.co.gamesys.gamesystest.request.UserRequest;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserMapper class suite test")
class UserMapperTest {
    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserMapperImpl();
    }

    @Test
    @DisplayName("Should map an UserRequest to User")
    void givenUserRequest_whenToUser_thenShouldMapToUser() {
        //given
        UserRequest request = UserRequest.builder()
                .username("user")
                .password("SHa1".toCharArray())
                .dateOfBirth("1991-09-05")
                .ssn("12312322")
                .build();

        //when
        User user = mapper.toUser(request);

        //then
        assertNotNull(user);
        assertEquals(request.getUsername(), user.getUsername());
        assertEquals(request.getPassword(), user.getPassword());
        assertEquals(request.getSsn(), user.getSsn());
        assertEquals(request.getDateOfBirth(),
                user.getDateOfBirth().format(ISO_LOCAL_DATE));
    }

    @Test
    @DisplayName("Should NOT accept invalid parameter when mapping toUser")
    void givenInvalidRequest_whenToUser_thenShouldThrowException() {
        //given
        UserRequest request = null;

        //when
        Executable executable = () -> mapper.toUser(request);

        //then
        assertThrows(IllegalArgumentException.class, executable);
    }

   /* @Test
    @DisplayName("Should map an User to UserResponse")
    void givenUser_whenToResponse_thenShouldMapToUser() {
        //given
        User user = User.builder()
                .username("user")
                .password("SHa1".toCharArray())
                .dateOfBirth(now())
                .ssn("12312322")
                .build();

        //when
        UserResponse userResponse = mapper.toResponse(user);

        //then
        assertNotNull(userResponse);
        assertEquals(userResponse.getSsn(), user.getSsn());
        assertEquals(userResponse.getDateOfBirth(), user.getDateOfBirth());
    }

    @Test
    @DisplayName("Should NOT accept invalid parameter when mapping toResponse")
    void givenInvalidRequest_whenToResponse_thenShouldThrowException() {
        //given
        User user = null;

        //when
        Executable executable = () -> mapper.toResponse(user);

        //then
        assertThrows(IllegalArgumentException.class, executable);
    }*/
}
