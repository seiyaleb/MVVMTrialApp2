package com.example.mvvmtrialapp2.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("api/search")
    Call<City> getCity(@Query("zipcode") String zipcode);
}
