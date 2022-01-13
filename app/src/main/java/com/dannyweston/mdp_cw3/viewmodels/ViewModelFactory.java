package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * This class was produced to solve the issue of not being able
 * to pass parameters into a viewmodel. I considered using the Factory
 * design pattern a long time before using this solution referenced below,
 * but couldn't figure out how to do it with the janky-nature of it
 *
 * Inspiration: https://stackoverflow.com/a/50374088
 */

public class ViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private final Application _application;
    private final Object[] _args;

    public ViewModelFactory(Application application, Object... args) {
        super(application);

        _application = application;
        _args = args;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == RunOverviewActivityViewModel.class) {
            // Following for some reason introduces a bug of not being able to identify correct ViewModel from generic
            @SuppressWarnings("unchecked")
            T result = (T) new RunOverviewActivityViewModel(_application, (long) _args[0]);
            return result;
        }

        return super.create(modelClass);
    }
}
