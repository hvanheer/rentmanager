package com.epf.rentmanager.dto;

import com.epf.rentmanager.model.Client;

import java.time.LocalDate;

public record ReservationClientDto(int id, Client client, LocalDate debut, LocalDate fin) {}