package com.dannyweston.mdp_cw3.dao.repositories;

import androidx.lifecycle.LiveData;

import com.dannyweston.mdp_cw3.dao.Position;

import java.util.List;

public interface IPositionRepository {
    LiveData<Position> getPosById(int id);

    LiveData<List<Position>> getAll();

    void addPos(Position pos);

    void removePos(Position p);

    LiveData<Position> getRecentPos();
}
