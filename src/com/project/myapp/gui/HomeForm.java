package com.project.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

public class HomeForm extends Form{
    Form current;
    public HomeForm() {
        current=this;
        setTitle("Home");
        setLayout(BoxLayout.y());

        add(new Label("Choose an option"));
        Button btnAddTask = new Button("Add Dons");
        Button btnListTasks = new Button("Show Dons");

        btnAddTask.addActionListener(e-> new AddDonForm(current).show());
        btnListTasks.addActionListener(e-> new ListDonForm(current).show());
        addAll(btnAddTask,btnListTasks);


    }


}
