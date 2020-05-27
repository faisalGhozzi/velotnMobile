package com.project.myapp.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import java.util.List;

import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.project.myapp.entity.Produits;
import com.project.myapp.entity.Review;
import com.project.myapp.utils.DataSource;
import com.project.myapp.utils.Statics;

public class ProduitsService {
	private ConnectionRequest request;
	private boolean responseResult;
	
	
	public ArrayList<Produits> products;
	public ArrayList<Review> reviews;
	
	public Produits p = new Produits();
	
	public ProduitsService() {
		request = DataSource.getInstance().getRequest();
	}
	
	
	public ArrayList<Review> getProductReviews(int id)
	{
		String url = Statics.BASE_URL+"/Reviews/"+id;
		request.setUrl(url);
		InfiniteProgress prog = new InfiniteProgress();
		Dialog d = prog.showInfiniteBlocking();
		request.setDisposeOnCompletion(d);
		request.addResponseListener(new ActionListener<NetworkEvent>() {
			
			@Override
			public void actionPerformed(NetworkEvent evt) {
				try {
					reviews = parseReviews(new String(request.getResponseData()),id);
				} catch (IOException e) {
					e.printStackTrace();
				}
				request.removeResponseListener(this);
				
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(request);
		return reviews;
	}
	
	
	@SuppressWarnings("unchecked")
	public ArrayList<Review> parseReviews(String jsonText,int idp) throws IOException
	{
		reviews = new ArrayList<Review>();
		JSONParser jp = new JSONParser();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		Map<String, Object> ReviewsListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
		List<Map<String, Object>> list = (List<Map<String, Object>>) ReviewsListJson.get("root");
		for (Map<String, Object> obj : list)
		{
			int id = (int)Float.parseFloat(obj.get("id").toString());
			int rate = (int)Float.parseFloat(obj.get("rate").toString());
			String comment = obj.get("comment").toString();
			String date = obj.get("date").toString();
			reviews.add(new Review(id, rate, idp,comment, LocalDate.parse(date,formatter)));
		}
		
		return reviews;
		
	}
	
	public ArrayList<Produits> getAllProducts() {
		
		String url = Statics.BASE_URL+"/Products/";
		request.removeAllArguments();
		request.setPost(false);
		request.setUrl(url);
		
		InfiniteProgress prog = new InfiniteProgress();
		Dialog d = prog.showInfiniteBlocking();
		request.setDisposeOnCompletion(d);
		request.addResponseListener(new ActionListener<NetworkEvent>() {
			
			@Override
			public void actionPerformed(NetworkEvent evt) {
				try {
					products = parseProducts(new String(request.getResponseData()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				request.removeResponseListener(this);
				
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(request);
		return products;
		
	}
	
		public Produits findProduct(int id) {
		
		String url = Statics.BASE_URL+"/Products/"+id;
		request.setUrl(url);
		request.setPost(false);
		InfiniteProgress prog = new InfiniteProgress();
		Dialog d = prog.showInfiniteBlocking();
		request.setDisposeOnCompletion(d);

		request.addResponseListener(new ActionListener<NetworkEvent>() {
			
			@Override
			public void actionPerformed(NetworkEvent evt) {
				try {
					
					p = parseProduct(new String(request.getResponseData()));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				request.removeResponseListener(this);
				
			}
		});
		NetworkManager.getInstance().addToQueueAndWait(request);
		return p;
		
	}

public boolean deleteProduct(int id) {
	String url = Statics.BASE_URL+"/DeleteProduct/"+id;
	request.setUrl(url);
	InfiniteProgress prog = new InfiniteProgress();
	Dialog d = prog.showInfiniteBlocking();
	request.setDisposeOnCompletion(d);
	request.addResponseListener(new ActionListener<NetworkEvent>() {
		
		@Override
		public void actionPerformed(NetworkEvent evt) {
			responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
            request.removeResponseListener(this);
			
		}
	});
	NetworkManager.getInstance().addToQueueAndWait(request);

    return responseResult;
}


public boolean sendReview(int idp,int rate,String comment)
{
	String url = Statics.BASE_URL+"/addReview/";
	request.setPost(true);
	request.addArgument("idp", String.valueOf(idp));
	request.addArgument("rate", String.valueOf(rate));
	request.addArgument("comment", comment);
	
	request.setUrl(url);
	InfiniteProgress prog = new InfiniteProgress();
	Dialog d = prog.showInfiniteBlocking();
	request.setDisposeOnCompletion(d);
	
	request.addResponseListener(new ActionListener<NetworkEvent>() {
		
		@Override
		public void actionPerformed(NetworkEvent evt) {
			responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
            request.removeResponseListener(this);
			
		}
	});
	NetworkManager.getInstance().addToQueueAndWait(request);
    return responseResult;
	
}

public boolean addProduct(String typeP,String nom,String Description,Float prix,int qte,String marque,String type)
{
	String url = Statics.BASE_URL+"/addProduct/";
	request.setPost(true);
	request.addArgument("typeProduit", typeP);
	request.addArgument("nom", nom);
	request.addArgument("description", Description);
	request.addArgument("prix", String.valueOf(prix));
	request.addArgument("qte", String.valueOf(qte));
	request.addArgument("marque", marque);
	request.addArgument("type", type);
	request.setUrl(url);
	
	InfiniteProgress prog = new InfiniteProgress();
	Dialog d = prog.showInfiniteBlocking();
	request.setDisposeOnCompletion(d);
	
	request.addResponseListener(new ActionListener<NetworkEvent>() {
		
		@Override
		public void actionPerformed(NetworkEvent evt) {

			responseResult = request.getResponseCode() == 200; // Code HTTP 200 OK
            request.removeResponseListener(this);
            
		}
	});
	NetworkManager.getInstance().addToQueueAndWait(request);
    return responseResult;
	
}

public Produits parseProduct(String jsonText) throws IOException {
	Produits p = new Produits();
	JSONParser jp = new JSONParser();
	System.out.println(jsonText);
	Map<String, Object> ProductJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
	
	
		int id = (int)Float.parseFloat(ProductJson.get("id").toString());
		String nomprod = ProductJson.get("nomprod").toString();
		String description = ProductJson.get("description").toString();
		float prix = Float.parseFloat(ProductJson.get("prix").toString());
		int quantite = (int)Float.parseFloat(ProductJson.get("quantite").toString());
		String imgUrl = "http://localhost/velotnweb/web"+ProductJson.get("imgUrl").toString();
		p=new Produits(id, nomprod, description, prix, quantite, imgUrl);
	
	return p;
}


	
	@SuppressWarnings("unchecked")
	public ArrayList<Produits> parseProducts(String jsonText) throws IOException
	{
		products = new ArrayList<>();
		JSONParser jp = new JSONParser();
		Map<String, Object> ProductsListJson = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
		List<Map<String, Object>> list = (List<Map<String, Object>>) ProductsListJson.get("root");
		for (Map<String, Object> obj : list)
		{
			int id = (int)Float.parseFloat(obj.get("id").toString());
			String nomprod = obj.get("nomprod").toString();
			String description = obj.get("description").toString();
			float prix = Float.parseFloat(obj.get("prix").toString());
			int quantite = (int)Float.parseFloat(obj.get("quantite").toString());
			String imgUrl = "http://localhost/velotnweb/web"+obj.get("imgUrl").toString();
			products.add(new Produits(id, nomprod, description, prix, quantite, imgUrl));
		}
		
		return products;		
	
	}
	

}
