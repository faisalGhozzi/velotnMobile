package com.project.myapp.gui;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.project.myapp.entity.Don;
import com.project.myapp.services.ServiceDon;

import javax.swing.border.Border;
import java.time.LocalDate;

public class AddDonForm extends SideMenu {

    public AddDonForm(Resources res){
        super(new BorderLayout());
        Toolbar tb = getToolbar();
        tb.setUIID("ListDonToolbar");
        setTitle("Donate");
        setLayout(BoxLayout.y());
        tb.addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));

        TextField tfSomme = new TextField("","Donation sum");
        Button btnValider = new Button("Donate");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfSomme.getText().length()==0)
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Don d = new Don(Double.parseDouble(tfSomme.getText()));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

                }
            }
        });
        addAll(tfSomme,btnValider);
    }

    @Override
    protected void showOtherForm(Resources res) {
        new HomeForm(res).show();
    }
}
