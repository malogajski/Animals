package com.umld.animals.di;

import com.umld.animals.model.AnimalApiService;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(AnimalApiService service);
}
