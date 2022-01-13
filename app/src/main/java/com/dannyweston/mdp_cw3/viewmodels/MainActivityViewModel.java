package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;

public class MainActivityViewModel extends SignallingViewModel {
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void runActivity(){
        setEventInvoked(true, 0);
    }

    public void historyActivity(){
        setEventInvoked(true, 1);
    }

    public void settingsActivity(){
        setEventInvoked(true, 2);
    }

    public void aboutActivity() {
        setEventInvoked(true, 3);
    }
}
