package com.project.myapp.gui;

import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.project.myapp.entity.Don;
import com.project.myapp.services.ServiceDon;

import java.time.LocalDate;

public class AddDonForm extends Form {

    public AddDonForm(Form previous){
        setTitle("Add a new task");
        setLayout(BoxLayout.y());

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
                        if( ServiceDon.getInstance().addDon(d))
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

                }
            }
        });

        addAll(tfSomme,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }

}
