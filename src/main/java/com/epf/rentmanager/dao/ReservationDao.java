package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.model.Reservation;


public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}

	private VehicleDao vehicleDao;
	private ClientDao clientDao;
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public int create(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS)){
		ps.setInt(1, reservation.getClient_id());
		ps.setInt(2, reservation.getVehicule_id());
		ps.setDate(3, Date.valueOf(reservation.getDebut()));
		ps.setDate(4, Date.valueOf(reservation.getFin()));
		ps.executeUpdate();
		ResultSet resultSet = ps.getGeneratedKeys();
		if (!resultSet.next()) {
			throw new DaoException();
		} else {
			return resultSet.getInt(1);
		}
	} catch (SQLException e) {
		throw new DaoException(); }
}

	public int delete(int reservationId) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(DELETE_RESERVATION_QUERY)){

			ps.setInt(1, reservationId);
			int nbDeletedRows = ps.executeUpdate();
			if (nbDeletedRows == 0){
				System.out.println("reservation = 0");
				throw new DaoException();
			} else {
				return nbDeletedRows;
			}
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	
	public List<Reservation> findResaByClientId(int clientId) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY)){
			ps.setInt(1, clientId);
			ResultSet resultSet = ps.executeQuery();

			List<Reservation> listReservation = new ArrayList<Reservation>();

			while (resultSet.next()) {
				Reservation reservation = new Reservation(clientDao.findById(clientId), vehicleDao.findById(resultSet.getInt(2)), resultSet.getDate(3).toLocalDate(), resultSet.getDate(4).toLocalDate(), resultSet.getInt(1));
				listReservation.add(reservation);
			}
			return listReservation;


		} catch (SQLException e) {
			throw new DaoException();
		}

	}
	
	public List<Reservation> findResaByVehicleId(int vehicleId) throws DaoException {

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY)){

			ps.setInt(1, vehicleId);
			ResultSet resultSet = ps.executeQuery();


			List<Reservation> listReservation = new ArrayList<Reservation>();
			while (resultSet.next()) {
				Reservation reservation = new Reservation(clientDao.findById(resultSet.getInt(2)), vehicleDao.findById(vehicleId), resultSet.getDate(3).toLocalDate(), resultSet.getDate(4).toLocalDate(), resultSet.getInt(1));
				listReservation.add(reservation);
			}
			return listReservation;


		} catch (SQLException e) {
			throw new DaoException();
		}

	}

	public List<Reservation> findAll() throws DaoException {

		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(FIND_RESERVATIONS_QUERY)) {

			ResultSet resultSet = ps.executeQuery();

			List<Reservation> listReservation = new ArrayList<Reservation>();

			while (resultSet.next()){
				Reservation reservation = new Reservation(clientDao.findById(resultSet.getInt(2)), vehicleDao.findById(resultSet.getInt(3)), resultSet.getDate(4).toLocalDate(), resultSet.getDate(5).toLocalDate(), resultSet.getInt(1));
				listReservation.add(reservation);
			}

			return listReservation;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}



	}
}
