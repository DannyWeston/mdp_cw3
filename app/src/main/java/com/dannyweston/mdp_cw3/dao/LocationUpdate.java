package com.dannyweston.mdp_cw3.dao;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.dannyweston.mdp_cw3.util.DistanceUtils;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Run.class,
                parentColumns = "uid",
                childColumns = "runId"
        )
)
public class LocationUpdate {
    public LocationUpdate(long runId, double latitude, double longitude, double travelled){
        this.latitude = latitude;
        this.longitude = longitude;
        this.travelled = travelled;

        this.runId = runId;

        this.timestamp = System.currentTimeMillis();
    }

    @PrimaryKey(autoGenerate = true)
    int uid;

    double latitude, longitude, travelled;

    long timestamp;

    long runId;

    public long getTimestamp(){
        return timestamp;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getTravelled(){
        return travelled;
    }

    public int getId(){
        return uid;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" +
        DistanceUtils.milliToSeconds(timestamp) +
        " : " +
        String.format("%.2f]", travelled);
    }
}


