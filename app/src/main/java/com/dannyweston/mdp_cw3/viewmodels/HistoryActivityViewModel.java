package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.dannyweston.mdp_cw3.dao.Run;
import com.dannyweston.mdp_cw3.dao.repositories.RunRepository;

public class HistoryActivityViewModel extends SignallingViewModel {
    private final RunRepository _runRepo;

    public HistoryActivityViewModel(@NonNull Application application) {
        super(application);

        _runRepo = new RunRepository(application);

        _runRepo.getLastRun().observeForever(recentObserver);
    }

    public void viewRun(View view) {
        long runId = (long)view.getTag();

        setEventInvoked(true, runId);
    }

    @Override
    protected void onCleared() {
        // Remove observers
        _runRepo.getLastRun().removeObserver(recentObserver);

        super.onCleared();
    }

    // Recent Position
    private final MutableLiveData<Run> lastRun = new MutableLiveData<>();
    public LiveData<Run> getLastRun(){
        return lastRun;
    }
    private final Observer<Run> recentObserver = lastRun::setValue;
}
