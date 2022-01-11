package com.dannyweston.mdp_cw3.util;

public class DistanceUtils {
    public static String milliToSeconds(long millis){
        return String.format("%.2fs", (double)millis / 1000);
    }
}
