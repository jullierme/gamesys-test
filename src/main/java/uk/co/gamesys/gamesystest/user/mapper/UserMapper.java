package uk.co.gamesys.gamesystest.user.mapper;

import uk.co.gamesys.gamesystest.domain.User;
import uk.co.gamesys.gamesystest.request.UserRequest;

public interface UserMapper {
    User toUser(UserRequest request);
    //UserResponse toResponse(@NonNull User user);
}
