package com.dannyweston.mdp_cw3.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.dannyweston.mdp_cw3.R;
import com.dannyweston.mdp_cw3.dao.Run;
import com.dannyweston.mdp_cw3.repositories.RunRepository;

import java.util.List;

public class HistoryActivityViewModel extends SignallingViewModel {
    private final RunRepository _runRepo;
    private long _selected;

    public HistoryActivityViewModel(@NonNull Application application) {
        super(application);

        _runRepo = new RunRepository(application);

        _runRepo.getLastRun().observeForever(recentObserver);
        _runRepo.getLongestRun().observeForever(longestObserver);
        _runRepo.getRunCount().observeForever(countObserver);
    }

    public long getSelected(){ return _selected; }

    public void btnViewRunClick(View view) {
        _selected = (long)view.getTag();

        setAction(new Action(R.integer.actionOpenRunOverviewActivity));
    }

    public LiveData<List<Run>> getRuns(){
        return _runRepo.getRuns();
    }

    @Override
    protected void onCleared() {
        // Remove observers
        _runRepo.getLastRun().removeObserver(recentObserver);
        _runRepo.getLongestRun().removeObserver(longestObserver);
        _runRepo.getRunCount().removeObserver(countObserver);

        super.onCleared();
    }

    // Run count
    private final MutableLiveData<Long> count = new MutableLiveData<>();
    public LiveData<Long> getCount(){
        return count;
    }
    private final Observer<Long> countObserver = count::setValue;

    // Recent Run
    private final MutableLiveData<Run> lastRun = new MutableLiveData<>();
    public LiveData<Run> getLastRun(){
        return lastRun;
    }
    private final Observer<Run> recentObserver = lastRun::setValue;

    // Longest Run
    private final MutableLiveData<Run> longestRun = new MutableLiveData<>();
    public LiveData<Run> getLongestRun(){
        return longestRun;
    }
    private final Observer<Run> longestObserver = longestRun::setValue;
}
