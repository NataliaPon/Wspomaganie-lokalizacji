package com.example.asus.lokalizacja;

import android.bluetooth.BluetoothDevice;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//klasa wyswitlajaca i zapisujaca wszystkie dane  z/do bazy mysql przez serwer Apache Tomcat
public class RestAPI extends AppCompatActivity {

    private TextView addres,textView;
    private EditText name;
    private EditText local,wl,addr;
    private Button add, edit, del;
    String dAddr;
    GetApi mApi;

    private List<Device> dataList;
    Call<List<Device>> call;
    Call<ResponseBody> call1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_api);


        addres = (TextView) findViewById(R.id.textView8);
        textView = (TextView) findViewById(R.id.textView5);
        name = (EditText) findViewById(R.id.name);
        local = (EditText) findViewById(R.id.local);
        wl= (EditText) findViewById(R.id.editText);
        //addr = (EditText) findViewById(R.id.editText2);
        add = (Button) findViewById(R.id.button2);
        edit = (Button) findViewById(R.id.button3);
        del = (Button) findViewById(R.id.button4);

        new MyTask().execute();

        mApi = RetrofitClient.getClient().create(GetApi.class);

        call = mApi.getPostData();


        System.out.print("Url: "+call.request().url());

        call.enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {

                dataList = response.body();
                for (int i = 0; i < dataList.size(); i++) {
                    textView.append(dataList.get(i).toString()+"\n");
                }
            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
                System.out.println("Err"+t.getMessage());
                System.out.println("");
                Toast.makeText(RestAPI.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//wyslanie danych na serwer
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Device dev = new Device(name.getText().toString(),wl.getText().toString(),
                        addres.getText().toString(),local.getText().toString());

                if(!TextUtils.isEmpty(dev.getadres()) && !TextUtils.isEmpty(dev.getUrzadzenie())
                        && !TextUtils.isEmpty(dev.getLokalizacja()) && !TextUtils.isEmpty(dev.getWlasciciel()))
                {

                    call1 = mApi.addDevice(dev.toString());


                    call1.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if(response.isSuccess())
                            {
                                Toast.makeText(RestAPI.this, "Dodano! ", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                try {
                                    Toast.makeText(RestAPI.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
//                                    Log.d("mojblad",response.errorBody().string());
//                                    System.out.print("Blad moj: "+response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(RestAPI.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                Intent mainIntent = new Intent(RestAPI.this, DevicesList.class);
                startActivity(mainIntent);
            }
        });

//powrot do menu
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent = new Intent(RestAPI.this, MainActivity.class);
                startActivity(mainIntent);
                }

        });
//powrot
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(RestAPI.this, DevicesList.class);
                startActivity(mainIntent);
            }
        });
    }

    //adres ma byc wpisany do textView dopiero po zaladowaniu go
    class MyTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            dAddr = getIntent().getExtras().getString("a");
            return dAddr;
        }

        @Override
        protected void onPostExecute(String dAddr) {
            addres.setText(dAddr);
        }
    }

}
