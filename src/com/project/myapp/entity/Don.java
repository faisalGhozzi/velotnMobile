package com.project.myapp.entity;

import java.time.LocalDate;

public class Don {
    private int id;
    private double somme;
    private LocalDate date;

    public Don(){}

    public Don(int id, double somme) {
        this.id = id;
        this.somme = somme;
    }

    public Don(double somme) {
        this.date = LocalDate.now().plusDays(1);
        this.somme = somme;
    }

    public Don(int id, double somme, LocalDate date) {
        this.id = id;
        this.somme = somme;
        this.date = date;
    }

    public Don(double somme, LocalDate date) {
        this.somme = somme;
        this.date = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSomme() {
        return somme;
    }

    public void setSomme(double somme) {
        this.somme = somme;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "com.velotn.Entite.Don{" +
                "id=" + id +
                ", somme=" + somme +
                ", date='" + date + '\'' +
                '}';
    }
}
