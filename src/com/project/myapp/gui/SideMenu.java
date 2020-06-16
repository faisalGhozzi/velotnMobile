package com.project.myapp.gui;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class SideMenu extends Form {
    public SideMenu(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenu(String title) {
        super(title);
    }

    public SideMenu() {
    }

    public SideMenu(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public void setupSideMenu(Resources res) {

        String data = "";
        try {
            File myObj = new File("C:\\Users\\user\\IdeaProjects\\velotnMobile\\src\\com\\project\\myapp\\gui\\status.txt");
            Scanner read = new Scanner(myObj);
            data = read.nextLine();
            read.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Image profilePic = res.getImage("user-picture.png");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        //profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label("", profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");

        getToolbar().addComponentToSideMenu(sidemenuTop);

        if(data.equals("2")) {
            getToolbar().addCommandToSideMenu("  Donate", null, e -> new AddDonForm(res).show());
            getToolbar().addCommandToSideMenu("  Shop", null, e -> new ProductsListForm(res).show());
            getToolbar().addCommandToSideMenu("  Wishlist", null, e -> new WishlistForm(res).show());
            getToolbar().addCommandToSideMenu("  Cart", null, e -> new PanierForm(res).show());
            getToolbar().addCommandToSideMenu("  My Orders", null, e -> new CommandeForm(res).show());
            getToolbar().addCommandToSideMenu("  Logout", null, e -> new SignInForm(res).show());
        }else if(data.equals("1")){
            getToolbar().addCommandToSideMenu("  Show donations", null, e -> new ListDonForm(res).show());
            getToolbar().addCommandToSideMenu("  Logout", null, e -> new SignInForm(res).show());
        }
        //getToolbar().addCommandToSideMenu("  Logout", null,  e -> new LoginForm(res).show());
    }

    protected abstract void showOtherForm(Resources res);
}
