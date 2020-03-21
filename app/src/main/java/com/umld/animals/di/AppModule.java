package com.umld.animals.di;

import android.app.Application;

import dagger.Module;
import dagger.Provides;
@Module
public class AppModule {

    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides Application providesApp() {
        return app;
    }
}
