package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ClientService clientService = ClientService.getInstance();
	private final VehicleService vehicleService = VehicleService.getInstance();
	private final ReservationService reservationService = ReservationService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Client> clientList;
		List<Vehicule> vehicleList;
		List<Reservation> reservationList;
		try {
			clientList = clientService.findAll();
			vehicleList = vehicleService.findAll();
			reservationList = reservationService.findAll();
		} catch (ServiceException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		request.setAttribute("nbClients", clientList.size());
		request.setAttribute("nbVehicles", vehicleList.size());
		request.setAttribute("nbReservations", reservationList.size());

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

}
