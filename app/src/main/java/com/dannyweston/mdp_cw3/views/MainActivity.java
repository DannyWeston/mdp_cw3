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
    }

    // Open other activities
    public void openRunningActivity(View v){
        // Start the running activity

        Intent intent = new Intent(this, RunningActivity.class);
        startActivity(new Intent(intent));
    }
    public void openHistoryActivity(View v){
        // Start the running activity

        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(new Intent(intent));
    }
    public void openSettingsActivity(View v){
        // Start the running activity

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(new Intent(intent));
    }
}