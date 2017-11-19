package calendarapp.truongnh.com.calendarapp;

/**
 * Created by 217REC10 on 10/27/17.
 */

public class TNHDate {

    private final int day;
    private final int month;
    private final int year;

    public TNHDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
