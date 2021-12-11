package bank_management.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    private DateUtils() {}

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month + 1;
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year + 1900;
    }

    public static boolean isGreaterThanCurrentDate(Date date) {
        return date.after(new Date());
    }

    public static boolean isLessThanCurrentDate(Date date) {
        return date.before(new Date());
    }
}
