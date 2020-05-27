package com.project.myapp.services;

import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.project.myapp.entity.Don;
import com.project.myapp.utils.Statics;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceDon {
    public ArrayList<Don> dons;

    public static ServiceDon instance=null;
    public boolean resultOk;
    private ConnectionRequest req;

    private ServiceDon(){
        req = new ConnectionRequest();
    }

    public static ServiceDon getInstance(){
        if(instance == null){
            instance = new ServiceDon();
        }
        return instance;
    }

    public boolean addDon(Don d){
        String url = Statics.BASE_URL+"/dons/new";
        req.setUrl(url);
        req.addArgument("somme",String.valueOf(d.getSomme()));
        req.addArgument("date",d.getDate().toString());
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }

    public ArrayList<Don> parseDons(String jsonText) throws IOException {
        dons = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> donsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)donsListJson.get("root");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        for(Map<String,Object> obj : list){
            Don d = new Don();
            int id = (int)Float.parseFloat(obj.get("id").toString());
            d.setId(id);
            d.setSomme(Double.parseDouble(obj.get("somme").toString()));
            d.setDate(LocalDate.parse(obj.get("date").toString().replaceAll("\\s",""),formatter));
            dons.add(d);
        }
        return dons;
    }

    public ArrayList<Don> getAllDons(){
        String url = Statics.BASE_URL+"/dons/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    dons = parseDons(new String(req.getResponseData()));
                } catch (IOException ex) {
                    Logger.getLogger(ServiceDon.class.getName()).log(Level.SEVERE, null, ex);
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return dons;
    }
}
