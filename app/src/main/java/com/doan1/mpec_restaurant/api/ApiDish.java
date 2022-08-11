package com.doan1.mpec_restaurant.api;

import com.doan1.mpec_restaurant.object.Category;
import com.doan1.mpec_restaurant.object.Dish;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiDish {
    //http://mpecrestaurant-env.eba-3fm4k7uh.us-west-2.elasticbeanstalk.com/dish

    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();

    ApiDish apiDish = new Retrofit.Builder()
            .baseUrl("http://mpecrestaurant-env.eba-3fm4k7uh.us-west-2.elasticbeanstalk.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiDish.class);

    @Multipart
    @POST("dish")
    Call<Dish> postDish(@Part (Const.KEY_DISH_NAME) RequestBody name,
                        @Part (Const.KEY_DISH_PRICE) RequestBody price,
                        @Part (Const.KEY_DISH_SHORTDES) RequestBody shortDescription,
                        @Part (Const.KEY_DISH_CATEGORY) RequestBody categoryName,
                        @Part MultipartBody.Part image);

    @GET ("dish")
    Call<List<Dish>> getListDish();

    @Multipart
    @PUT("dish/{id}")
    Call<Dish> putDish(@Path("id") int id,
                           @Part (Const.KEY_DISH_NAME) RequestBody name,
                           @Part (Const.KEY_DISH_PRICE) RequestBody price,
                           @Part (Const.KEY_DISH_SHORTDES) RequestBody shortDescription,
                           @Part (Const.KEY_DISH_CATEGORY) RequestBody categoryName,
                           @Part MultipartBody.Part image);

    @Multipart
    @PUT("dish/{id}")
    Call<Dish> putDishNoneImage(@Path("id") int id,
                       @Part (Const.KEY_DISH_NAME) RequestBody name,
                       @Part (Const.KEY_DISH_PRICE) RequestBody price,
                       @Part (Const.KEY_DISH_SHORTDES) RequestBody shortDescription,
                       @Part (Const.KEY_DISH_CATEGORY) RequestBody categoryName);

    @HTTP(method = "DELETE", path = "dish", hasBody = true)
    Call<ResponseBody> deleteDish(@Body ArrayList<Integer> list);
}

