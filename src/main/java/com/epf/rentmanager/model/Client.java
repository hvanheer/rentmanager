package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Client {
    private String nom;
    private String prenom;
    private LocalDate naissance;
    private int client_id;
    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;


    @Override
    public String toString() {
        return "Client{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", naissance=" + naissance +
                ", client_id=" + client_id +
                ", email='" + email + '\'' +
                '}';
    }

    // Constructeur paramétré
    public Client(String nom, String prenom, LocalDate naissance, String email, int client_id) {
        this.nom = nom;
        this.prenom = prenom;
        this.naissance = naissance;
        this.email = email;
        this.client_id = client_id;
    }

    public Client(){

    }
}
