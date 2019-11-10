package uk.co.gamesys.gamesystest.validator;

class UsernameHelperTest {
    @Username
    String username;

    public UsernameHelperTest(String username) {
        this.username = username;
    }
}
