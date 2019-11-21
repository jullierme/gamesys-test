package uk.co.gamesys.gamesystest.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import uk.co.gamesys.gamesystest.request.UserRequest;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@RequestMapping(
        value = "/api/user",
        produces = APPLICATION_JSON_VALUE,
        consumes = APPLICATION_JSON_VALUE
)
public class UserController {
    private final UserAPIService service;

    @PostMapping
    public Mono<ResponseEntity<Void>> create(@Valid @RequestBody UserRequest request) {
        return service.create(request);
    }
}
