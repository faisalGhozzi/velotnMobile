package com.project.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.project.myapp.services.ServiceDon;

import java.util.ArrayList;

public class ListDonForm extends Form{

    public ListDonForm(Form previous) {
        setTitle("List dons");

        SpanLabel sp = new SpanLabel();
        ArrayList list = ServiceDon.getInstance().getAllDons();

        for(int i = 0; i < list.size(); i++){
            SpanLabel sp1 = new SpanLabel();
            SpanLabel sp2 = new SpanLabel();
            String[] arr = list.get(i).toString().split(",");
            sp1.setText(arr[1]);
            sp2.setText(arr[2].substring(0,arr[2].length()-1));
            addAll(sp1,sp2);
        }
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }


}
