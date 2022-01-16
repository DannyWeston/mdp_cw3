package com.dannyweston.mdp_cw3.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.databinding.ActivityHistoryBinding;
import com.dannyweston.mdp_cw3.viewmodels.Action;
import com.dannyweston.mdp_cw3.viewmodels.HistoryActivityViewModel;
import com.dannyweston.mdp_cw3.viewmodels.ViewModelFactory;

public class HistoryActivity extends AppCompatActivity implements ActionListener {
    private HistoryActivityViewModel _vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Setup viewmodel */
        ActivityHistoryBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_history);
        _vm = new ViewModelProvider(this, new ViewModelFactory(getApplication()))
                .get(HistoryActivityViewModel.class);

        // set up the RecyclerView
        RecyclerView runView = findViewById(R.id.list_AllRuns);
        runView.setLayoutManager(new LinearLayoutManager(this));
        RunRecyclerViewAdapter adapter = new RunRecyclerViewAdapter(this);
        adapter.setClickListener((a, b) -> _vm.viewRun(a));
        runView.setAdapter(adapter);

        _vm.getRuns().observe(this, (runs) -> {
            if (runs != null) adapter.setData(runs);
        });

        // Listen for actions
        _vm.getAction().observe(this, this::onAction);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(_vm);
    }

    @Override
    public void onAction(Action action) {
        if (action.getValue() == R.integer.actionOpenRunOverviewActivity){
            // Start the overview activity and pass in the runId to use
            Intent intent = new Intent(this, RunOverviewActivity.class);
            intent.putExtra(getString(R.string.extraRunInfo), _vm.getSelected());
            _vm.setAction(Action.RESET_ACTION);

            startActivity(new Intent(intent));
        }
    }
}