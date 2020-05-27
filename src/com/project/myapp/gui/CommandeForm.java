package com.project.myapp.gui;

import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.project.myapp.entity.Commande;
import com.project.myapp.entity.Don;
import com.project.myapp.services.ServiceCommande;
import com.project.myapp.services.ServiceDon;

import java.util.ArrayList;

public class CommandeForm extends SideMenu{

    public CommandeForm(Resources res){
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setUIID("ListDonToolbar");
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));
        setTitle("My orders");
        Container tab1 = TableLayout.encloseIn(3, new Label("Order cost"), new Label("Date"), new Label("Details"));
        ArrayList<Commande> list = ServiceCommande.getInstance().getAllCommande();
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getUserid() == 2){
                tab1.add(new Label(String.valueOf(list.get(i).getPrix())))
                        .add(new Label(list.get(i).getDate().toString()))
                .add(new Label("Show"));
            }

        }
        if(list.size() != 0) {
            tab1.setScrollableX(true);
            add(tab1);
        }else{
            add(BoxLayout.encloseXCenter(new Label("You have no orders")));
        }
    }

    @Override
    protected void showOtherForm(Resources res) {
        new HomeForm(res).show();
    }
}
