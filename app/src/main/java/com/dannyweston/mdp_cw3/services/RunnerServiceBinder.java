package com.dannyweston.mdp_cw3.services;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;

import com.dannyweston.mdp_cw3.services.RunnerService;

public class RunnerServiceBinder extends Binder implements IInterface {
    private final RunnerService _rs;

    public RunnerServiceBinder(RunnerService rs){
        this._rs = rs;
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    public RunnerService getService(){
        return this._rs;
    }
}
