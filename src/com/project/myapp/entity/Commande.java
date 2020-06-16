package com.project.myapp.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Commande {
    private int id;
    private LocalDate date;
    private double prix;
    private List<Panier> paniers = new ArrayList<Panier>();
    private int userid;
    private String username;

    public Commande(){

    }

    public Commande(int id, LocalDate date, double prix, String username){
        this.id = id;
        this.date = date;
        this.prix = prix;
        this.username = username;
    }

    public Commande(LocalDate date, double prix, List<Panier> paniers, int userid) {
        this.date = date;
        this.prix = prix;
        this.paniers = paniers;
        this.userid = userid;
    }

    public Commande(double prix, int userid) {
        this.date = LocalDate.now().plusDays(0);
        this.prix = prix;
        this.userid = userid;
    }

    public Commande(int id, LocalDate date, double prix, int userid) {
        this.id = id;
        this.date = date;
        this.prix = prix;
        this.userid = userid;
    }

    /*public Commande(int id) {
        this.id = id;
    }*/

    public Commande(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public List<Panier> getPaniers() {
        return paniers;
    }

    public void setPaniers(List<Panier> paniers) {
        this.paniers = paniers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "com.velotn.Entite.Commande{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", prix=" + prix +
                ", produits=" + paniers +
                '}';
    }
}
