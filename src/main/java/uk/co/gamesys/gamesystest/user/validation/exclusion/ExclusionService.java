package uk.co.gamesys.gamesystest.user.validation.exclusion;

/**
 * Service to offer validation of an user against an exclusion list.
 *
 * @author gamesys
 */
public interface ExclusionService {

    /**
     * Validates an user against an exclusion list using their date of
     * birth and social security number as identifier.
     *
     * @param dateOfBirth the user's date of birth in ISO 8601 format
     * @param ssn         the user's social security number (United States)
     * @return true if the user is not excluded
     */
    boolean validate(String dateOfBirth, String ssn);
}
