package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.model.Vehicule;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	public VehicleDao() {}


	/*private static VehicleDao instance = null;


	public static VehicleDao getInstance() {
		if (instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}*/

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places, modele) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places, modele FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places, modele FROM Vehicle;";
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur=?, modele=?, nb_places=? WHERE id=?;";

	public int create(Vehicule vehicule) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, vehicule.getConstructeur());
			ps.setInt(2, vehicule.getNb_places());
			ps.setString(3, vehicule.getModele());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();

			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}

	public int delete(Vehicule vehicule) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(DELETE_VEHICLE_QUERY)){

			ps.setInt(1, vehicule.getVehicule_id());
			int nbDeletedRows = ps.executeUpdate();

			if (nbDeletedRows == 0) {
				throw new DaoException();
			} else {
				return nbDeletedRows;
			}

		} catch (SQLException e) {
			throw new DaoException();
		}
	}

	public Vehicule findById(int id) throws DaoException {

		try(Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(FIND_VEHICLE_QUERY)){

			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			if (!resultSet.next()) {
				throw new DaoException();
			}
			Vehicule vehicule = new Vehicule(resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(1), resultSet.getString(4));
			return vehicule;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

	}

	public List<Vehicule> findAll() throws DaoException {

		try {

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_VEHICLES_QUERY);
			ps.execute();
			ResultSet resultSet = ps.getResultSet();

			List<Vehicule> list = new ArrayList<Vehicule>();

			while (resultSet.next()){
				Vehicule vehicule = new Vehicule(resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(1), resultSet.getString(4));
				list.add(vehicule);
			}
			ps.close();
			connection.close();

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

	}

	public void update(Vehicule vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(UPDATE_VEHICLE_QUERY)) {
			ps.setString(1, vehicle.getConstructeur());
			ps.setString(2, vehicle.getModele());
			ps.setInt(3, vehicle.getNb_places());
			ps.setLong(4, vehicle.getVehicule_id());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}
	

}
