package com.doan1.mpec_restaurant.api;

import com.doan1.mpec_restaurant.object.Manager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiManager {
//https://313134cc-3d5f-4dee-8fb0-e93ee23a9174.mock.pstmn.io/maneger
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-DD HH:mm:ss").create();

    ApiManager API_MANAGER = new Retrofit.Builder().baseUrl("https://313134cc-3d5f-4dee-8fb0-e93ee23a9174.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiManager.class);

    @GET("maneger")
    Call<List<Manager>> getListManager();


}

