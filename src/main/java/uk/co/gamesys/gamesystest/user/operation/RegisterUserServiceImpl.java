package uk.co.gamesys.gamesystest.user.operation;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import uk.co.gamesys.gamesystest.domain.User;
import uk.co.gamesys.gamesystest.user.validation.duplication.DuplicationService;
import uk.co.gamesys.gamesystest.user.validation.exclusion.ExclusionService;

import java.util.Set;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static reactor.core.publisher.Mono.just;
import static uk.co.gamesys.gamesystest.config.DataProviderConfiguration.USERS_COLLECTION;

@Service
@AllArgsConstructor
public class RegisterUserServiceImpl implements RegisterUserService {
    @Qualifier(USERS_COLLECTION)
    private final Set<User> usersCollection;
    private final ExclusionService exclusionService;
    private final DuplicationService duplicationService;

    @Override
    public Mono<User> create(@NonNull User newUser) {
        return just(newUser)
                .doOnNext(this::validateExcludedUser)
                .doOnNext(this::validateDuplicateUser)
                .doOnNext(usersCollection::add);
    }

    private void validateDuplicateUser(User user) {
        if (!duplicationService.validate(user.getDateOfBirth(), user.getSsn())) {
            throw new IllegalStateException("User already exists");
        }
    }

    private void validateExcludedUser(User user) {
        /*Converting LocalDate to string because the given interface has dateOfBirth as string*/
        String dateOfBirth = user.getDateOfBirth().format(ISO_LOCAL_DATE);

        if (!exclusionService.validate(dateOfBirth, user.getSsn())) {
            throw new IllegalStateException("User is blocked");
        }
    }
}
