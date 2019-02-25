package com.example.asus.lokalizacja;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class DevicesList extends AppCompatActivity
{

    BluetoothManager btManager;
    BluetoothAdapter btAdapter;
    BluetoothLeScanner btScanner;
    BluetoothDevice btDevice;
    private ArrayList<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
    private ArrayList<ScanResult> results = new ArrayList<ScanResult>();
    private MyAdapter arrayAdapter;
    Handler btHandler = new Handler();
    TextView name,adress,lokal, odl;
    private ListView devicesList;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    List<Device> dataList;
    Context context;
    Boolean fin = false;

//klasa wyswietlajaca wyszukane urzadzenie BLE w formie listy
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_list);
        context = this;

        devicesList = (ListView) findViewById(R.id.device);
        name = (TextView) findViewById(R.id.name);
        adress = (TextView) findViewById(R.id.address);
        lokal = (TextView) findViewById(R.id.lokal);
        odl = (TextView) findViewById(R.id.rssi);

        btManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        btScanner = btAdapter.getBluetoothLeScanner();

        if(!btAdapter.isEnabled())
        {
            btAdapter.enable();
        }
        if(!(checkSelfPermission(ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED))
        {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
        devices.clear();


        new MyTask().execute();

            devicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    btDevice = devices.get(i);
                    String a = devices.get(i).getAddress();

                    Intent connectDev = new Intent(getApplicationContext(), RestAPI.class);
                    connectDev.putExtra("a", a);//wyslanie wyniku
                    connectDev.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


                    getApplicationContext().startActivity(connectDev);
                }
            });

    }
//zapisanie wyszukanych urzadzen do ArrayList
    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {

            if(!devices.contains(result.getDevice()))
            {
                devices.add(result.getDevice());
                results.add(result);
                arrayAdapter.notifyDataSetChanged();
            }
            else
            {
                results.set(devices.indexOf(result.getDevice()),result);
                arrayAdapter.notifyDataSetChanged();
            }
        }
    };

//stworzenie adaptera listy dopiero po pobraniu danych z serwera
    class MyTask extends AsyncTask<Void, Void, List<Device>> {

        dbSerwer d = new dbSerwer();
        List<Device> dataList=null;

        @Override
        protected List<Device> doInBackground(Void... params) {
            dataList = d.getDBdata();
            return dataList;
        }

        @Override
        protected void onPostExecute(List<Device> dataList) {

            arrayAdapter = new MyAdapter(context, results, dataList);

            btScanner.startScan(leScanCallback);
            btHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btScanner.stopScan(leScanCallback);
                    arrayAdapter.notifyDataSetChanged();
                }
            }, 500000);

            devicesList.setAdapter(arrayAdapter);

        }
    }
}


