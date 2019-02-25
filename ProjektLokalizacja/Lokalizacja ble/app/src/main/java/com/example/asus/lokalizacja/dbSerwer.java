package com.example.asus.lokalizacja;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//pobieranie danych z bazy mysql z serwera Apache Tomcat
//dane sa uzywane w klasie DevicesList
public class dbSerwer {
    private List<Device> dataList;
    GetApi mApi;
    Call<List<Device>> call;

    public List<Device> getDBdata (){

        mApi = RetrofitClient.getClient().create(GetApi.class);
        call = mApi.getPostData();

        dataList = new ArrayList<Device>();
        try {
            //dataList = call.execute().body();
            Response<List<Device>> response = call.execute();
            dataList = response.body();
        } catch (IOException e) {
            Log.d("Blad",e.toString());
        }
        return dataList;

    }

}
