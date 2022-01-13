package com.dannyweston.mdp_cw3.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.databinding.ActivityRunOverviewBinding;
import com.dannyweston.mdp_cw3.viewmodels.RunOverviewActivityViewModel;
import com.dannyweston.mdp_cw3.viewmodels.ViewModelFactory;

public class RunOverviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve run from passed runId (bundle)
        long _runId = getIntent().getExtras().getLong("showRunId");

        /* Setup viewmodel */
        ActivityRunOverviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_run_overview);
        RunOverviewActivityViewModel viewmodel = new ViewModelProvider(this, new ViewModelFactory(getApplication(), _runId))
                .get(RunOverviewActivityViewModel.class);

        // Close activity on finish
        viewmodel.getEventInvoked().observe(this, (obs) -> {
            if (obs.first) this.finish();
        });

        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewmodel);
    }
}