package com.project.myapp.gui;

import java.io.IOException;
import java.util.ArrayList;

import com.codename1.components.ImageViewer;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.project.myapp.entity.Produits;
import com.project.myapp.services.ProduitsService;

public class ProductsListForm extends SideMenu {
	
	public ArrayList<Produits> products;
	 @SuppressWarnings("unused")
	//private Resources theme;
	
	public ProductsListForm(Form previous, Resources theme) throws IOException {
		super("Products List",BoxLayout.yLast());
		Toolbar tb = getToolbar();
		tb.setUIID("ListDonToolbar");
		tb.addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(theme));
		products = new ProduitsService().getAllProducts();
		EncodedImage enc = EncodedImage.createFromImage(Image.createImage(500,500), true);

		for (Produits prod : products) {
			String url = prod.getImgUrl();
			ImageViewer img = new ImageViewer(URLImage.createToStorage(enc, url.substring(url.lastIndexOf("/")+1, url.length()), url));
			img.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FIT);
			Style s = img.getAllStyles();
			s.setPaddingUnit(Style.UNIT_TYPE_DIPS);
			s.setPadding(6, 6, 6, 6);
			 img.addPointerReleasedListener((evt)->{
		        	new ProductDetailsForm(this, theme,prod.getId()).show();
		        });		
	        this.add(BoxLayout.encloseXCenter(img));
		}
		this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            previous.showBack();
        });
	}

	public ProductsListForm(Resources theme) {
		super("Products List",BoxLayout.yLast());
		Toolbar tb = getToolbar();
		tb.setUIID("ListDonToolbar");
		tb.addMaterialCommandToLeftBar("",FontImage.MATERIAL_ARROW_BACK, e-> showOtherForm(theme));
		products = new ProduitsService().getAllProducts();
		EncodedImage enc = EncodedImage.createFromImage(Image.createImage(400,400), true);
		for (Produits prod : products) {
			String url = prod.getImgUrl();
			ImageViewer img = new ImageViewer(URLImage.createToStorage(enc, url.substring(url.lastIndexOf("/")+1, url.length()), url));
			Style s = img.getAllStyles();
			s.setPaddingUnit(Style.UNIT_TYPE_DIPS);
			s.setPadding(6, 6, 6, 6);
			 img.addPointerReleasedListener((evt)->{
		        	new ProductDetailsForm(this, theme,prod.getId()).show();
				
		        });
	        this.add(img); 	
		}

	}

	@Override
	protected void showOtherForm(Resources res) {
		new HomeForm(res).show();
	}
}
