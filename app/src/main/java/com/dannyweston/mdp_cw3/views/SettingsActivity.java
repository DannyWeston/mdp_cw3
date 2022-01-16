package com.dannyweston.mdp_cw3.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.databinding.ActivitySettingsBinding;
import com.dannyweston.mdp_cw3.viewmodels.Action;
import com.dannyweston.mdp_cw3.viewmodels.SettingsActivityViewModel;
import com.dannyweston.mdp_cw3.viewmodels.ViewModelFactory;

public class SettingsActivity extends AppCompatActivity implements ActionListener {

    private SettingsActivityViewModel _vm;

    private SharedPreferences _pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get preference manager
        _pref = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        /* Setup viewmodel */
        ActivitySettingsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        _vm = new ViewModelProvider(this, new ViewModelFactory(getApplication()))
                .get(SettingsActivityViewModel.class);

        // Make a dropdown (spinner) for selecting units
        Spinner dropdown = findViewById(R.id.dropdown_units);
        String[] items = new String[]{"Metres", "Kilometres", "Miles"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                _vm.setUnits(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing for now
            }
        });

        // Fetch units from preferences
        int units = _pref.getInt(getString(R.string.prefUnits), R.integer.Metres);
        _vm.setUnits(units);

        dropdown.setAdapter(adapter);
        dropdown.setSelection(units); // Set dropdown value according to preference

        // Listen for actions
        _vm.getAction().observe(this, this::onAction);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(_vm);
    }

    @Override
    public void onAction(Action action) {
        if (action.getValue() == R.integer.actionSaveSettings){
            SharedPreferences.Editor prefEditor = _pref.edit();

            Integer unit = _vm.getUnits().getValue();
            if (unit != null) prefEditor.putInt(getString(R.string.prefUnits), unit);

            prefEditor.apply();

            // Show popup message for settings saved
            Toast.makeText(getApplicationContext(),R.string.txtSavedSettingsPopup,Toast.LENGTH_LONG).show();

            _vm.setAction(Action.RESET_ACTION);
        }
    }
}