package com.dannyweston.mdp_cw3.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.databinding.ActivityMainBinding;
import com.dannyweston.mdp_cw3.databinding.ActivityRunningBinding;
import com.dannyweston.mdp_cw3.services.location.RunnerService;
import com.dannyweston.mdp_cw3.services.location.RunnerServiceBinder;
import com.dannyweston.mdp_cw3.viewmodels.MainActivityViewModel;
import com.dannyweston.mdp_cw3.viewmodels.RunningActivityViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class RunningActivity extends AppCompatActivity {
    // Service connection for managing runer location updates
    // Runs in foreground so as to allow user to change to another app
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            RunnerServiceBinder binder = (RunnerServiceBinder)service;
            RunnerService _service = binder.getService();
            if (!_service.alreadyRunning()) {
                _service.startNewRun();
            }

            // Observe timer
            _service.getTimeElapsed().observe(RunningActivity.this, _vm::setElapsed);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // Some problem happened, cancel the timer for now
        }
    };

    private RunningActivityViewModel _vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Setup viewmodel */
        ActivityRunningBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_running);
        _vm = new ViewModelProvider(this).get(RunningActivityViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(_vm);

        // Setup service to store location

        startService(new Intent(this, RunnerService.class));
        bindService(new Intent(this, RunnerService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }
}