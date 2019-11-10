package uk.co.gamesys.gamesystest.config;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import uk.co.gamesys.gamesystest.domain.User;

import java.util.Set;

import static java.time.LocalDate.parse;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.co.gamesys.gamesystest.config.DataProviderConfiguration.USERS_COLLECTION;
import static uk.co.gamesys.gamesystest.config.DataProviderConfiguration.USERS_EXCLUDED_COLLECTION;

@SpringBootTest
@DisplayName("DataProviderConfiguration class test suite")
class DataProviderConfigurationTest {
    @Autowired
    @Qualifier(USERS_EXCLUDED_COLLECTION)
    Set<User> usersExcludedCollection;

    @Autowired
    @Qualifier(USERS_COLLECTION)
    Set<User> usersCollection;

    @Test
    @DisplayName("Should inject users collection")
    void givenUsersCollection_whenInjected_thenShouldNotBeNull() {
        //given userCollection
        //when asserting
        //then
        assertNotNull(usersCollection);
    }

    @Test
    @DisplayName("Should inject users excluded collection")
    void givenUsersExcludedCollection_whenInjected_thenShouldNotBeNull() {
        //given userCollection
        //when asserting
        //then
        assertNotNull(usersExcludedCollection);
    }

    @Test
    @DisplayName("Should inject excluded list with excluded Users")
    void givenUserExcludedCollection_whenInjected_thenShouldHaveExcludedUsers() {
        //given excluded Users
        User adaLovelace = User.builder()
                .password("Analytical3ngineRulz".toCharArray())
                .username("adaLovelace")
                .dateOfBirth(parse("1815-12-10", ISO_LOCAL_DATE))
                .ssn("85385075")
                .build();

        User alanTuring = User.builder()
                .password("eniGmA123".toCharArray())
                .username("alanTuring")
                .dateOfBirth(parse("1912-06-23", ISO_LOCAL_DATE))
                .ssn("123456789")
                .build();

        User konradZuse = User.builder()
                .password("zeD1".toCharArray())
                .username("konradZuse")
                .dateOfBirth(parse("1910-06-22", ISO_LOCAL_DATE))
                .ssn("987654321")
                .build();

        //when
        boolean adaExcluded = usersExcludedCollection.contains(adaLovelace);
        boolean alanExcluded = usersExcludedCollection.contains(alanTuring);
        boolean konradExcluded = usersExcludedCollection.contains(konradZuse);

        //then
        assertTrue(adaExcluded);
        assertTrue(alanExcluded);
        assertTrue(konradExcluded);
    }
}
