package com.project.myapp.gui;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;

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
        Image profilePic = res.getImage("user-picture.png");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        //profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label("", profilePic, "SideMenuTitle");
        profilePicLabel.setMask(mask.createMask());

        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");

        getToolbar().addComponentToSideMenu(sidemenuTop);
        getToolbar().addCommandToSideMenu("  Donate", null, e -> new AddDonForm(res).show());
        getToolbar().addCommandToSideMenu("  Show donations", null,  e -> new ListDonForm(res).show());
        getToolbar().addCommandToSideMenu("  Shop", null,  e -> new ProductsListForm(res).show());
        getToolbar().addCommandToSideMenu("  Wishlist", null,  e -> new WishlistForm(res).show());
        getToolbar().addCommandToSideMenu("  Cart", null,  e -> new PanierForm(res).show());
        getToolbar().addCommandToSideMenu("  My Orders", null,  e -> new CommandeForm(res).show());
        //getToolbar().addCommandToSideMenu("  Logout", null,  e -> new LoginForm(res).show());
    }

    protected abstract void showOtherForm(Resources res);
}
