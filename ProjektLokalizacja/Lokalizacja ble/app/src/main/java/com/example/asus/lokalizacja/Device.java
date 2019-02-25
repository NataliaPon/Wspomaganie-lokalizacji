package com.example.asus.lokalizacja;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//klasa definiujaca wyglad obiektu Device
//pojo
public class Device {
    @SerializedName("urzadzenie")
    @Expose
    private String urzadzenie;
    @SerializedName("wlasciciel")
    @Expose
    private String wlasciciel;
    @SerializedName("adres")
    @Expose
    private String adres;
    @SerializedName("lokalizacja")
    @Expose
    private String lokalizacja;

    Device(String urzadzenie, String wlasciciel, String adres, String lokalizacja)
    {
        this.urzadzenie = urzadzenie;
        this.wlasciciel = wlasciciel;
        this.adres = adres;
        this.lokalizacja = lokalizacja;
    }

    public String getUrzadzenie() {
        return urzadzenie;
    }

    public void setUrzadzenie(String urzadzenie) {
        this.urzadzenie = urzadzenie;
    }

    public String getWlasciciel() {
        return wlasciciel;
    }

    public void setWlasciciel(String wlasciciel) {
        this.wlasciciel = wlasciciel;
    }

    public String getadres() {
        return adres;
    }

    public void setadres(String adres) {
        this.adres = adres;
    }

    public String getLokalizacja() {
        return lokalizacja;
    }

    public void setLokalizacja(String lokalizacja) {
        this.lokalizacja = lokalizacja;
    }

    public String toString() {
        return "{\"urzadzenie\":\"" + urzadzenie + "\"" +
                ", \"wlasciciel\":\"" + wlasciciel + "\"" +
                ", \"adres\":\"" + adres + "\""+
                ", \"lokalizacja\":\"" + lokalizacja +"\"}";
    }
}
