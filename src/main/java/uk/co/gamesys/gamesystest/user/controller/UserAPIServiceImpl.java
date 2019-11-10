package uk.co.gamesys.gamesystest.user.controller;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import uk.co.gamesys.gamesystest.domain.User;
import uk.co.gamesys.gamesystest.request.UserRequest;
import uk.co.gamesys.gamesystest.user.mapper.UserMapper;
import uk.co.gamesys.gamesystest.user.operation.RegisterUserService;

import java.net.URI;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.ResponseEntity.created;
import static reactor.core.publisher.Mono.just;

@AllArgsConstructor
@Service
public class UserAPIServiceImpl implements UserAPIService {
    private final RegisterUserService registerUserService;
    private final UserMapper userMapper;

    @Override
    public Mono<ResponseEntity<Void>> create(@NonNull UserRequest request) {
        return just(request)
                .map(userMapper::toUser)
                .flatMap(registerUserService::create)
                .map(user -> created(buildUri(user)).build());
    }

    private URI buildUri(User response) {
        return URI.create("/api/user/"
                + response.getDateOfBirth().format(DateTimeFormatter.ISO_LOCAL_DATE)
                + "/"
                + response.getSsn());
    }
}
