package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.dannyweston.mdp_cw3.dao.Position;
import com.dannyweston.mdp_cw3.dao.repositories.PositionRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private final PositionRepository _posRepo;

    private final MutableLiveData<Position> recentPos = new MutableLiveData<>();

    public LiveData<Position> getRecentPos(){
        return recentPos;
    }

    private final Observer<Position> recentObserver = recentPos::setValue;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        _posRepo = new PositionRepository(application);

        _posRepo.getRecentPos().observeForever(recentObserver);
    }


    public void addPosition(){
        _posRepo.addPos(new Position(10, 10));
    }

    @Override
    protected void onCleared() {
        // Remove observers
        _posRepo.getRecentPos().removeObserver(recentObserver);

        super.onCleared();
    }
}
