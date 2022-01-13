package com.dannyweston.mdp_cw3.dao.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.dannyweston.mdp_cw3.dao.LocationUpdate;
import com.dannyweston.mdp_cw3.dao.Run;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RunRepository extends BaseRepository {
    public RunRepository(Application app) {
        super(app);
    }

    public LiveData<Run> getLastRun() {
        return getDb().runDao().getLastRun();
    }

    public long addNewRun(Run run){
        try {
            return getDb().getExecutors().submit(
                    () -> getDb().runDao().insert(run)
            ).get();
        }
        catch (ExecutionException | InterruptedException e) {
            Log.e("mdpcw3", "Couldn't insert a new run into the database");
            return -1;
        }
    }

    public LiveData<Double> getRunDistance(long runId){
        return getDb().runDao().getRunDistanceById(runId);
    }

    public void setRunDistance(long runId, double distance){
        getDb().getExecutors().execute(() -> getDb().runDao().setRunDistanceById(runId, distance));
    }

    public LiveData<Long> getRunDuration(long runId){
        return getDb().runDao().getRunDurationById(runId);
    }

    public void setRunDuration(long runId, long duration){
        getDb().getExecutors().execute(() -> getDb().runDao().setRunDurationById(runId, duration));
    }

    public void deleteRun(long runId){
        getDb().getExecutors().execute(() -> getDb().runDao().deleteRunById(runId));
    }

    public LiveData<Run> getRun(long runId){
        return getDb().runDao().getRun(runId);
    }
}
