package com.dannyweston.mdp_cw3.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Run {
    public Run(String name){
        this.name = name;
    }

    @PrimaryKey(autoGenerate = true)
    int uid;

    String name;

    public int getId(){
        return uid;
    }

    @Override
    public String toString() {
        return "Run " + uid + " : " + name;
    }
}
