/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverdev;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/db")
@Produces("application/json")
@Consumes("application/json")
public class DeviceDB {
    
   
    Connection con;
    protected Connection getConnection() throws SQLException, NamingException, InstantiationException, IllegalAccessException {
        try {
          
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bazaurzadzen", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DeviceDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public Device getFromResultSet(ResultSet rs) throws SQLException {
        Device event = new Device();
        event.setUrzadzenie(rs.getString("urzadzenie"));
        event.setWlasciciel(rs.getString("wlasciciel"));
        event.setAdres(rs.getString("adres"));
        event.setLokalizacja(rs.getString("lokalizacja"));
        
        return event;
    }
    // pobiera dane z bazy mysql
    public List getList() throws SQLException, NamingException {
        
        try {
            
            List<Device> events = new ArrayList<Device>();
            Connection db = getConnection();
            
            try {
                PreparedStatement st = db.prepareStatement("SELECT urzadzenie, wlasciciel, adres, lokalizacja from urzadzenia");
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Device e = getFromResultSet(rs);
                    events.add(e);
                }
                return events;
            } finally {
                db.close();
            }
        }   catch (InstantiationException ex) {
            Logger.getLogger(DeviceDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DeviceDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
//dodanie rekordu do bazy mysql
    public void addDev(String n, String w,String a, String l) throws NamingException, SQLException
    {
        try{
            Connection db = getConnection();

            try{
                PreparedStatement st = db.prepareStatement("INSERT INTO urzadzenia ( urzadzenie, wlasciciel, adres, lokalizacja) VALUES ( \""+n+"\", \""+w+"\", \""+a+"\", \""+l+"\")");
                st.executeUpdate();  
                
            }finally{
            db.close();
        }
            
        } catch (InstantiationException ex) {
            Logger.getLogger(DeviceDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DeviceDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
