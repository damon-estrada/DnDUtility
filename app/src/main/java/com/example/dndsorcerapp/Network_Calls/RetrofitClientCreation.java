package com.example.dndsorcerapp.Retrofit_Init;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientCreation {

    private static Retrofit retrofitService = null;
    private static final String BASE_URL = "https://api.open5e.com/";

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




