package uk.co.gamesys.gamesystest.user.controller;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import uk.co.gamesys.gamesystest.request.UserRequest;

public interface UserAPIService {
    Mono<ResponseEntity<Void>> create(UserRequest request);
}
