package com.umld.animals.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.umld.animals.model.AnimalModel;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends ViewModel {

    public MutableLiveData<List<AnimalModel>> animals = new MutableLiveData<List<AnimalModel>>();
    public MutableLiveData<Boolean> loadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    public void refresh() {
        AnimalModel animal1 = new AnimalModel("Lion");
        AnimalModel animal2 = new AnimalModel("Zebra");
        AnimalModel animal3 = new AnimalModel("Bear");

        List<AnimalModel> animalList = new ArrayList<>();
        animalList.add(animal1);
        animalList.add(animal2);
        animalList.add(animal3);

        animals.setValue(animalList);
        loadError.setValue(false);
        loading.setValue(false);
    }
}
