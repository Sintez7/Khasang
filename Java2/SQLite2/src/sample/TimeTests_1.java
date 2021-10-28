package sample;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeTests_1 {
    public static void main(String[] args) {

        Calendar calendar = new GregorianCalendar();
        Calendar calendar2 = Calendar.getInstance();

//        Date time1 = calendar.getTime();
//
//        var lt = LocalDateTime.now();
//        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyMMdd");
//        int a2 = Integer.parseInt(f.format(lt));
//
//        long time = System.currentTimeMillis();
//        System.err.println(a2);

        calendar.set(2020, 6, 5);
//        System.err.println(System.currentTimeMillis() + " curtimemillis");
        System.err.println(calendar.getTimeInMillis());
        Duration dur = Duration.of(500, ChronoUnit.DAYS);
        System.err.println(dur.toMillis());
    }
}
