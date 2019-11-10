package uk.co.gamesys.gamesystest.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode(of = {"dateOfBirth", "ssn"})
public class User {
    @NonNull
    private String username;
    @NonNull
    private char[] password;
    @NonNull
    private LocalDate dateOfBirth;
    @NonNull
    private String ssn;

    @Builder
    public User(String username,
                char[] password,
                LocalDate dateOfBirth,
                String ssn) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.ssn = ssn;
    }
}
