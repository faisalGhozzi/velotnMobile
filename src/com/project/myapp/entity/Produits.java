package com.project.myapp.entity;

public class Produits {
	
	protected int id;
	protected String nomprod;
	protected String description;
	protected float prix;
	protected int quantite;
	protected String imgUrl;
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return "Produits [id=" + id + ", nomprod=" + nomprod + ", description=" + description + ", prix=" + prix
				+ ", quantite=" + quantite + ", imgUrl=" + imgUrl + "]";
	}
	
	

	public Produits(int id, String nomprod, String description, float prix, int quantite, String imgUrl) {
		super();
		this.id = id;
		this.nomprod = nomprod;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.imgUrl = imgUrl;
	}
	
	public Produits(String nomprod, String description, float prix, int quantite, String imgUrl) {
		super();
		this.nomprod = nomprod;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.imgUrl = imgUrl;
	}

	public Produits() {
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomprod() {
		return nomprod;
	}
	public void setNomprod(String nomprod) {
		this.nomprod = nomprod;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrix() {
		return prix;
	}
	public void setPrix(float prix) {
		this.prix = prix;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	

}
