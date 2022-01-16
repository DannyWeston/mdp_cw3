package com.dannyweston.mdp_cw3.repositories;

import android.app.Application;

import com.dannyweston.mdp_cw3.dao.AppDatabase;

abstract class BaseRepository {
    private final AppDatabase _db;

    BaseRepository(Application app){
        this._db = AppDatabase.getInstance(app);
    }

    AppDatabase getDb(){
        return _db;
    }
}
