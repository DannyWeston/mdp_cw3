package com.dannyweston.mdp_cw3.util;

public class TimeUtils {
    public static String MillisToClockFormat(long millis){
        int seconds = (int)millis / 1000;
        int minutes = (seconds / 60) % 60;
        int hours = seconds / 3600;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds % 60);
    }
}
