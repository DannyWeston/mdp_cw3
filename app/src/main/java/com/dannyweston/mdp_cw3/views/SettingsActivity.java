package com.dannyweston.mdp_cw3.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.databinding.ActivityMainBinding;
import com.dannyweston.mdp_cw3.databinding.ActivitySettingsBinding;
import com.dannyweston.mdp_cw3.viewmodels.MainActivityViewModel;
import com.dannyweston.mdp_cw3.viewmodels.SettingsActivityViewModel;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Setup viewmodel */
        ActivitySettingsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        SettingsActivityViewModel viewmodel = new ViewModelProvider(this).get(SettingsActivityViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewmodel);
    }
}