package com.dannyweston.mdp_cw3.dao;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_positions")
public class Position {
    public Position(double x, double y){
        this.x = x;
        this.y = y;

        this.created_at = System.currentTimeMillis();
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    public double x;

    public double y;

    public long created_at;

    @NonNull
    @Override
    public String toString() {
        return "(" + x + " " + y + ")";
    }
}


