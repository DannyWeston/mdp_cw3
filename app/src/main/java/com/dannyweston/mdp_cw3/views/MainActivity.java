package com.dannyweston.mdp_cw3.views;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.services.location.RunnerService;
import com.dannyweston.mdp_cw3.viewmodels.MainActivityViewModel;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION }, 0);

        /* Setup viewmodel */
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityViewModel viewmodel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewmodel);

        startService(new Intent(this, RunnerService.class));
        bindService(new Intent(this, RunnerService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }
}