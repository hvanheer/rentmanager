package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class main {
    public static void main(String[] args) throws DaoException, ServiceException {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ClientService clientService = context.getBean(ClientService.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);
        ReservationService reservationService = context.getBean(ReservationService.class);

        // Create a Scanner object to read input from the console
        Scanner scanner = new Scanner(System.in);

        // Read the user input
        String userInput = "0";


        while (!userInput.equals("12")) {
            System.out.print("Please enter a value: 1 - Create a Client; 2 - Find all Clients; 3 - Create a Vehicle; 4 - Find all Vehicles; 5 - Delete a Client; 6 - Delete a Vehicle; 7 - Create a Reservation; 8 - Find all Reservations; 9 - Delete a Reservation; 10 - Find Resa by Client ID; 11 - Find Resa by Vehicle ID; 12 - Quit");
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

                    String modele = IOUtils.readString("Vehicle modele: ", true);

                    int newVehicleID = 0;

                    Vehicule newVehicle = new Vehicule(constructeur, nbPlacesInt, newVehicleID, modele);

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

                    Client clientToDelete = clientService.findById(clientIDint);

                    clientService.delete(clientToDelete);
                    System.out.println("Client "+clientIDint+" has been deleted.");
                    break;

                case "6":
                    int vehicleIDint = IOUtils.readInt("Vehicle ID: ");

                    Vehicule vehicleToDelete = vehicleService.findById(vehicleIDint);

                    int nbDeletedVehicles = vehicleService.delete(vehicleToDelete);
                    System.out.println(nbDeletedVehicles + " vehicle(s) deleted.");
                    break;

                case "7":

                    int resaClientIdInt = IOUtils.readInt("Client ID: ");
                    Client newClient = clientService.findById(resaClientIdInt);

                    int resaVehicleIdInt = IOUtils.readInt("Vehicle ID: ");
                    Vehicule newVehicle1 = vehicleService.findById(resaVehicleIdInt);

                    LocalDate reservationStartDateLD = IOUtils.readDate("Reservation Start Date: ", true);

                    LocalDate reservationEndDateLD = IOUtils.readDate("Reservation End Date: ", true);

                    Reservation newReservation = new Reservation(newClient, newVehicle1, reservationStartDateLD, reservationEndDateLD, 0);

                    int newReservationID = reservationService.create(newReservation);

                    System.out.println("Id de la nouvelle reservation: " + newReservationID);

                    break;

                case "8":
                    try{
                        System.out.println(reservationService.findAll());
                    } catch (ServiceException e) {
                        System.out.println("service exception "+e.getMessage());
                    }
                    break;


                case "9":
                    int reservationIDint = IOUtils.readInt("Reservation ID: ");

                    int nbDeletedReservations = reservationService.delete(reservationIDint);
                    System.out.println(nbDeletedReservations + " reservation(s) deleted.");
                    break;

                case "10":
                    int clientIdForResa = IOUtils.readInt("Client ID for reservation: ");
                    try{
                        System.out.println(reservationService.findResaByClientId(clientIdForResa));
                    } catch (ServiceException e) {
                        System.out.println("service exception "+e.getMessage());
                    }
                    break;

                case "11":
                    int vehicleIdForResa = IOUtils.readInt("Vehicle ID for reservation: ");
                    try{
                        System.out.println(reservationService.findResaByClientId(vehicleIdForResa));
                    } catch (ServiceException e) {
                        System.out.println("service exception "+e.getMessage());
                    }
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
