package uk.co.gamesys.gamesystest.user.operation;

import reactor.core.publisher.Mono;
import uk.co.gamesys.gamesystest.domain.User;

public interface RegisterUserService {
    Mono<User> create(User user);
}
