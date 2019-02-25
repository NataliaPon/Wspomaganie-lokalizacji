/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverdev;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

//Klasa definiujaca wyglad obiektu Device 
//pojo
@XmlRootElement
public class Device {
    
    private String urzadzenie;
    private String wlasciciel;
    private String adres;
    private String lokalizacja;

    public Device (){}
    
    public Device (String urzadzenie, String wlasciciel, String adres, String lokalizacja){
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

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getLokalizacja() {
        return lokalizacja;
    }

    public void setLokalizacja(String lokalizacja) {
        this.lokalizacja = lokalizacja;
    }
    
    
    @Override
    public String toString() {
        return "{\"urzadzenie\":\"" + urzadzenie + "\"" +
                ", \"wlasciciel\":\"" + wlasciciel + "\"" +
                ", \"adres\":\"" + adres + "\""+
                ", \"lokalizacja\":\"" + lokalizacja +"\"}";
    }
}
