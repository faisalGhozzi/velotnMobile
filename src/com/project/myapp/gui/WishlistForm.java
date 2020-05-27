package com.project.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.project.myapp.entity.Produits;
import com.project.myapp.entity.Wishlist;
import com.project.myapp.services.ProduitsService;
import com.project.myapp.services.ServiceWishlist;

import java.util.ArrayList;

public class WishlistForm extends SideMenu{

    public WishlistForm(Resources res){
        super(BoxLayout.y());
        Label remove;
        Toolbar tb = getToolbar();
        tb.setUIID("ListDonToolbar");
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));
        setTitle("Wishlist");
        ArrayList<Wishlist> list = ServiceWishlist.getInstance().getAllWishlist();
        Container tab1 = TableLayout.encloseIn(4, new Label("Image"), new Label("Product"), new Label("Price"), new Label(""));
        EncodedImage enc = EncodedImage.createFromImage(Image.createImage(200,200), true);
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getUser_id() == 2) {
                remove = new Label("Discard");
                int finalI = i;
                remove.addPointerReleasedListener(e -> {
                    ServiceWishlist.getInstance().deleteWishlist(list.get(finalI).getId());
                    /*this.repaint();
                    this.refreshTheme();*/
                    new WishlistForm(res).show();
                });
                Produits p = new ProduitsService().findProduct(list.get(i).getProduct_id());
                tab1.add(new ImageViewer(URLImage.createToStorage(enc,p.getImgUrl().substring(p.getImgUrl().lastIndexOf("/")+1, p.getImgUrl().length()),p.getImgUrl())))
                        .add(new Label(p.getNomprod()))
                        .add(new Label(String.valueOf(p.getPrix())))
                        .add(remove);
            }
        }
        if(list.size() != 0){
            tab1.setScrollableX(true);
            add(tab1);
        }else{
            add(BoxLayout.encloseXCenter(new Label("Your wishlist is empty")));
        }

    }

    @Override
    protected void showOtherForm(Resources res) {
        new HomeForm(res).show();
    }
}
