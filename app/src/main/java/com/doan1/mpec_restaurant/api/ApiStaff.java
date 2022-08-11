package com.doan1.mpec_restaurant.api;

import com.doan1.mpec_restaurant.object.Staff;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiStaff {
//http://mpecrestaurant-env.eba-3fm4k7uh.us-west-2.elasticbeanstalk.com/staff

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-DD HH:mm:ss").create();

    ApiStaff apiStaff = new Retrofit.Builder().baseUrl("http://mpecrestaurant-env.eba-3fm4k7uh.us-west-2.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiStaff.class);

    @GET("staff")
    Call<List<Staff>> getListStaff();


}

