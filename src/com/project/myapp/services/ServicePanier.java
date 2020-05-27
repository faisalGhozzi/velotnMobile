package com.project.myapp.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.project.myapp.entity.Panier;
import com.project.myapp.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicePanier {
    public ArrayList<Panier> panier;

    public static  ServicePanier instance = null;
    public boolean resultOk;
    private ConnectionRequest req;

    private ServicePanier(){
        req = new ConnectionRequest();
    }

    public static ServicePanier getInstance(){
        if(instance == null){
            instance = new ServicePanier();
        }
        return instance;
    }

    public boolean addPanier(Panier p){
        String url = Statics.BASE_URL+"/panierjson/new";
        req.setUrl(url);
        req.addArgument("product",String.valueOf(p.getProduit_id()));
        req.addArgument("prix_unitaire",String.valueOf(p.getPrix_unitaire()));
        req.addArgument("prix_total",String.valueOf(p.getPrix_total()));
        req.addArgument("qte",String.valueOf(p.getQte()));
        req.addArgument("user",String.valueOf(2));
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

    public ArrayList<Panier> parsePanier(String jsonText) throws IOException{
        panier = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> panierListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)panierListJson.get("root");
        for(Map<String,Object> obj : list){
            Panier p = new Panier();
            int id = (int)Float.parseFloat(obj.get("id").toString());
            p.setId(id);
            int qte = (int)Float.parseFloat(obj.get("qte").toString());
            p.setQte(qte);
            p.setPrix_unitaire(Double.parseDouble(obj.get("prix_unitaire").toString()));
            p.setPrix_total(Double.parseDouble(obj.get("prix_total").toString()));
            int product_id = (int)Float.parseFloat(obj.get("produit_id").toString());
            p.setProduit_id(product_id);
            int user_id = (int)Float.parseFloat(obj.get("user_id").toString());
            p.setUser_id(user_id);
            panier.add(p);
        }
        return panier;
    }

    public ArrayList<Panier> getAllPanier(){
        String url = Statics.BASE_URL+"/panierjson/";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try{
                    panier = parsePanier(new String(req.getResponseData()));
                }catch(IOException ex){
                    Logger.getLogger(ServicePanier.class.getName()).log(Level.SEVERE,null,ex);
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return panier;
    }

    public boolean deletePanier(int id){
        String url = Statics.BASE_URL+"/panierjson/delete/"+id;
        req.setUrl(url);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog d = prog.showInfiniteBlocking();
        req.setDisposeOnCompletion(d);
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

}
