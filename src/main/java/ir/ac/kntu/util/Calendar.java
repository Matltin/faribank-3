package ir.ac.kntu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Calendar {
    public static Date getDate() {
        return new Date();
    }

    public static String getDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd -- HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
