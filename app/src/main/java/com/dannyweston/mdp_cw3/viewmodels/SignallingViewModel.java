package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public abstract class SignallingViewModel extends AndroidViewModel {
    public SignallingViewModel(@NonNull Application application) {
        super(application);
    }

    // For signalling to close the activity
    private final MutableLiveData<Pair<Boolean, Long>> eventInvoked = new MutableLiveData<>();
    public LiveData<Pair<Boolean, Long>> getEventInvoked(){ return eventInvoked; }
    public void setEventInvoked(boolean change, long value){ eventInvoked.setValue(new Pair<>(change, value)); }
}
