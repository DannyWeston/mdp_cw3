package com.dannyweston.mdp_cw3.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PositionDao {
    @Query("SELECT * FROM tb_positions")
    LiveData<List<Position>> getAll();

    @Query("SELECT * FROM tb_positions WHERE uid IN (:posIds)")
    LiveData<List<Position>> getByIds(int[] posIds);

    @Query("SELECT * FROM tb_positions WHERE uid = (:id)")
    LiveData<Position> getById(int id);

    @Query("SELECT * FROM tb_positions WHERE created_at = (SELECT MAX(created_at) FROM tb_positions)")
    LiveData<Position> getRecent();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertAll(Position... positions);

    @Delete
    void delete(Position position);
}
