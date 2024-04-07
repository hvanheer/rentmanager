package com.epf.rentmanager.model;
import java.time.LocalDate;
import java.util.Date;

public class Vehicule {

    public Vehicule(int vehicule_id) {
        this.vehicule_id = vehicule_id;
    }

    public int getVehicule_id() {
        return vehicule_id;
    }


    public void setVehicule_id(int vehicule_id) {
        this.vehicule_id = vehicule_id;
    }

    private int vehicule_id;

    private String constructeur;

    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }
    private String modele;

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    private int nb_places;

    @Override
    public String toString() {
        return "Vehicule{" +
                "vehicule_id=" + vehicule_id +
                ", constructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                ", nb_places=" + nb_places +
                '}';
    }

    public Vehicule(String constructeur, int nb_places, int vehicule_id, String modele) {
        this.constructeur = constructeur;
        this.modele = modele;
        this.nb_places = nb_places;
        this.vehicule_id = vehicule_id;
    }

    public Vehicule(){

    }
}
