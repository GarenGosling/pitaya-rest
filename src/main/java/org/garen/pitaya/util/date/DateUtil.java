package org.garen.pitaya.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A tool class for date and time
 *
 * @author Garen Gosling
 * @create 2017-09-07 00:00
 * @since v1.0
 */
public class DateUtil {

    /**
     * arg to date
     *
     * @param arg
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String arg) throws ParseException {
        String dateString = Format.formatDate(arg);
        if(dateString == null || "".equals(dateString)){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(Pattern.DATE.value());
        return sdf.parse(dateString);
    }

    /**
     * arg to time
     *
     * @param arg
     * @return
     * @throws ParseException
     */
    public static Date parseTime(String arg) throws ParseException {
        String timeString = Format.formatTime(arg);
        if(timeString == null || "".equals(timeString)){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(Pattern.TIME.value());
        return sdf.parse(timeString);
    }

    /**
     * arg to date
     *
     * @param arg
     * @return
     * @throws ParseException
     */
    public static String parseDateString(Date arg) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Pattern.DATE.value());
        return Format.formatDate(sdf.format(arg));
    }

    /**
     *  arg to time
     *
     * @param arg
     * @return
     * @throws ParseException
     */
    public static String parseTimeString(Date arg) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Pattern.TIME.value());
        return Format.formatTime(sdf.format(arg));
    }

    /**
     * arg to date
     *
     * @param arg
     * @return
     * @throws ParseException
     */
    public static String parseDateString(String arg) throws ParseException {
        return Format.formatDate(arg);
    }

    /**
     * arg to time
     *
     * @param arg
     * @return
     * @throws ParseException
     */
    public static String parseTimeString(String arg) throws ParseException {
        return Format.formatTime(arg);
    }

    /**
     * arg to date
     *
     * @param arg
     * @return
     * @throws ParseException
     */
    public static Date parseDate(Date arg) throws  ParseException {
        String dateString = parseDateString(arg);
        return parseDate(dateString);
    }

    /**
     * arg to long
     *
     * @param arg
     * @return
     * @throws ParseException
     */
    public static Long getTime(String arg) throws ParseException {
        Date date = parseTime(arg);
        if(date == null){
            return null;
        }
        return date.getTime();
    }

    /**
     * arg to time
     *
     * @param arg
     * @return
     */
    public static Date parseTime(long arg) {
        return new Date(arg);
    }

    /**
     * time - subtractedTime = days
     *
     * @param time
     * @param subtractedTime
     * @return
     */
    public static Long minusTime(Date time, Date subtractedTime) {
        if(time == null || subtractedTime == null){
            return null;
        }
        return (time.getTime() - subtractedTime.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * date - subtractedDate = days
     *
     * @param date
     * @param subtractedDate
     * @return
     */
    public static Long minusDate(Date date, Date subtractedDate){
        if(date == null || subtractedDate == null){
            return null;
        }
        return (date.getTime() - subtractedDate.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * date + days
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days){
        if(date == null){
            return null;
        }
        long l = date.getTime() + days * 24 * 60 * 60 * 1000;
        return parseTime(l);
    }

    /**
     * date - days
     *
     * @param date
     * @param days
     * @return
     */
    public static Date minusDays(Date date, int days){
        if(date == null){
            return null;
        }
        long l = date.getTime() - days * 24 * 60 * 60 * 1000;
        return parseTime(l);
    }

}
