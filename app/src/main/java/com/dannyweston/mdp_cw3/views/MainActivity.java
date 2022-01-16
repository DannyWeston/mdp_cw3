package com.dannyweston.mdp_cw3.views;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.viewmodels.Action;
import com.dannyweston.mdp_cw3.viewmodels.MainActivityViewModel;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.dannyweston.mdp_cw3.databinding.ActivityMainBinding;
import com.dannyweston.mdp_cw3.viewmodels.ViewModelFactory;

public class MainActivity extends AppCompatActivity implements ActionListener {
    private MainActivityViewModel _vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(this, new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION }, 0);

        /* Setup viewmodel */
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        _vm = new ViewModelProvider(this, new ViewModelFactory(getApplication()))
                .get(MainActivityViewModel.class);

        // Setup action listening
        _vm.getAction().observe(this, this::onAction);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(_vm);
    }

    @Override
    public void onAction(Action action) {
        Intent intent;
        if (action.getValue() == R.integer.actionOpenRunActivity) {
            intent = new Intent(this, RunningActivity.class);
        } else if (action.getValue() == R.integer.actionOpenHistoryActivity) {
            intent = new Intent(this, HistoryActivity.class);
        } else if (action.getValue() == R.integer.actionOpenSettingsActivity) {
            intent = new Intent(this, SettingsActivity.class);
        } else if (action.getValue() == R.integer.actionOpenAboutActivity) {
            intent = new Intent(this, AboutActivity.class);
        } else { return; }

        _vm.setAction(Action.RESET_ACTION);
        startActivity(new Intent(intent));
    }
}