package com.dannyweston.mdp_cw3.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.databinding.ActivityRunOverviewBinding;
import com.dannyweston.mdp_cw3.viewmodels.HistoryActivityViewModel;
import com.dannyweston.mdp_cw3.viewmodels.RunOverviewActivityViewModel;

public class RunOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Setup viewmodel */
        ActivityRunOverviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_run_overview);
        RunOverviewActivityViewModel viewmodel = new ViewModelProvider(this).get(RunOverviewActivityViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewmodel);
    }
}