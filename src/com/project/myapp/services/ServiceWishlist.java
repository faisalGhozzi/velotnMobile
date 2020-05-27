package com.project.myapp.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.project.myapp.entity.Wishlist;
import com.project.myapp.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceWishlist {
    public ArrayList<Wishlist> wishlist;

    public static ServiceWishlist instance=null;
    public boolean resultOk;
    private ConnectionRequest req;

    private ServiceWishlist(){
        req = new ConnectionRequest();
    }

    public static ServiceWishlist getInstance(){
        if(instance == null){
            instance = new ServiceWishlist();
        }
        return instance;
    }

    public boolean addWishlist(Wishlist w){
        String url = Statics.BASE_URL+"/wishlistjson/new";
        req.setUrl(url);
        req.addArgument("product",String.valueOf(w.getProduct_id()));
        req.addArgument("user",String.valueOf(2)); // user static
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

    public ArrayList<Wishlist> parseWishlist(String jsonText) throws IOException{
        wishlist = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String,Object> wishlistListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        List<Map<String,Object>> list = (List<Map<String,Object>>)wishlistListJson.get("root");
        for(Map<String,Object> obj : list){
            Wishlist w = new Wishlist();
            int id = (int)Float.parseFloat(obj.get("id").toString());
            w.setId(id);
            int product_id = (int)Float.parseFloat(obj.get("product_id").toString());
            w.setProduct_id(product_id);
            int user_id = (int)Float.parseFloat(obj.get("user_id").toString());
            w.setUser_id(user_id);
            wishlist.add(w);
        }
        return wishlist;
    }

    public ArrayList<Wishlist> getAllWishlist(){
        String url = Statics.BASE_URL+"/wishlistjson/";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try{
                    wishlist = parseWishlist(new String(req.getResponseData()));
                }catch(IOException ex){
                    Logger.getLogger(ServiceWishlist.class.getName()).log(Level.SEVERE,null,ex);
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return wishlist;
    }

    public boolean deleteWishlist(int id){
        String url = Statics.BASE_URL+"/wishlistjson/delete/"+id;
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
