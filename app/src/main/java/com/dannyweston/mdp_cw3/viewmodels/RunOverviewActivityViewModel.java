package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dannyweston.mdp_cw3.dao.Run;
import com.dannyweston.mdp_cw3.dao.repositories.RunRepository;

public class RunOverviewActivityViewModel extends SignallingViewModel {

    private final RunRepository _runRepo;
    private final long _runId;

    public RunOverviewActivityViewModel(@NonNull Application application, long runId) {
        super(application);

        _runId = runId;

        _runRepo = new RunRepository(application);

        _runRepo.getRun(_runId).observeForever(run::setValue);
    }

    public void deleteRun(){
        _runRepo.deleteRun(_runId);

        setEventInvoked(true, 0);
    }

    // Run object
    private final MutableLiveData<Run> run = new MutableLiveData<>();
    public LiveData<Run> getRun(){ return run; }
    public void setRun(Run run){
        this.run.setValue(run);
    }
}
