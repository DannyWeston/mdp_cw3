package com.dannyweston.mdp_cw3.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.dao.repositories.RunRepository;
import com.dannyweston.mdp_cw3.databinding.ActivityRunningBinding;
import com.dannyweston.mdp_cw3.services.RunnerService;
import com.dannyweston.mdp_cw3.services.RunnerServiceBinder;
import com.dannyweston.mdp_cw3.viewmodels.RunningActivityViewModel;
import com.dannyweston.mdp_cw3.viewmodels.ViewModelFactory;

public class RunningActivity extends AppCompatActivity {
    // Service connection for managing runner location updates
    // Runs in foreground so as to allow user to change to another app

    private RunnerService _runnerService;

    // For handling the connection with the location service
    private final ServiceConnection _serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            RunnerServiceBinder binder = (RunnerServiceBinder)service;
            _runnerService = binder.getService();

            long activeId = _runnerService.getActiveRunId();

            if (activeId < 0) activeId = _runnerService.startNewRun();

            // Observe things
            _runRepo.getRunDuration(activeId).observeForever((val) -> {
                if (val != null) _vm.setElapsed(val);
            });

            _runRepo.getRunDistance(activeId).observeForever((val) -> {
                if (val != null) _vm.setDistance(val);
            });
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private RunningActivityViewModel _vm;

    private RunRepository _runRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _runRepo = new RunRepository(getApplication());

        /* Setup viewmodel */
        ActivityRunningBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_running);

        _vm = new ViewModelProvider(this, new ViewModelFactory(getApplication()))
                .get(RunningActivityViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(_vm);

        // Listen for close activity event
        _vm.getEventInvoked().observe(this, (obs) -> {
            if (obs.first){
                _runnerService.endRun();

                _runnerService.stopService(new Intent(RunningActivity.this, RunnerService.class));
            }
        });

        // Setup service to handle location gathering
        this.startService(new Intent(RunningActivity.this, RunnerService.class));
        this.bindService(new Intent(RunningActivity.this, RunnerService.class), _serviceConnection, Context.BIND_AUTO_CREATE);
    }
}