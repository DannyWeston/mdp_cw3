package com.dannyweston.mdp_cw3.dao;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.dannyweston.mdp_cw3.util.DistanceTimeUtils;

import java.util.Date;

@Entity(indices = {@Index("uid")})
public class Run {
    public Run(String name){
        this.name = name;

        this.startTime = System.currentTimeMillis();

        distance = 0;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    long uid;

    String name;

    double distance;

    long startTime, duration;

    public long getId(){
        return uid;
    }

    public long getStartTime(){
        return startTime;
    }

    public String getName(){
        return name;
    }

    public double getDistance(){ return distance; }

    public long getDuration(){ return duration; }

    @Override
    public String toString() {
        return DistanceTimeUtils.DateTimeFormat(getStartTime());
    }
}
