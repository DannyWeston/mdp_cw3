package com.dannyweston.mdp_cw3.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.os.Bundle;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.databinding.ActivityAboutBinding;
import com.dannyweston.mdp_cw3.viewmodels.AboutActivityViewModel;
import com.dannyweston.mdp_cw3.viewmodels.ViewModelFactory;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Setup viewmodel */
        ActivityAboutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        AboutActivityViewModel viewmodel = new ViewModelProvider(this, new ViewModelFactory(getApplication()))
                .get(AboutActivityViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewmodel);
    }
}