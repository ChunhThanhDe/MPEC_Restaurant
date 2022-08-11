package com.doan1.mpec_restaurant.api;

import com.doan1.mpec_restaurant.object.Customer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiCustomer {
    //http://mpecrestaurant-env.eba-3fm4k7uh.us-west-2.elasticbeanstalk.com/customer

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiCustomer apiCustomer = new Retrofit.Builder()
            .baseUrl("http://mpecrestaurant-env.eba-3fm4k7uh.us-west-2.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiCustomer.class);

    @GET("customer")
    Call<List<Customer>> getListCustomer();

    @POST("customer")
    Call<Customer> addCustomer(@Body Customer customer);

    @PUT("customer/{id}")
    Call<Customer> putCustomer(@Path("id") int id, @Body Customer customer);

    @HTTP(method = "DELETE", path = "customer", hasBody = true)
    Call<ResponseBody> deleteCustomer(@Body ArrayList<Integer> list);
}

