package uk.co.gamesys.gamesystest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import uk.co.gamesys.gamesystest.domain.User;

import java.util.HashSet;
import java.util.Set;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.Collections.synchronizedSet;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Configuration
public class DataProviderConfiguration {
    public static final String USERS_EXCLUDED_COLLECTION = "usersExcludedCollection";
    public static final String USERS_COLLECTION = "usersCollection";

    @Bean(USERS_EXCLUDED_COLLECTION)
    @Scope(value = SCOPE_SINGLETON)
    Set<User> usersExcludedCollection() {
        Set<User> list = new HashSet<>();

        initExcludedList(list);

        return synchronizedSet(list);
    }

    @Bean(USERS_COLLECTION)
    @Scope(value = SCOPE_SINGLETON)
    Set<User> usersCollection() {
        return synchronizedSet(new HashSet<>());
    }

    private void initExcludedList(Set<User> usersExcluded) {
        User adaLovelace = User.builder()
                .username("adaLovelace")
                .password("Analytical3ngineRulz".toCharArray())
                .dateOfBirth(parse("1815-12-10", ISO_LOCAL_DATE))
                .ssn("85385075")
                .build();

        User alanTuring = User.builder()
                .username("alanTuring")
                .password("eniGmA123".toCharArray())
                .dateOfBirth(parse("1912-06-23", ISO_LOCAL_DATE))
                .ssn("123456789")
                .build();

        User konradZuse = User.builder()
                .username("konradZuse")
                .password("zeD1".toCharArray())
                .dateOfBirth(parse("1910-06-22", ISO_LOCAL_DATE))
                .ssn("987654321")
                .build();

        usersExcluded.add(adaLovelace);
        usersExcluded.add(alanTuring);
        usersExcluded.add(konradZuse);
    }
}
