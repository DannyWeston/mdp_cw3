package com.dannyweston.mdp_cw3.repositories;

import android.app.Application;

import androidx.annotation.NonNull;

import com.dannyweston.mdp_cw3.dao.LocationUpdate;

public class LocUpdateRepository extends BaseRepository {

    public LocUpdateRepository(Application app) {
        super(app);
    }

    public void addPos(@NonNull LocationUpdate pos) {
        getDb().getExecutors().execute(() -> getDb().locationDao().insertAll(pos));
    }
}
