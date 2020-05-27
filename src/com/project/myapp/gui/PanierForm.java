package com.project.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.project.myapp.entity.Commande;
import com.project.myapp.entity.Panier;
import com.project.myapp.entity.Produits;
import com.project.myapp.services.ProduitsService;
import com.project.myapp.services.ServiceCommande;
import com.project.myapp.services.ServicePanier;
import com.project.myapp.services.ServiceWishlist;

import javax.swing.border.Border;
import java.util.ArrayList;

public class PanierForm extends SideMenu{

    public PanierForm(Resources res){
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        double total = 0;
        Label remove;
        Button order = new Button("Order");
        tb.setUIID("ListDonToolbar");
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));
        setTitle("Cart");
        ArrayList<Panier> list = ServicePanier.getInstance().getAllPanier();
        Container tab1 = TableLayout.encloseIn(6,new Label("Image"),new Label("Product"),new Label("Unitary Price"),new Label("Quantity"), new Label("Total Price"),new Label(""));
        EncodedImage enc = EncodedImage.createFromImage(Image.createImage(200,200), true);



        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getUser_id() == 2) {
                remove = new Label("Discard");

                int finalI = i;
                remove.addPointerReleasedListener(e -> {
                    ServicePanier.getInstance().deletePanier(list.get(finalI).getId());
                    /*this.repaint();
                    this.refreshTheme();*/
                    new PanierForm(res).show();
                });
                Produits p = new ProduitsService().findProduct(list.get(i).getProduit_id());
                total += list.get(i).getPrix_total();
                tab1.add(new ImageViewer(URLImage.createToStorage(enc,p.getImgUrl().substring(p.getImgUrl().lastIndexOf("/")+1, p.getImgUrl().length()),p.getImgUrl())))
                        .add(new Label(p.getNomprod()))
                        .add(new Label(String.valueOf(p.getPrix())))
                        .add(new Label(String.valueOf(list.get(i).getQte())))
                        .add(new Label(String.valueOf(list.get(i).getPrix_total())))
                        .add(remove);
            }
        }
        if(list.size() != 0){
            double finalTotal = total;
            order.addActionListener(l -> ServiceCommande.getInstance().addCommande(new Commande(finalTotal,2)));

            tab1.add(new Label("Total Price : "+ total));
            tab1.add(order);
            tab1.setScrollableX(true);

            add(tab1);
        }else{

            add(BoxLayout.encloseXCenter(new Label("Your cart is empty")));

        }




    }

    @Override
    protected void showOtherForm(Resources res) {
        new HomeForm(res).show();
    }
}
