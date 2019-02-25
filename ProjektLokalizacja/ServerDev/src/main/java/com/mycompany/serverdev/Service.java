/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverdev;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.DataFormat.URL;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author 
 */
@Path("service")
public class Service {
    
    DeviceDB db = new DeviceDB();
    List<Device> devices = new ArrayList<Device>();   
    
    //pobieranie danych z bazy mysql
    public Service() throws SQLException, NamingException {
        
        this.devices = db.getList();
     
    }

    
    @GET
    @Path("/getDeviceXML")
    @Produces(MediaType.APPLICATION_XML)
    public Object getDeviceXML()
    {
        return devices.get(1);
    }
    
    //pobranie wszystkich danych 
    //http://localhost:8080/ServerDev/webapi/service/getAll
    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() throws JsonProcessingException, IOException
    {
        return new ObjectMapper()
                .writeValueAsString(devices);
    }
    
    @GET
    @Path("/getStr")
    @Produces(MediaType.TEXT_HTML)
    public String getStr()
    {
        return "cos jest";
    }
    
    //http://localhost:8080/ServerDev/webapi/service/getDevice/1
    @GET
    @Path("/getDevice/{a}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDevice(@PathParam("a") int a) throws JsonProcessingException, IOException
    {
        return new ObjectMapper()
                .writeValueAsString(devices.get(a));
    }
    
    //wyslanie danej i zapisanie jej do bazy mysql
    //http://localhost:8080/ServerDev/webapi/service/addDevice
    @POST
    @Path("/addDevice")
    @Produces("text/plain")
    @Consumes("text/plain")
    public Response addDevice( String device) throws JsonProcessingException, IOException, NamingException, SQLException
    {
        String s ="\\";
            if(device.startsWith("\"")){
                int i = device.length();
                device = device.substring(1,i-1);
                device = device.replace(s, "");
            }
            
        ObjectMapper mapper = new ObjectMapper();
        Device readValue = mapper.readValue(device, Device.class);
        System.out.println("readValue = " + readValue);
        
        String n = readValue.getUrzadzenie();
        String a = readValue.getAdres();
        String w = readValue.getWlasciciel();
        String l = readValue.getLokalizacja();
        db.addDev(n, w, a, l);
        return Response.status(Status.OK).entity("Wykonano!").build();
    }
    
    //wyslanie danej z potwierdzeniem
    @POST
    @Path("/addDevice2")
	@Consumes("text/plain")
	@Produces("text/plain")
	public Response addDevice2(String device) throws JsonProcessingException, IOException {
            
            String s ="\\";
            if(device.startsWith("\"")){
                int i = device.length();
                device = device.substring(1,i-1);
                device = device.replace(s, "");
            }
            
            ObjectMapper mapper = new ObjectMapper();
            
            Device readValue = mapper.readValue(device, Device.class);
            
            System.out.println("readValue = " + readValue);
            System.out.println("readValue2 = " + device);

	return Response.status(Status.OK).entity("Wykonano!").build();
	}
        
    //wyslanie wiadomosci w formie stringa
    @POST
    @Path("/testPost")
    @Consumes("text/plain")
    public Response postClichedMessage(String message) {       
        return Response.status(200).entity(message).build();
    }
}