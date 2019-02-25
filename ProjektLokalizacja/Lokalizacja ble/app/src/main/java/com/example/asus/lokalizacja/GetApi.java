package com.example.asus.lokalizacja;

import java.util.List;

import com.squareup.okhttp.RequestBody;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GetApi {
    //pobranie listy
    @GET("getAll")
    Call<List<Device>> getPostData();

    //wyslanie json w formie string
    @Headers("Content-Type:text/plain")
    @POST("addDevice")
    Call<ResponseBody> addDevice(@Body String device);

    //wyslanie napisu
    @FormUrlEncoded
    @Headers("Content-Type:text/plain")
    @POST("testPost")
    Call<ResponseBody> addMessage(@Field("message") String message);


}
