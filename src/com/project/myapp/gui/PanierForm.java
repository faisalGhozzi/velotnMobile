package com.project.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.NumericSpinner;
import com.codename1.ui.spinner.Picker;
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
        final double[] total = {0};
        Label remove;
        Button order = new Button("Order");
        tb.setUIID("ListDonToolbar");
        tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(res));
        setTitle("Cart");
        ArrayList<Panier> list = ServicePanier.getInstance().getAllPanier();
        Container tab1 = TableLayout.encloseIn(6,new Label("Image"),new Label("Product"),new Label("Unitary Price"),new Label("Quantity"), new Label("Total Price"),new Label(""));
        EncodedImage enc = EncodedImage.createFromImage(Image.createImage(200,200), true);

        final int[] f = {0};
        Label Total_price = new Label();
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
                total[0] += list.get(i).getPrix_total();
                Label prixtot = new Label();
                prixtot.setUIID(String.valueOf(p.getId()));
                prixtot.setText(String.valueOf(list.get(i).getPrix_total()));
                Picker sp = new Picker();
               sp.setType(Display.PICKER_TYPE_STRINGS);
               sp.setStrings("1","2","3","4","5","6","7","8","9","10");
                sp.setUIID(String.valueOf(p.getId()));
                sp.setSelectedString(String.valueOf(list.get(i).getQte()));



                sp.addActionListener(evt -> {
                    prixtot.setText("1200");
                    f[0] = 1;
                    Total_price.setText("Total Price : "+"1300");
                    total[0] =1300;

                });



                tab1.add(new ImageViewer(URLImage.createToStorage(enc,p.getImgUrl().substring(p.getImgUrl().lastIndexOf("/")+1, p.getImgUrl().length()),p.getImgUrl())))
                        .add(new Label(p.getNomprod()))
                        .add(new Label(String.valueOf(p.getPrix())))
                        .add(sp)
                        .add(prixtot)
                        .add(remove);
            }
        }
        double finalTotal = total[0];
        if(list.size() != 0){

            if(f[0] == 1){
                order.addActionListener(l -> ServiceCommande.getInstance().addCommande(new Commande(1300,2)));

                Total_price.setText("Total Price : "+ "1300");
                tab1.add(Total_price);
                tab1.add(order);
                tab1.setScrollableX(true);

                add(tab1);
            }
            else{
                order.addActionListener(l -> ServiceCommande.getInstance().addCommande(new Commande(finalTotal,2)));

                Total_price.setText("Total Price : "+ total[0]);
                tab1.add(Total_price);
                tab1.add(order);
                tab1.setScrollableX(true);

                add(tab1);
            }

        }else{

            add(BoxLayout.encloseXCenter(new Label("Your cart is empty")));

        }




    }

    @Override
    protected void showOtherForm(Resources res) {
        new HomeForm(res).show();
    }
}
