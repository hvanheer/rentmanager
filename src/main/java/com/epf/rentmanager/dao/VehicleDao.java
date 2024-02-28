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


public class VehicleDao {

	private static VehicleDao instance = null;

	public VehicleDao() {}

	public static VehicleDao getInstance() {
		if (instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}

	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";

	public int create(Vehicule vehicule) throws DaoException {
		try(Connection connection = ConnectionManager.getConnection(); PreparedStatement ps = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, vehicule.getConstructeur());
			ps.setInt(2, vehicule.getNb_places());
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
			Vehicule vehicule = new Vehicule(resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(1) );
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
				Vehicule vehicule = new Vehicule(resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(1));
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
	

}
