package com.dannyweston.mdp_cw3.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.databinding.ActivityRunOverviewBinding;
import com.dannyweston.mdp_cw3.services.RunnerService;
import com.dannyweston.mdp_cw3.services.RunnerServiceBinder;
import com.dannyweston.mdp_cw3.viewmodels.Action;
import com.dannyweston.mdp_cw3.viewmodels.RunOverviewActivityViewModel;
import com.dannyweston.mdp_cw3.viewmodels.ViewModelFactory;

public class RunOverviewActivity extends AppCompatActivity implements ActionListener {
    private RunOverviewActivityViewModel _vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve run from passed runId (bundle)
        long runId = getIntent().getExtras().getLong(getString(R.string.extraRunInfo));

        /* Setup viewmodel */
        ActivityRunOverviewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_run_overview);
        _vm = new ViewModelProvider(this, new ViewModelFactory(getApplication(), runId))
                .get(RunOverviewActivityViewModel.class);

        // Close activity on finish
        _vm.getAction().observe(this, this::onAction);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(_vm);
    }

    @Override
    public void onAction(Action action) {
        if (action.getValue() == R.integer.actionDeleteRun){
            _vm.setAction(Action.RESET_ACTION);

            // Make a dialog for confirmation (yes / no)
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(R.string.txtDeleteRunDialogDesc)
                    .setTitle(R.string.txtDeleteRunDialogTitle)
                    .setPositiveButton(R.string.txtYes, (dialog, id) -> {
                        _vm.deleteRun();
                        _vm.setAction(new Action(R.integer.actionCloseActivity));
                    })
                    .setNegativeButton(R.string.txtNo,  (dialog, id) -> {
                        // Do nothing for now
                    });

            builder.create().show();
        }
        else if (action.getValue() == R.integer.actionCloseActivity){
            this.finish();
        }
    }
}