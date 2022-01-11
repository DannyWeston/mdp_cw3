package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.dannyweston.mdp_cw3.dao.LocationUpdate;
import com.dannyweston.mdp_cw3.dao.repositories.LocUpdateRepository;

public class RunningActivityViewModel extends AndroidViewModel {


    public RunningActivityViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    // Elapsed time
    private final MutableLiveData<Long> elapsed = new MutableLiveData<>();
    public LiveData<Long> getElapsed(){ return elapsed; }
    public void setElapsed(long elapsed){
        this.elapsed.postValue(elapsed);
    }
}
