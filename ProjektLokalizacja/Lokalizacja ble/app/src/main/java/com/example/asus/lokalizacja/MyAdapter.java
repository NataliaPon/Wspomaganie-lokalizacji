package com.example.asus.lokalizacja;


import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//definiowanie wygladu DevicesList na podstawie wyszukanych urzadzen i danych z bazy z serwera
public class MyAdapter extends ArrayAdapter<ScanResult> {

    private final Context context;
    private final ArrayList<ScanResult> values;
    TextView name, adress, lokal, odl;
    double rssi, tx;
    double ratio, distance;
//    dbConnection dbCon;
    String nazwa, addr, loc;
    String [] daneUrzadzenia;
    List<Device> dataList;
    dbSerwer dbS = new dbSerwer();
    View rowView;


    //
    public MyAdapter(@NonNull Context context, ArrayList<ScanResult> values, List<Device> dataList) {
        super(context, R.layout.activity_devices_list,values);
        this.context = context;
        this.values = values;
        this.dataList = dataList;

        //kod z sqlite
//        dbCon = new dbConnection(context);
//        dbCon =dbCon.open();

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.activity_devices_list, parent,false);

        name = (TextView) rowView.findViewById(R.id.name);
        adress = (TextView) rowView.findViewById(R.id.address);
        lokal = (TextView) rowView.findViewById(R.id.lokal);
        odl = (TextView) rowView.findViewById(R.id.rssi);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);
        df.setMinimumFractionDigits(3);

        ScanResult result = values.get(position);


/*        Cursor cr = dbCon.getRecord(result.getDevice().getAddress().toString());
        cr.moveToFirst();
        if (cr.moveToFirst()) {
            try {
                nazwa = cr.getString(cr.getColumnIndex("name"));
                addr = cr.getString(cr.getColumnIndex("addres"));
                loc = cr.getString(cr.getColumnIndex("localization"));
            }catch (Exception e){}
        }*/


        rssi = result.getRssi();

        tx =-59;//pomiedzy -59 a -65
        ratio = rssi * 1.0 / tx;
        if(ratio<1)
        {
            distance = Math.pow(ratio, 10);
        }
        else
        {
            distance = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
        }

        if(!dataList.isEmpty()) {
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).getadres().equals(result.getDevice().getAddress())) {
                    name.setText(dataList.get(i).getUrzadzenie());
                    odl.setText("Odleglosc: " + df.format(distance) + " [m]");
                    lokal.setText("Wlasciciel:" + dataList.get(i).getWlasciciel() + " Polozenie: " + dataList.get(i).getLokalizacja());
                    adress.setText(dataList.get(i).getadres());

                    return rowView;
                } }
        }

            if (result.getDevice().getName() == null) {
                name.setText("Nieznane urzadzenie");
                odl.setText("Odleglosc: " + df.format(distance)+ " [m]");
                lokal.setText("Lokalizacja nieznana");
                adress.setText(result.getDevice().getAddress());
            } else {
                name.setText(result.getDevice().getName());
                odl.setText(df.format(distance)+ " [m]");
                lokal.setText("Lokalizacja nieznana");
                adress.setText(result.getDevice().getAddress());
            }

        /*
        if( cr.moveToFirst())
        {
            name.setText(nazwa+"\n"+" (" + result.getDevice().getAddress()+") "+"\n"+"Polozenie: "+loc+"\n"+"Odleglosc: "+df.format(distance)+ " [m]");
        }
        else {
            if (result.getDevice().getName() == null) {
                name.setText("Nieznane urzadzenie " + "\n" + " (" + result.getDevice().getAddress() + ") " + "\n" + "Odleglosc: " + df.format(distance) + " [m]");
            } else {
                name.setText(result.getDevice().getName() + "\n" + " (" + result.getDevice().getAddress() + ") " + "\n" + "Odleglosc: " + df.format(distance) + " [m]");
            }
        }
//        cr.close();
//        dbCon.close();*/
        return rowView;
    }

}

