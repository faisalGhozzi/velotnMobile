package com.project.myapp.entity;

import java.time.LocalDate;

public class Review {

	
	
	private int id;
	private int rate;
	private int produit;
	private String Comment;
	private LocalDate date;
	
	
	
	public Review() {
		
	}

	
	public Review(int rate, int produit, LocalDate date) {
		super();
		this.rate = rate;
		this.produit = produit;
		this.date = date;
	}
	
	


	public Review(int id, int rate, int produit, String comment, LocalDate date) {
		super();
		this.id = id;
		this.rate = rate;
		this.produit = produit;
		Comment = comment;
		this.date = date;
	}

	

	public Review(int rate, int produit, String comment, LocalDate date) {
		super();
		this.rate = rate;
		this.produit = produit;
		Comment = comment;
		this.date = date;
	}


	public Review(int id, int rate, int produit, LocalDate date) {
		super();
		this.id = id;
		this.rate = rate;
		this.produit = produit;
		this.date = date;
	}



	public String getComment() {
		return Comment;
	}


	public void setComment(String comment) {
		Comment = comment;
	}


	@Override
	public String toString() {
		return "Review [id=" + id + ", rate=" + rate + ", produit=" + produit + ", date=" + date + "]";
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public int getProduit() {
		return produit;
	}
	public void setProduit(int produit) {
		this.produit = produit;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
}
