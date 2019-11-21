package uk.co.gamesys.gamesystest.user.validation.duplication;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uk.co.gamesys.gamesystest.domain.User;

import java.time.LocalDate;
import java.util.Set;

import static uk.co.gamesys.gamesystest.config.DataProviderConfiguration.USERS_COLLECTION;

@Service
@AllArgsConstructor
public class DuplicationServiceImpl implements DuplicationService {
    @Qualifier(USERS_COLLECTION)
    private final Set<User> usersCollection;

    @Override
    public boolean validate(@NonNull LocalDate dateOfBirth,
                            @NonNull String ssn) {
        final User user = User.builder()
                .dateOfBirth(dateOfBirth)
                .ssn(ssn)
                .build();

        return !usersCollection.contains(user);
    }
}
