package com.dannyweston.mdp_cw3.dao.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.dannyweston.mdp_cw3.BuildConfig;
import com.dannyweston.mdp_cw3.dao.AppDatabase;
import com.dannyweston.mdp_cw3.dao.Position;

import java.util.List;
import java.util.Objects;

public class PositionRepository implements IPositionRepository {

    private final AppDatabase _db;

    public PositionRepository(Application app){
        _db = AppDatabase.getInstance(app);
    }

    @Override
    public LiveData<Position> getPosById(int id) {
        return _db.positionDao().getById(id);
    }

    @Override
    public LiveData<List<Position>> getAll() {
        return _db.positionDao().getAll();
    }

    @Override
    public void addPos(@NonNull Position pos) {
        _db.getExecutors().execute(() -> _db.positionDao().insertAll(pos));
    }

    @Override
    public void removePos(@NonNull Position pos) {
        _db.getExecutors().execute(() -> _db.positionDao().delete(pos));
    }

    @Override
    public LiveData<Position> getRecentPos() {
        return _db.positionDao().getRecent();
    }
}
