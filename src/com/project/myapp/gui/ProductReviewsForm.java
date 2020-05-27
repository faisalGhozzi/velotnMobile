package com.project.myapp.gui;

import java.util.ArrayList;

import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.project.myapp.entity.Review;
import com.project.myapp.services.ProduitsService;

public class ProductReviewsForm extends Form{
	
	
	@SuppressWarnings("unused")
	private Resources theme;
	private ArrayList<Review> reviews;
	
	
	public ProductReviewsForm(Form previous,Resources theme,int id) {
		
		super("Product Details",BoxLayout.y());
		this.theme=theme;
		
		reviews= new ProduitsService().getProductReviews(id);
		
		for (Review r : reviews) {
			SpanLabel rate = new SpanLabel("Rate: "+r.getRate());
			SpanLabel comment = new SpanLabel("Comment: "+r.getComment());
			SpanLabel date = new SpanLabel("Date: "+r.getDate().toString());
			SpanLabel space = new SpanLabel("=========================");
			addAll(rate,comment,date,space);
			
		}
		
		TextField Rev = new TextField();
		
		Picker rates = new Picker();
		rates.setType(Display.PICKER_TYPE_STRINGS);
		rates.setStrings("1","2","3","4","5");
		rates.setSelectedString("1");
		
		
		Button addRev = new Button("Add review");
		
		addRev.addActionListener((evt)->{
			if(Rev.getText().equals(""))
			{
				ToastBar.showInfoMessage("Review Empty");
			}else
			{
				if(new ProduitsService().sendReview(id, Integer.parseInt(rates.getSelectedString()), Rev.getText()))
				{
					new ProductReviewsForm(this, theme, id).show();
				}
			}
		});
		
		addAll(encloseIn(BoxLayout.x(), Rev,rates),addRev);
		
		
		this.getToolbar().addCommandToLeftBar("Return", null, (evt) -> {
            previous.showBack();
        });
	}

}
