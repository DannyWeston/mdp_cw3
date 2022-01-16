package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.repositories.RunRepository;
import com.dannyweston.mdp_cw3.util.DistanceTimeUtils;

import java.util.Locale;

public class RunningActivityViewModel extends SignallingViewModel {
    private final RunRepository _runRepo;
    private final Application _app;

    private final int _units;

    public RunningActivityViewModel(@NonNull Application application) {
        super(application);

        _runRepo = new RunRepository(application);

        // Load things in from preferences manager
        SharedPreferences _pref = application.getSharedPreferences(application.getString(R.string.app_name), Context.MODE_PRIVATE);

        // Cache application context for now
        _app = application;

        // Fetch units from preferences
        _units = _pref.getInt(application.getString(R.string.prefUnits), R.integer.Metres);
    }

    public String getTravelled(double distance){
        double dist;
        String units;
        if (_units == _app.getResources().getInteger(R.integer.Kilometres)) {
            dist = DistanceTimeUtils.MetresToKM(distance);
            units = _app.getString(R.string.txtKilometreUnits);
        }
        else if (_units == _app.getResources().getInteger(R.integer.Miles)) {
            dist = DistanceTimeUtils.MetresToMiles(distance);
            units = _app.getString(R.string.txtMileUnits);
        }
        else {
            dist = distance;
            units = _app.getString(R.string.txtMetreUnits);
        }

        return String.format(Locale.ENGLISH, "%.2f %s", dist, units);
    }

    public void startListening(long runId, LiveData<Boolean> serviceFinished){
        setStarted(true);

        _runRepo.getRunDuration(runId).observeForever((val) -> {
            if (val != null) setElapsed(val);
        });

        _runRepo.getRunDistance(runId).observeForever((val) -> {
            if (val != null) setDistance(val);
        });

        serviceFinished.observeForever((val) -> {
            if (val != null) setFinished(val);
        });
    }

    public void stopRun(){
        setAction(new Action(R.integer.actionEndRun));
    }

    // Elapsed time
    private final MutableLiveData<Boolean> started = new MutableLiveData<>(false);
    public LiveData<Boolean> getStarted(){ return started; }
    public void setStarted(boolean started){ this.started.postValue(started); }

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
