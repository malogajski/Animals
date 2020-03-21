package com.umld.animals.di;

import com.umld.animals.model.AnimalApi;
import com.umld.animals.model.AnimalApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final String BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net";

    @Provides
    public AnimalApi provideAnimalApi() {
        return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())         // konvertuje response iz JSON u MODEL
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // uzima objekte iz modela i konvertuje u tim definisan u interface-u
            .build()
            .create(AnimalApi.class);
    }

    @Provides
    public AnimalApiService provideAnimalApiService() {
        return new AnimalApiService();
    }
}
