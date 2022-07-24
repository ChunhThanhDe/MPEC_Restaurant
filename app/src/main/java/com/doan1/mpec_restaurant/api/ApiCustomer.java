package com.doan1.mpec_restaurant.api;

import com.doan1.mpec_restaurant.object.Customer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiCustomer {
    //https://06298a4a-153d-4af1-89d0-26b3dbbcfdfb.mock.pstmn.io/

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiCustomer apiCustomer = new Retrofit.Builder()
            .baseUrl("https://06298a4a-153d-4af1-89d0-26b3dbbcfdfb.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiCustomer.class);

    @GET("test")
    Call<List<Customer>> getListCustomer();
}

