package com.example.asus.lokalizacja;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Nawiazywanie polaczenia z serwerem
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String baseUrl = "http://192.168.1.20:8080/ServerDev/webapi/service/";
//192.168.8.100
//192.168.1.20

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
