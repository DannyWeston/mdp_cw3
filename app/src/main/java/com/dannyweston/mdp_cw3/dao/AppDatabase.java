package com.dannyweston.mdp_cw3.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dannyweston.mdp_cw3.BuildConfig;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = { LocationUpdate.class, Run.class },
        version = 6,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "db_cw3";
    private static final int EXECUTOR_THREADS = 3;

    private final ExecutorService dbExecutors = Executors.newFixedThreadPool(EXECUTOR_THREADS);

    public abstract LocUpdateDao locationDao();
    public abstract RunDao runDao();

    private static volatile AppDatabase _instance;
    private static boolean _cleared = false;

    public static AppDatabase getInstance(final Context context) {
        if (_instance == null) {
            synchronized (AppDatabase.class) {
                if (_instance == null)
                    _instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
            }
        }

        // Clear all tables if debug
        if (BuildConfig.DEBUG && !_cleared){
            _instance.getExecutors().execute(() -> _instance.clearAllTables());
            _cleared = true;
        }

        return _instance;
    }

    public ExecutorService getExecutors(){
        return dbExecutors;
    }
}

