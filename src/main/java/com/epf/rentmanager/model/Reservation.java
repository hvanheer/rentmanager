package com.epf.rentmanager.model;

import java.time.LocalDate;


public class Reservation {

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getVehicule_id() {
        return vehicule_id;
    }

    public void setVehicule_id(int vehicule_id) {
        this.vehicule_id = vehicule_id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }


    private int client_id;
    private int vehicule_id;
    private LocalDate debut;
    private LocalDate fin;

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    private int reservation_id;

    public Reservation(int client_id, int vehicule_id, LocalDate debut, LocalDate fin, int reservation_id) {
        this.client_id = client_id;
        this.vehicule_id = vehicule_id;
        this.debut = debut;
        this.fin = fin;
        this.reservation_id = reservation_id;
    }

    public Reservation(){

    }

    @Override
    public String toString() {
        return "Reservation{" +
                "client_id=" + client_id +
                ", vehicule_id=" + vehicule_id +
                ", debut=" + debut +
                ", fin=" + fin +
                ", reservation_id=" + reservation_id +
                '}';
    }
}
