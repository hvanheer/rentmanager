package com.epf.rentmanager.dto;

import com.epf.rentmanager.model.Vehicule;

import java.time.LocalDate;
public record ReservationVehicleDto(int id, Vehicule vehicle, LocalDate debut, LocalDate fin) {}