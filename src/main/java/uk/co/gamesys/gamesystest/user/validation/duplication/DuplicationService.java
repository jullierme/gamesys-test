package uk.co.gamesys.gamesystest.user.validation.duplication;

import java.time.LocalDate;

public interface DuplicationService {
    /**
     * Validates an user against a provider list using their date of
     * birth and social security number as identifier.
     *
     * @param dateOfBirth the user's date of birth
     * @param ssn         the user's social security number (United States)
     * @return true if the user is not duplicated
     */
    boolean validate(LocalDate dateOfBirth, String ssn);
}
