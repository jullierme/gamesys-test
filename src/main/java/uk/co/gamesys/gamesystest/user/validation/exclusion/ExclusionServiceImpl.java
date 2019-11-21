package uk.co.gamesys.gamesystest.user.validation.exclusion;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uk.co.gamesys.gamesystest.domain.User;

import java.util.Set;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static uk.co.gamesys.gamesystest.config.DataProviderConfiguration.USERS_EXCLUDED_COLLECTION;

@Service
@AllArgsConstructor
public class ExclusionServiceImpl implements ExclusionService {
    @Qualifier(USERS_EXCLUDED_COLLECTION)
    private final Set<User> usersExcludedCollection;

    @Override
    public boolean validate(@NonNull String dateOfBirth,
                            @NonNull String ssn) {

        final User user = User.builder()
                .dateOfBirth(parse(dateOfBirth, ISO_LOCAL_DATE))
                .ssn(ssn)
                .build();

        return !usersExcludedCollection.contains(user);
    }
}
