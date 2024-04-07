package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicule;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;

	@Autowired
	private ReservationDao reservationDao;

	//private ReservationService(ReservationDao reservationDao) {this.reservationDao = reservationDao;}
	
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}
	
	/*public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}*/
	
	
	public int create(Vehicule vehicle) throws ServiceException {
		// TODO: créer un véhicule
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public int delete(Vehicule vehicle) throws ServiceException {
		// TODO: delete un véhicule
		try {
			List<Reservation> vehicleReservations = reservationDao.findResaByVehicleId(vehicle.getVehicule_id());
			if (!vehicleReservations.isEmpty()) {
				for (Reservation reservation : vehicleReservations) {
					if (reservation.getDebut().isBefore(LocalDate.now()) && reservation.getFin().isAfter(LocalDate.now())) {
						throw new ServiceException();
					}
				}
				int deleted_reservations = 0;
				for (Reservation reservation : vehicleReservations) {
					deleted_reservations += reservationDao.delete(reservation.getReservation_id());
				}
				if (deleted_reservations != vehicleReservations.size()) {
					throw new ServiceException();
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		try {
			return vehicleDao.delete(vehicle);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}


	public Vehicule findById(int id) throws ServiceException {
		// TODO: récupérer un véhicule par son id
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}

	public List<Vehicule> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException();
		}
	}
	
}
