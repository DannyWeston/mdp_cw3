package com.dannyweston.mdp_cw3.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DistanceTimeUtils {
    private static final double METRES_IN_MILE = 1609.34;
    private static final double METRES_IN_KILOMETRE = 1000;

    private static final long MILLIS_IN_HOUR = 3600000;
    private static final long MILLIS_IN_SECOND = 1000;
    private static final long MILLIS_IN_MINUTE = 60000;

    public static String ToMetresString(double metres){
        return String.format(Locale.ENGLISH, "%.0f m", metres);
    }

    public static double MetresToKM(double metres){
        return metres / METRES_IN_KILOMETRE;
    }

    public static double MetresToMiles(double metres){
        return metres / METRES_IN_MILE;
    }

    public static double GetSpeedMetresSecond(long millis, double distance){
        return distance / MillisToSeconds(millis);
    }

    public static double GetSpeedKilometresHour(long millis, double distance){
        return MetresToKM(distance) / MillisToHours(millis);
    }

    public static double GetSpeedMilesHour(long millis, double distance){
        return MetresToMiles(distance) / MillisToHours(millis);
    }

    public static double MillisToHours(long millis){
        return (double)millis / MILLIS_IN_HOUR;
    }

    public static double MillisToMinutes(long millis){
        return (double)millis /  MILLIS_IN_MINUTE;
    }

    public static double MillisToSeconds(long millis){
        return (double)millis /  MILLIS_IN_SECOND;
    }

    public static String MillisToClockFormat(long millis){
        int seconds = (int)(Math.floor(MillisToSeconds(millis)) % 60);
        int minutes = (int)(Math.floor(MillisToMinutes(millis)) % 60);
        int hours = (int)Math.floor(MillisToHours(millis));

        return String.format(Locale.ENGLISH, "%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String DateTimeFormat(long millis){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);

        return sdf.format(new Date(millis));
    }

    public static String asShortDateFormat(long millis){
        return new Date(millis).toString();
    }
}
