package com.dannyweston.mdp_cw3.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RunDao {
    @Query("SELECT * FROM Run WHERE uid = (SELECT MAX(uid) FROM Run)")
    LiveData<Run> getLastRun();

    @Query("SELECT * FROM LocationUpdate WHERE runId = (:runId)")
    LiveData<LocationUpdate> getLocations(long runId);

    @Query("SELECT SUM(travelled) FROM LocationUpdate WHERE runId = (:runId)")
    LiveData<Long> getRunDistanceById(long runId);

    @Insert
    long insert(Run run);
}
