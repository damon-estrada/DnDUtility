package com.example.dndsorcerapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientCreation {

    private static Retrofit retrofitService = null;
    private static final String BASE_URL = "http://dnd5eapi.co/api/";

    public static Retrofit getRetrofitCreation() {

        if (retrofitService == null) {
            retrofitService = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitService;
    }
}




