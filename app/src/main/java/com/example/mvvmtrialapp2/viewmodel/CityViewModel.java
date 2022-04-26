package com.example.mvvmtrialapp2.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmtrialapp2.model.Address;
import com.example.mvvmtrialapp2.model.CityRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityViewModel extends AndroidViewModel {

    private MutableLiveData<List<Address>> address = new MutableLiveData<List<Address>>();
    private CityRepository repository;

    public CityViewModel(@NonNull Application application) {
        super(application);
        repository = new CityRepository();
    }

    public LiveData<List<Address>> getAddress() { return address; }

    //取得したデータを設定
    //非同期で実行
    public void searchAddress(String zipcode) {

        CityRepository.executorService.execute(() -> {
            address.postValue(repository.getAddress(zipcode));
        });
    }

    //データを空にする
    public void clearAddress() {
        address.postValue(Collections.emptyList());
    }
}
