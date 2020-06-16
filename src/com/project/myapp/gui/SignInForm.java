package com.project.myapp.gui;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

import java.io.FileWriter;
import java.io.IOException;

public class SignInForm extends Form {
    public SignInForm(Resources theme){
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        Toolbar.setGlobalToolbar(true);
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                //new Label("Welcome", "WelcomeWhite")
                new Label("Sign in", "WelcomeBlack")
        );

        getTitleArea().setUIID("Container");

        Image profilePic = theme.getImage("user-picture.png");
        Image mask = theme.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePic");
        profilePicLabel.setMask(mask.createMask());

        TextField login = new TextField("", "Login", 20, TextField.USERNAME) ;
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD) ;
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);

        Button loginButton = new Button("LOGIN");
        loginButton.setUIID("LoginButton");
        loginButton.addActionListener(e -> {
            //Toolbar.setGlobalToolbar(false);
            //new WalkthruForm(theme).show();
            if(login.getText().equals("khalil") && password.getText().equals("123")){
                try{
                    FileWriter writer = new FileWriter("C:\\Users\\user\\IdeaProjects\\velotnMobile\\src\\com\\project\\myapp\\gui\\status.txt");
                    writer.write("1");
                    writer.close();
                }catch (IOException s){
                    s.printStackTrace();
                }
                new HomeForm(theme).show();
            }else if(login.getText().equals("faicel") && password.getText().equals("123")){
                try{
                    FileWriter writer = new FileWriter("C:\\Users\\user\\IdeaProjects\\velotnMobile\\src\\com\\project\\myapp\\gui\\status.txt");
                    writer.write("2");
                    writer.close();
                }catch (IOException s){
                    s.printStackTrace();
                }
                new HomeForm(theme).show();
            }

        });

        /*Button createNewAccount = new Button("CREATE NEW ACCOUNT");
        createNewAccount.setUIID("CreateNewAccountButton");*/

        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }


        Container by = BoxLayout.encloseY(
                welcome,
                profilePicLabel,
                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                loginButton/*,
                createNewAccount*/
        );
        add(BorderLayout.CENTER, by);

        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
