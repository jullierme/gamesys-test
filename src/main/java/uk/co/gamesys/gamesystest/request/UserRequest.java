package uk.co.gamesys.gamesystest.request;

import lombok.Builder;
import lombok.Value;
import uk.co.gamesys.gamesystest.validator.DateISO8601;
import uk.co.gamesys.gamesystest.validator.Password;
import uk.co.gamesys.gamesystest.validator.Username;

import javax.validation.constraints.NotNull;

@Value
public class UserRequest {
    @Username
    @NotNull
    private String username;

    @Password
    @NotNull
    private char[] password;

    @DateISO8601
    @NotNull
    private String dateOfBirth;

    @NotNull
    private String ssn;

    @Builder
    public UserRequest(String username,
                       char[] password,
                       String dateOfBirth,
                       String ssn) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.ssn = ssn;
    }
}
