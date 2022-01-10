package com.dannyweston.mdp_cw3.services.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

public class RunnerServiceBinder extends Binder implements IInterface {
    @Override
    public IBinder asBinder() {
        return this;
    }
}
