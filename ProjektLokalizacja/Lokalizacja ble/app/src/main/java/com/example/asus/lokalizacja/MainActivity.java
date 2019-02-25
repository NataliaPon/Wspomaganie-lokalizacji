package com.example.asus.lokalizacja;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//wlaczenie / wylaczenie ble i lokalizacji
//rozpoczecie skanowania
public class MainActivity extends AppCompatActivity {

    private BluetoothManager btManager;
    private BluetoothAdapter btAdapter;
    private Button BLEon,skan,loc;
    private LocationManager manager;
    Context context;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        btManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();

        BLEon = (Button) findViewById(R.id.ble);
        BLEon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!btAdapter.isEnabled())
                {
                    btAdapter.enable();
                    BLEon.setText("BLE wlaczone");
                    BLEon.setBackgroundColor(Color.BLUE);
                    //BLEon.setTextColor();
                }
                else
                {
                    btAdapter.disable();
                    BLEon.setText("BLE wylaczone");
                    BLEon.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        if (btAdapter.isEnabled())
        {
            BLEon.setBackgroundColor(Color.BLUE);
            BLEon.setText("BLE wlaczone");
        }
        else
        {
            BLEon.setBackgroundColor(Color.LTGRAY);
            BLEon.setText("BLE wylaczone");
        }


        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        loc = (Button) findViewById(R.id.button);
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    loc.setText("Lokalizacja wlaczona");
                    loc.setBackgroundColor(Color.BLUE);
                }


            }
        });

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            loc.setBackgroundColor(Color.BLUE);
            loc.setText("Lokalizacja wlaczona");
        }

        else
        {
            loc.setBackgroundColor(Color.GRAY);
            loc.setText("Lokalizacja wylaczona");
        }


        skan = (Button) findViewById(R.id.skan);
        //przejscie do innego ekranu
        skan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(MainActivity.this, DevicesList.class);
                startActivity(mainIntent);
            }
        });


    }
}

