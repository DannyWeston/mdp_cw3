package com.dannyweston.mdp_cw3.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RunDao {
    @Query("SELECT * FROM Run WHERE uid = (:runId)")
    LiveData<Run> getRun(long runId);

    @Query("SELECT * FROM Run WHERE uid = (SELECT uid FROM Run WHERE startTime = (SELECT MAX(startTime) FROM Run))")
    LiveData<Run> getLastRun();

    @Query("SELECT * FROM Run WHERE uid = (SELECT uid FROM Run WHERE distance = (SELECT MAX(distance) FROM Run))")
    LiveData<Run> getLongestRun();

    @Query("SELECT * FROM LocationUpdate WHERE runId = (:runId)")
    LiveData<List<LocationUpdate>> getLocations(long runId);

    @Query("SELECT distance FROM Run WHERE uid = (:runId)")
    LiveData<Double> getRunDistanceById(long runId);

    @Query("UPDATE Run SET distance = :distance WHERE uid = :runId")
    void setRunDistanceById(long runId, double distance);

    @Query("SELECT duration FROM Run WHERE uid = (:runId)")
    LiveData<Long> getRunDurationById(long runId);

    @Query("UPDATE Run SET duration = :duration WHERE uid = :runId")
    void setRunDurationById(long runId, long duration);

    @Query("DELETE FROM Run WHERE uid = :runId")
    void deleteRunById(long runId);

    @Insert
    long insert(Run run);

    @Query("SELECT * FROM Run ORDER BY startTime DESC")
    LiveData<List<Run>> getAllRuns();

    @Query("SELECT COUNT(*) FROM Run")
    LiveData<Long> getRunCount();

    @Query("SELECT * FROM Run WHERE (startTime > :dateA AND startTime < :dateB)")
    LiveData<List<Run>> getFromDateRange(long dateA, long dateB);
}
