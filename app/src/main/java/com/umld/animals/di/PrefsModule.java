package com.umld.animals.di;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.umld.animals.util.SharedPreferecesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.umld.animals.di.TypeOfContext.CONTEXT_ACTIVITY;
import static com.umld.animals.di.TypeOfContext.CONTEXT_APP;

@Module
public class PrefsModule {

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_APP)
    public SharedPreferecesHelper providesSharedPreferences(Application app) {
        return new SharedPreferecesHelper(app);
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACTIVITY)
    public SharedPreferecesHelper providesActivitySharedPreferences(AppCompatActivity activity) {
        return new SharedPreferecesHelper(activity);
    }
}
