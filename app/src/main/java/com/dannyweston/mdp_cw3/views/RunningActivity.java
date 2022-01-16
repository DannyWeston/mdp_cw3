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
import android.util.Log;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.databinding.ActivityRunningBinding;
import com.dannyweston.mdp_cw3.services.RunnerService;
import com.dannyweston.mdp_cw3.services.RunnerServiceBinder;
import com.dannyweston.mdp_cw3.viewmodels.Action;
import com.dannyweston.mdp_cw3.viewmodels.RunningActivityViewModel;
import com.dannyweston.mdp_cw3.viewmodels.ViewModelFactory;

public class RunningActivity extends AppCompatActivity implements ActionListener {
    // Can only be used after bind (service connection below)
    private RunnerService _runnerService;

    // For handling the connection with the location service
    private final ServiceConnection _serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            RunnerServiceBinder binder = (RunnerServiceBinder)service;
            _runnerService = binder.getService();

            Boolean started = _vm.getStarted().getValue();

            long activeId = _runnerService.getActiveRunId();

            if (activeId < 0 && started != null && !started) _vm.startListening(_runnerService.startNewRun(), _runnerService.getFinished());
            else _vm.startListening(activeId, _runnerService.getFinished());
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("mdpcw3", "Disconnected");
        }
    };

    private RunningActivityViewModel _vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Setup viewmodel */
        ActivityRunningBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_running);

        _vm = new ViewModelProvider(this, new ViewModelFactory(getApplication()))
                .get(RunningActivityViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(_vm);

        // Listen for actions
        _vm.getAction().observe(this, this::onAction);

        // Only setup service to handle location gathering on fresh load
        startService(new Intent(RunningActivity.this, RunnerService.class));
        bindService(new Intent(RunningActivity.this, RunnerService.class), _serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onAction(Action action) {
        if (action.getValue() == R.integer.actionEndRun){
            _runnerService.endRun();

            _vm.setAction(Action.RESET_ACTION);
        }
    }
}