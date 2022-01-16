package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public abstract class SignallingViewModel extends AndroidViewModel {
    public SignallingViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    // For signalling to close the activity
    private final MutableLiveData<Action> _action = new MutableLiveData<>();
    public LiveData<Action> getAction(){ return _action; }
    public void setAction(Action action){
        _action.setValue(action);
    }
}
