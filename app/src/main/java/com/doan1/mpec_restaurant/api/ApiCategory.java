package com.doan1.mpec_restaurant.api;

import com.doan1.mpec_restaurant.object.Category;
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

public interface ApiCategory {

    //http://mpecrestaurant-env.eba-3fm4k7uh.us-west-2.elasticbeanstalk.com/category

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-DD HH:mm:ss").create();

    ApiCategory apiCategory = new Retrofit.Builder().baseUrl("http://mpecrestaurant-env.eba-3fm4k7uh.us-west-2.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(ApiCategory.class);

    @GET("category")
    Call<List<Category>> getListCategory();

    @POST("category")
    Call <Category> postCategory(@Body Category category);

    @PUT("category/{id}")
    Call<Category> putCategory(@Path("id") int id, @Body Category category);

    @HTTP(method = "DELETE", path = "category", hasBody = true)
    Call<ResponseBody> deleteCategory(@Body ArrayList<Integer> list);

}
