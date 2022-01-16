package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.dao.Run;
import com.dannyweston.mdp_cw3.repositories.RunRepository;
import com.dannyweston.mdp_cw3.util.DistanceTimeUtils;

import java.util.Locale;

public class RunOverviewActivityViewModel extends SignallingViewModel {

    private final RunRepository _runRepo;
    private final long _runId;
    private final SharedPreferences _pref;

    private final int _units;

    private final Application _app;

    public RunOverviewActivityViewModel(@NonNull Application application, long runId) {
        super(application);

        _app = application;

        _runId = runId;

        _runRepo = new RunRepository(application);

        _runRepo.getRun(_runId).observeForever(run::setValue);

        // Fetch preferences manager
        _pref = application.getSharedPreferences(application.getString(R.string.app_name), Context.MODE_PRIVATE);

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

    public String averageSpeed(Run r){
        // Check for null run
        if (r == null) return _app.getString(R.string.extraNullSpeed);

        // Get average speed units
        double distance;
        double time;
        String unitString;
        if (_units == _app.getResources().getInteger(R.integer.Kilometres)) {
            unitString = _app.getString(R.string.txtKilometresSpeedUnits);
            distance = DistanceTimeUtils.MetresToKM(r.getDistance());
            time = DistanceTimeUtils.MillisToHours(r.getDuration());
        }
        else if (_units == _app.getResources().getInteger(R.integer.Miles)) {
            unitString = _app.getString(R.string.txtMilesSpeedUnits);
            distance = DistanceTimeUtils.MetresToMiles(r.getDistance());
            time = DistanceTimeUtils.MillisToHours(r.getDuration());
        }
        else {
            unitString = _app.getString(R.string.txtMetresSpeedUnits);
            distance = r.getDistance();
            time = DistanceTimeUtils.MillisToSeconds(r.getDuration());
        }

        return String.format(Locale.ENGLISH, "%.2f %s", DistanceTimeUtils.GetSpeed(distance, time), unitString);
    }

    public void btnDeleteRunClick(){
        setAction(new Action(R.integer.actionDeleteRun));
    }

    public void deleteRun(){
        _runRepo.deleteRun(_runId);
    }

    // Run object
    private final MutableLiveData<Run> run = new MutableLiveData<>();
    public LiveData<Run> getRun(){ return run; }
    public void setRun(Run run){
        this.run.setValue(run);
    }
}