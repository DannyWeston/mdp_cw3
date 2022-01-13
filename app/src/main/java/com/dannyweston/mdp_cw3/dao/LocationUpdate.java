package com.dannyweston.mdp_cw3.dao;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.dannyweston.mdp_cw3.util.DistanceTimeUtils;

import java.util.Locale;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Run.class,
                parentColumns = "uid",
                childColumns = "runId"
        )
)
public class LocationUpdate {
    public LocationUpdate(long runId, double distance){
        this.runId = runId;

        this.timestamp = System.currentTimeMillis();

        this.distance = distance;
    }

    @PrimaryKey(autoGenerate = true)
    long uid;

    long runId, timestamp;

    double distance;

    public long getTimestamp(){
        return timestamp;
    }

    public long getId(){
        return uid;
    }

    public double getDistance() {
        return distance;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" +
        DistanceTimeUtils.asShortDateFormat(getTimestamp()) +
        " : " +
        String.format(Locale.ENGLISH, "%.2f]", getDistance());
    }

}


