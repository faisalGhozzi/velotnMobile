package com.project.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.project.myapp.entity.Don;
import com.project.myapp.services.ServiceDon;
import com.codename1.ui.table.TableLayout;

import java.util.ArrayList;

public class ListDonForm extends SideMenu{

    public ListDonForm(Resources res) {
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setUIID("ListDonToolbar");
        tb.addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));
        setTitle("Donations");
        Container tab1 = TableLayout.encloseIn(2, new Label("Donated Sum"), new Label("Donation Date"));
        ArrayList<Don> list = ServiceDon.getInstance().getAllDons();
        for(int i = 0; i < list.size(); i++){
            tab1.add(new Label(String.valueOf(list.get(i).getSomme())))
                    .add(new Label(list.get(i).getDate().toString()));
        }
        if(list.size() != 0) {
            tab1.setScrollableX(true);
            tab1.setScrollableY(true);
            add(tab1);
        }else{
            add(BoxLayout.encloseXCenter(new Label("Be the first in supporting us")));
        }
    }


    @Override
    protected void showOtherForm(Resources res) {
        new HomeForm(res).show();
    }
}
