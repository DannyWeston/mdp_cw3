package com.dannyweston.mdp_cw3.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.databinding.ActivityHistoryBinding;
import com.dannyweston.mdp_cw3.viewmodels.HistoryActivityViewModel;

public class HistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Setup viewmodel */
        ActivityHistoryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_history);
        HistoryActivityViewModel viewmodel = new ViewModelProvider(this).get(HistoryActivityViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewmodel);
    }

    public void overviewRunActivity(View view) {
        // Start the overview activity and pass in the pressed view

        Intent intent = new Intent(this, RunOverviewActivity.class);
        startActivity(new Intent(intent));
    }
}