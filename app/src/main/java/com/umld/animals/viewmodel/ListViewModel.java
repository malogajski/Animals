package com.umld.animals.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.umld.animals.model.AnimalApiService;
import com.umld.animals.model.AnimalModel;
import com.umld.animals.model.ApiKeyModel;
import com.umld.animals.util.SharedPreferecesHelper;

import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class ListViewModel extends AndroidViewModel {       //AndroidViewModel instead ViewModel because we need Context. If we use Activit/Fragment context, that can destroy context if goes to background etc.
                                                            // All of that because we need context for SharedPreferecesHelper where we store a Key!

    private AnimalApiService apiService = new AnimalApiService();
    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<List<AnimalModel>> animals = new MutableLiveData<List<AnimalModel>>();
    public MutableLiveData<Boolean> loadError = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    private SharedPreferecesHelper prefs;
    private boolean invalidApiKey = false;

    public ListViewModel(Application application) {
        super(application);
        prefs = new SharedPreferecesHelper(application);
    }

    public void hardRefresh() {
        loading.setValue(true);
        getKey();
    }

    public void refresh() {
        loading.setValue(true);
        invalidApiKey = false;
        String key = prefs.getApiKey();
        if (key != null && !key.equals("")) {
            getKey();
        } else {
            getAnimals(key);
        }
    }

    private void getKey() {
        disposable.add(
                apiService.getApiKey()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ApiKeyModel>() {
                            @Override
                            public void onSuccess(ApiKeyModel key) {
                                if (key.key.isEmpty()) {
                                    loadError.setValue(true);
                                    loading.setValue(false);
                                } else {
                                    prefs.saveApiKey(key.key);
                                    getAnimals(key.key);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                loading.setValue(false);
                                loadError.setValue(true);
                            }
                        })
        );
    }

    private void getAnimals(String key) {
        disposable.add(
                apiService.getAnimals(key)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<AnimalModel>>() {
                            @Override
                            public void onSuccess(List<AnimalModel> animalModels) {
                                loadError.setValue(false);
                                animals.setValue(animalModels);
                                loading.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (!invalidApiKey) {
                                    invalidApiKey = true;
                                    getKey();
                                } else {
                                    e.printStackTrace();
                                    loading.setValue(false);
                                    loadError.setValue(true);
                                }
                            }
                        })
        );
    }

    @Override
    protected void onCleared() {    // Kada se aplikacija vrati iz background-a
        super.onCleared();
        disposable.clear();
    }
}
