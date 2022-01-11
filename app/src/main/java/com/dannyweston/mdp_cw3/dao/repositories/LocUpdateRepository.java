package com.dannyweston.mdp_cw3.dao.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.dannyweston.mdp_cw3.dao.LocationUpdate;

import java.util.List;

public class LocUpdateRepository extends BaseRepository {

    public LocUpdateRepository(Application app) {
        super(app);
    }

    public LiveData<List<LocationUpdate>> getAll() {
        return getDb().locationDao().getAll();
    }

    public void addPos(@NonNull LocationUpdate pos) {
        getDb().getExecutors().execute(() -> getDb().locationDao().insertAll(pos));
    }

    public LiveData<LocationUpdate> getRecentPos() {
        return getDb().locationDao().getMostRecent();
    }
}
