package com.doan1.mpec_restaurant.api;

import com.doan1.mpec_restaurant.object.Dish;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiDish {
    //https://aeedbec5-14ec-46c4-a63b-a7b8b25a1e33.mock.pstmn.io/dish

    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();

    ApiDish apiDish = new Retrofit.Builder()
            .baseUrl("https://aeedbec5-14ec-46c4-a63b-a7b8b25a1e33.mock.pstmn.io/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiDish.class);

    @Multipart
    @POST("posts")
    Call<Dish> postDish(@Part (Const.KEY_DISH_NAME) RequestBody name,
                        @Part (Const.KEY_DISH_PRICE) RequestBody price,
                        @Part (Const.KEY_DISH_SHORTDES) RequestBody shortDescription,
                        @Part (Const.KEY_DISH_CATEGORY) RequestBody categoryName,
                        @Part MultipartBody.Part image);
}

