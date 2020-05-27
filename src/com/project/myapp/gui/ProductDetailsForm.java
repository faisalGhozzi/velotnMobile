package com.project.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.table.TableLayout;
import com.codename1.ui.util.Resources;
import com.project.myapp.entity.Panier;
import com.project.myapp.entity.Produits;
import com.project.myapp.entity.Wishlist;
import com.project.myapp.services.ProduitsService;
import com.project.myapp.services.ServicePanier;
import com.project.myapp.services.ServiceWishlist;

public class ProductDetailsForm extends SideMenu {
	
	@SuppressWarnings("unused")
	private Produits p = new Produits();
	
	public ProductDetailsForm(Form previous,Resources theme,int id) {
		
		super("Product Details",BoxLayout.y());
		Toolbar tb = getToolbar();
		tb.setUIID("ListDonToolbar");
		p= new ProduitsService().findProduct(id);
		Button reviews = new Button("Reviews");
		Button wishlist = new Button("Add To Wislist");
		Button cart = new Button("Add to Cart");

		EncodedImage enc = EncodedImage.createFromImage(Image.createImage(800,800), true);
		String url = p.getImgUrl();
		ImageViewer img = new ImageViewer(URLImage.createToStorage(enc, url.substring(url.lastIndexOf("/")+1, url.length()), url));
		img.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FIT);
		Container tab1 = TableLayout.encloseIn(2, new Label("Name"), new Label(p.getNomprod()),
				new Label("Description"), new Label(p.getDescription()),
				new Label("Price"), new Label(Float.toString(p.getPrix())));
		Style s = img.getAllStyles();
		s.setPaddingUnit(Style.UNIT_TYPE_DIPS);
		s.setPadding(6, 6, 6, 6);
		
		reviews.addActionListener((evt)->{
			new ProductReviewsForm(this, theme, id).show();
			
		});

		wishlist.addActionListener(e -> ServiceWishlist.getInstance().addWishlist(new Wishlist(id,2)));

		cart.addActionListener(e -> ServicePanier.getInstance().addPanier(new Panier(id,1,p.getPrix(),2)));
		
		this.addAll(img,tab1,reviews,wishlist,cart);
		
		
		
		this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, (evt) -> {
            previous.showBack();
        });
		
	}

	@Override
	protected void showOtherForm(Resources res) {

	}
}
