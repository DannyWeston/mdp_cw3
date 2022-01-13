package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RunningActivityViewModel extends SignallingViewModel {
    public RunningActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void stopRun(){
        setFinished(true);

        setEventInvoked(true, 0);
    }

    // Elapsed time
    private final MutableLiveData<Long> elapsed = new MutableLiveData<>(0L);
    public LiveData<Long> getElapsed(){ return elapsed; }
    public void setElapsed(long elapsed){ this.elapsed.postValue(elapsed); }

    // Is the run finished or not?
    private final MutableLiveData<Boolean> finished = new MutableLiveData<>(false);
    public LiveData<Boolean> getFinished(){ return finished; }
    public void setFinished(boolean finished){ this.finished.setValue(finished); }

    // Accumulated distance
    private final MutableLiveData<Double> distance = new MutableLiveData<>(0D);
    public LiveData<Double> getDistance(){ return distance; }
    public void setDistance(double distance){ this.distance.setValue(distance); }
}
