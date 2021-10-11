package sample;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeTests_1 {
    public static void main(String[] args) {

        Calendar calendar = new GregorianCalendar();
        Calendar calendar2 = Calendar.getInstance();

        Date time1 = calendar.getTime();

        var lt = LocalDateTime.now();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyMMdd");
        int a2 = Integer.parseInt(f.format(lt));

        long time = System.currentTimeMillis();
        System.err.println(a2);
    }
}
