package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dannyweston.mdp_cw3.R;


public class SettingsActivityViewModel extends SignallingViewModel {
    public SettingsActivityViewModel(@NonNull Application application) {
        super(application);
    }


    private final MutableLiveData<Boolean> _saved = new MutableLiveData<>();
    public LiveData<Boolean> getSaved(){ return _saved; }
    public void setSaved(boolean saved){ this._saved.setValue(saved); }

    private final MutableLiveData<Integer> _units = new MutableLiveData<>();
    public LiveData<Integer> getUnits(){ return _units; }
    public void setUnits(int units){ this._units.setValue(units); }

    public void btnSaveSettingsClick(){
        setSaved(true);

        setAction(new Action(R.integer.actionSaveSettings));
    }
}
