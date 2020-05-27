package com.project.myapp.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.project.myapp.entity.Commande;
import com.project.myapp.utils.Statics;

import java.io.CharArrayReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceCommande {
    public ArrayList<Commande> commande;

    public static ServiceCommande instance = null;

    public boolean resultOk;
    private ConnectionRequest req;

    private ServiceCommande() {req = new ConnectionRequest();}

    public static ServiceCommande getInstance(){
        if(instance == null){
            instance = new ServiceCommande();
        }
        return instance;
    }

    public boolean addCommande(Commande c){
        String url = Statics.BASE_URL+"/commandejson/new";
        InfiniteProgress prog = new InfiniteProgress();
        Dialog d = prog.showInfiniteBlocking();
        req.setDisposeOnCompletion(d);
        req.setUrl(url);
        req.addArgument("prix",String.valueOf(c.getPrix()));
        req.addArgument("date",c.getDate().toString());
        req.addArgument("user",String.valueOf(2)); //static user
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

    public ArrayList<Commande> parseCommande(String jsonText) throws IOException{
        commande = new ArrayList<>();
        JSONParser j = new JSONParser();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        Map<String,Object> commandeListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)commandeListJson.get("root");
        for(Map<String,Object> obj : list){
            Commande c = new Commande();
            int id = (int)Float.parseFloat(obj.get("id").toString());
            c.setId(id);
            int user_id = (int)Float.parseFloat(obj.get("user_id").toString());
            c.setUserid(user_id);
            c.setPrix(Double.parseDouble(obj.get("prix").toString()));
            c.setDate(LocalDate.parse(obj.get("date").toString(),formatter));
            commande.add(c);
        }
        return commande;
    }

    public ArrayList<Commande> getAllCommande(){
        String url = Statics.BASE_URL+"/commandejson/";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try{
                    commande = parseCommande(new String(req.getResponseData()));
                }catch(IOException ex){
                    Logger.getLogger(ServiceWishlist.class.getName()).log(Level.SEVERE,null,ex);
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return commande;
    }

}
