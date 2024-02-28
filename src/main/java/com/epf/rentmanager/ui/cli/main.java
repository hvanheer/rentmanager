package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws DaoException, ServiceException {

        ClientService clientService =  ClientService.getInstance();

        VehicleService vehicleService = VehicleService.getInstance();

        ReservationService  reservationService = ReservationService.getInstance();


        // Create a Scanner object to read input from the console
        Scanner scanner = new Scanner(System.in);

        // Read the user input
        String userInput = "0";


        while (!userInput.equals("12")) {
            System.out.print("Please enter a value: 1 - Create a Client; 2 - Find all Clients; 3 - Create a Vehicle; 4 - Find all Vehicles; 5 - Delete a Client; 6 - Delete a Vehicle; 7 - Create a Reservation; 8 - Find all Reservations; 9 - Delete a Reservation; 12 - Quit");
            userInput = scanner.next();
            System.out.println("You entered: " + userInput);
            switch (userInput) {
                case "1":
                    try{
                        String lastName = IOUtils.readString("Client Last Name: ", true);

                        String firstName = IOUtils.readString("Client First Name: ", true);

                        LocalDate dateOfBirthLD = IOUtils.readDate("Client Date of Birth: ", true);

                        System.out.println("Client Email: ");
                        String newClientEmail = scanner.next();

                        int newClientID = 0;

                        Client newClient = new Client(lastName, firstName, dateOfBirthLD, newClientEmail, newClientID);

                        int newIdClient = clientService.create(newClient);
                        System.out.println("Id du nouveau client: " + newIdClient);


                    } catch (ServiceException e) {
                        e.printStackTrace();
            }
                    break;


                case "2":
                    try{
                        System.out.println(clientService.findAll());
                    } catch (ServiceException e) {
                        System.out.println("service exception "+e.getMessage());
                     }
                    break;
                case "3":
                    String constructeur = IOUtils.readString("Vehicle Constructor: ", true);

                    int nbPlacesInt = IOUtils.readInt("Number of seats: ");

                    int newVehicleID = 0;

                    Vehicule newVehicle = new Vehicule(constructeur, nbPlacesInt, newVehicleID);

                    int newIdVehicle = vehicleService.create(newVehicle);
                    System.out.println("Id du nouveau vehicule: " + newIdVehicle);
                    break;

                case "4":
                    try{
                        System.out.println(vehicleService.findAll());
                    } catch (ServiceException e) {
                        System.out.println("service exception "+e.getMessage());
                    }
                    break;

                case "5":

                    int clientIDint = IOUtils.readInt("Client ID: ");
                    System.out.println(clientIDint);

                    Client clientToDelete = clientService.findById(clientIDint);

                    clientService.delete(clientToDelete);
                    System.out.println("Client "+clientIDint+" has been deleted.");
                    break;

                case "6":
                    int vehicleIDint = IOUtils.readInt("Vehicle ID: ");

                    Vehicule vehicleToDelete = vehicleService.findById(vehicleIDint);

                    int nbDeletedVehicles = vehicleService.delete(vehicleToDelete);
                    System.out.println(nbDeletedVehicles + "vehicle(s) deleted.");
                    break;

                case "7":

                    System.out.println("Client ID: ");
                    String resaClientIdString = scanner.next();
                    int resaClientIdInt = IOUtils.readInt(resaClientIdString);

                    System.out.println("Vehicle ID: ");
                    String resaVehicleIdString = scanner.next();
                    int resaVehicleIdInt = IOUtils.readInt(resaVehicleIdString);


                    System.out.println("Reservation Start Date: ");
                    String reservationStartDateString = scanner.next();
                    LocalDate reservationStartDateLD = IOUtils.readDate(reservationStartDateString, true);

                    System.out.println("Reservation End Date: ");
                    String reservationEndDateString = scanner.next();
                    LocalDate reservationEndDateLD = IOUtils.readDate(reservationEndDateString, true);

                    int newReservationID = 0;

                    Reservation newReservation = new Reservation(resaClientIdInt, resaVehicleIdInt, reservationStartDateLD, reservationEndDateLD, newReservationID);

                    reservationService.create(newReservation);
                    break;

                case "8":
                    reservationService.findAll();
                    break;

                case "9":
                    System.out.println("Reservation ID: ");
                    String reservationIDString = scanner.next();
                    int reservationIDint = IOUtils.readInt(reservationIDString);

                    reservationService.delete(reservationIDint);
                    break;



                case "12":
                    // No need to close the Scanner here
                    break;

                default:
                    System.out.println("Invalid input. Please enter a valid option.");
            }
        }

        // Close the Scanner outside the loop
        scanner.close();

    }

}
