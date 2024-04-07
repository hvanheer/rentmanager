package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;

	@Autowired
	private ReservationDao reservationDao;

	
	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	/*public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}*/
	
	
	public int create(Client client) throws ServiceException {
		// TODO: créer un client
        try {
			return clientDao.create(client);
        } catch (DaoException e) {
			e.printStackTrace();
            throw new ServiceException();
        }
	}

	public int delete(Client client) throws ServiceException {
		// TODO: delete un client
		try {
			List<Reservation> clientReservations = reservationDao.findResaByClientId(client.getClient_id());
			if (!clientReservations.isEmpty()) {
				for (Reservation reservation : clientReservations) {
					if (reservation.getDebut().isBefore(LocalDate.now()) && reservation.getFin().isAfter(LocalDate.now())) {
						throw new ServiceException();
					}
				}
				int deleted_reservations = 0;
				for (Reservation reservation : clientReservations) {
					deleted_reservations += reservationDao.delete(reservation.getReservation_id());
				}
				if (deleted_reservations != clientReservations.size()) {
					throw new ServiceException();
				}
			}
		} catch (DaoException e) {
			throw new ServiceException();
		}
		try {
			return clientDao.delete(client);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}


	public Client findById(int id) throws ServiceException {
		// TODO: récupérer un client par son id
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<Client> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			//e.printStackTrace();
			throw new ServiceException();
		}
	}
	
}
