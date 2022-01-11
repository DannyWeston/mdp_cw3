package com.dannyweston.mdp_cw3.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocUpdateDao {
    @Query("SELECT * FROM LocationUpdate")
    LiveData<List<LocationUpdate>> getAll();

    @Query("SELECT * FROM LocationUpdate WHERE timestamp = (SELECT MAX(timestamp) FROM LocationUpdate)")
    LiveData<LocationUpdate> getMostRecent();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertAll(LocationUpdate... positions);
}
