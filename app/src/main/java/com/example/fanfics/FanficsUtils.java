package com.example.fanfics;

import android.os.Bundle;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FanficsUtils {

    static Bundle args = new Bundle();

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://172.20.216.146:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static JsonPlaceHolderApi getJsonPlaceholder() {
        return getRetrofit().create(JsonPlaceHolderApi.class);
    }

    public static Bundle getBundle() {
        return args;
    }
}
