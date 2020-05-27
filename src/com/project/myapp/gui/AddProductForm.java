package com.project.myapp.gui;

import java.io.IOException;

import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.project.myapp.services.ProduitsService;

public class AddProductForm extends Form {
	
	@SuppressWarnings("unused")
	private Resources theme;
	
	public AddProductForm(Form previous,Resources theme) {
		super("Add Product",BoxLayout.y());
		this.theme=theme;
		
		SpanLabel label_type = new SpanLabel("Type Produit: ");
		
		
		
		SpanLabel label_nom = new SpanLabel("Nom produit: ");
		SpanLabel label_desc = new SpanLabel("Description produit: ");
		SpanLabel label_p = new SpanLabel("Prix produit: ");
		SpanLabel label_q = new SpanLabel("Quantite produit: ");
		SpanLabel label_m = new SpanLabel("Marque produit: ");
		SpanLabel label_t = new SpanLabel("Type produit: ");
		
		TextField tf_nom = new TextField();
		TextField tf_desc = new TextField();
		TextField tf_p = new TextField();
		TextField tf_q = new TextField();
		TextField tf_m = new TextField();
		TextField tf_t = new TextField();
		
		Button addP = new Button("Add Product");
		
		
		Picker types = new Picker();
		types.setType(Display.PICKER_TYPE_STRINGS);
		types.setStrings("Velo","Velo a Louer","Pieces de Rechange","Accessoire");
		types.setSelectedString("Velo");
		
		
		addP.addActionListener((evt)->{
			if(new ProduitsService().addProduct(types.getSelectedString(), tf_nom.getText(), tf_desc.getText(), Float.parseFloat(tf_p.getText()), Integer.parseInt(tf_q.getText()), tf_m.getText(), tf_t.getText()))
			{
				ToastBar.showInfoMessage("Product Added");
				try {
					new ProductsListForm(this,theme).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else
			{
				ToastBar.showInfoMessage("Error Adding Product");
			}
			
		});
		
		
		add(encloseIn(BoxLayout.x(), label_type,types));
		add(encloseIn(BoxLayout.x(), label_nom,tf_nom));
		add(encloseIn(BoxLayout.x(), label_desc,tf_desc));
		add(encloseIn(BoxLayout.x(), label_p,tf_p));
		add(encloseIn(BoxLayout.x(), label_q,tf_q));
		add(encloseIn(BoxLayout.x(), label_m,tf_m));
		add(encloseIn(BoxLayout.x(), label_t,tf_t));
		add(addP);
		
		
		this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            previous.showBack();
        });
		
		
	}

}
