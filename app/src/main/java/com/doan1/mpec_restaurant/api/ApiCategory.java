package com.doan1.mpec_restaurant.api;

import com.doan1.mpec_restaurant.object.Category;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCategory {

    //http://mpecrestaurant-env.eba-3fm4k7uh.us-west-2.elasticbeanstalk.com/category?fbclid=IwAR3Qe10XcrTozZmaphRP1TJteafMvEnh6UQOchgBfEp175PHd6qm-Pr0y4I

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-DD HH:mm:ss").create();

    ApiCategory apiCategory = new Retrofit.Builder().baseUrl("http://mpecrestaurant-env.eba-3fm4k7uh.us-west-2.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiCategory.class);

    @GET("category")
    Call<List<Category>> getListCategory(@Query("fbclid") String key);
}
