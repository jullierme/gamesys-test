package uk.co.gamesys.gamesystest.validator;

class PasswordHelperTest {
    @Password
    char[] password;

    public PasswordHelperTest(char[] password) {
        this.password = password;
    }
}
