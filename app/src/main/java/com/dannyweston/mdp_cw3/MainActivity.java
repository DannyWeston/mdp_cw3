package com.dannyweston.mdp_cw3;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.dannyweston.mdp_cw3.services.RunnerService;
import com.dannyweston.mdp_cw3.viewmodels.MainActivityViewModel;

import androidx.appcompat.app.AppCompatActivity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dannyweston.mdp_cw3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private MainActivityViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Setup viewmodel */
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewmodel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewmodel);

        startService(new Intent(this, RunnerService.class));
        bindService(new Intent(this, RunnerService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }
}