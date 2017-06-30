package com.example.skajkut.chatapp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Stefan Kajkut on 6/30/17.
 * For more information contact me on stefan.kajkutsf@gmail.com .
 */

public class DateTimeFormatter {

    public static String dateTimeFormatter(Date date) {
        Date currentDate = new Date();
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(currentDate);
        int currentHour = currentCalendar.get(Calendar.HOUR);
        int currentMinute = currentCalendar.get(Calendar.MINUTE);


        Calendar messageCalendar = Calendar.getInstance();
        messageCalendar.setTime(date);
        int hour = currentCalendar.get(Calendar.HOUR);
        int minute = currentCalendar.get(Calendar.MINUTE);

        String messageTime = null;

        if(currentDate.equals(date)) {
            if(currentHour == hour && currentMinute == minute) {
                messageTime = "A moment ago";
            } else if (currentHour == hour) {
                messageTime = "Few minutes ago";
            } else {
                messageTime = "Couple hours ago";
            }
        } else {
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
            messageTime = simpleDateFormat.format(date);
        }

        return messageTime;
    }

}
