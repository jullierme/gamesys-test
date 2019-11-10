package uk.co.gamesys.gamesystest.user.mapper;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import uk.co.gamesys.gamesystest.domain.User;
import uk.co.gamesys.gamesystest.request.UserRequest;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Component
@AllArgsConstructor
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(@NonNull UserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .ssn(request.getSsn())
                .dateOfBirth(parse(request.getDateOfBirth(), ISO_LOCAL_DATE))
                .build();
    }

   /* @Override
    public UserResponse toResponse(@NonNull User user) {
        return UserResponse.builder()
                .ssn(user.getSsn())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }*/
}
