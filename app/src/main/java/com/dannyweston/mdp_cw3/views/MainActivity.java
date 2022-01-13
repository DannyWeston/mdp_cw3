package com.dannyweston.mdp_cw3.views;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.viewmodels.MainActivityViewModel;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dannyweston.mdp_cw3.databinding.ActivityMainBinding;
import com.dannyweston.mdp_cw3.viewmodels.ViewModelFactory;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION }, 0);

        /* Setup viewmodel */
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityViewModel viewmodel = new ViewModelProvider(this, new ViewModelFactory(getApplication()))
                .get(MainActivityViewModel.class);

        viewmodel.getEventInvoked().observe(this, (obs) -> {
            Intent intent;
            switch ((int)obs.second.longValue()){
                case 0:
                    intent = new Intent(this, RunningActivity.class);
                    break;
                case 1:
                    intent = new Intent(this, HistoryActivity.class);
                    break;
                case 2:
                    intent = new Intent(this, SettingsActivity.class);
                    break;
                case 3:
                    intent = new Intent(this, AboutActivity.class);
                    break;
                default: return;
            }

            startActivity(new Intent(intent));
        });

        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewmodel);
    }
}