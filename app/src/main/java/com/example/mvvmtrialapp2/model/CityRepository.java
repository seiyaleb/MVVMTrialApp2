package com.example.mvvmtrialapp2.model;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityRepository {

    private ApiInterface service;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public CityRepository() {

        //APIインスタンスを生成
        service = new Retrofit.Builder()
                .baseUrl("https://zipcloud.ibsnet.co.jp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface.class);
    }

    //郵便番号をもとにデータ取得
    public List<Address> getAddress(String zipcode)  {
        try {
            //通信結果を受け取る
            Response<City> response = service.getCity(zipcode).execute();

            if(response.isSuccessful()) {
                City city = response.body();
                List<Address> address = city.getResults();
                return address;

            } else {
                Log.d("message2", "error_code" + response.code());
            }

        } catch (IOException e) {

            Log.i("message2", "error:" + e.getMessage());

        }
        return null;
    }
}
