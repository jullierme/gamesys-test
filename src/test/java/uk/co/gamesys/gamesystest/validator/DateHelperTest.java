package uk.co.gamesys.gamesystest.validator;

class DateHelperTest {
    @DateISO8601
    String date;

    public DateHelperTest(String date) {
        this.date = date;
    }
}
