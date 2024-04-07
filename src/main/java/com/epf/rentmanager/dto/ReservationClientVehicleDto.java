package com.epf.rentmanager.dto;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicule;

import java.time.LocalDate;

public record ReservationClientVehicleDto(int id, Vehicule vehicle, Client client, LocalDate debut, LocalDate fin) {}