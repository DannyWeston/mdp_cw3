package com.dannyweston.mdp_cw3.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.databinding.ActivityHistoryBinding;
import com.dannyweston.mdp_cw3.viewmodels.HistoryActivityViewModel;
import com.dannyweston.mdp_cw3.viewmodels.ViewModelFactory;

public class HistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Setup viewmodel */
        ActivityHistoryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_history);
        HistoryActivityViewModel viewmodel = new ViewModelProvider(this, new ViewModelFactory(getApplication()))
                .get(HistoryActivityViewModel.class);

        // Listen for signal from viewmodel to view another activity
        viewmodel.getEventInvoked().observe(this, (obs) -> {
            if (obs.first){
                // Start the overview activity and pass in the runId to use
                Intent intent = new Intent(this, RunOverviewActivity.class);
                intent.putExtra("showRunId", obs.second);

                startActivity(new Intent(intent));
            }
        });

        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewmodel);
    }

}