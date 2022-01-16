package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;

import com.dannyweston.mdp_cw3.R;

public class MainActivityViewModel extends SignallingViewModel {
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void btnOpenRunActivityClick(){
        setAction(new Action(R.integer.actionOpenRunActivity));
    }

    public void btnOpenHistoryActivityClick(){
        setAction(new Action(R.integer.actionOpenHistoryActivity));
    }

    public void btnOpenSettingsActivityClick(){
        setAction(new Action(R.integer.actionOpenSettingsActivity));
    }

    public void btnOpenAboutActivityClick() {
        setAction(new Action(R.integer.actionOpenAboutActivity));
    }
}
