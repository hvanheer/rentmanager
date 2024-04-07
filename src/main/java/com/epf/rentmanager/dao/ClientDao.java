package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {

	public ClientDao() {}

/*
	private static ClientDao instance = null;

	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	} */
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom=?, prenom=?, email=?, naissance=? WHERE id=?;";

	public int create(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();PreparedStatement ps = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS)){
        ps.setString(1, client.getNom());
        ps.setString(2, client.getPrenom());
        ps.setString(3, client.getEmail());
        ps.setDate(4, Date.valueOf(client.getNaissance()));
        ps.executeUpdate();
        ResultSet resultSet = ps.getGeneratedKeys();
        int result = 0;
        if (resultSet.next()) {
            result = resultSet.getInt(1);
        }
        return result;


    } catch (SQLException e) {
		e.printStackTrace();
		throw new DaoException();
	}

	}

	//NE FONCTIONNE PAS
	public int delete(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();PreparedStatement ps = connection.prepareStatement(DELETE_CLIENT_QUERY)){
			System.out.println(client.getClient_id());
			ps.setInt(1, client.getClient_id());
			int nbDeletedRows = ps.executeUpdate();
			if (nbDeletedRows == 0) {
				throw new DaoException();
			}
			else {
				return nbDeletedRows;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}








	public Client findById(int id) throws DaoException {

		try {

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENT_QUERY);
			ps.setInt(1, id);
			ps.execute();
			ResultSet resultSet = ps.getResultSet();
			if (!resultSet.next()) {
				throw new DaoException();
			}

			Client client = new Client(resultSet.getString(2), resultSet.getString(3), resultSet.getDate(5).toLocalDate(), resultSet.getString(4), resultSet.getInt(1));

			ps.close();
			connection.close();

			return client;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}

	}

	public List<Client> findAll() throws DaoException {

		try {

			Connection connection = ConnectionManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(FIND_CLIENTS_QUERY);
			ps.execute();
			ResultSet resultSet = ps.getResultSet();

			List<Client> list = new ArrayList<Client>();

			while (resultSet.next()){
				Client client = new Client(resultSet.getString(2), resultSet.getString(3), resultSet.getDate(5).toLocalDate(), resultSet.getString(4), resultSet.getInt(1));
				list.add(client);
			}

			ps.close();
			connection.close();

			return list;

		} catch (SQLException e) {
			//e.printStackTrace();
			throw new DaoException();
		}


	}

	public void update(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement ps = connection.prepareStatement(UPDATE_CLIENT_QUERY)) {
			ps.setString(1, client.getNom());
			ps.setString(2, client.getPrenom());
			ps.setString(3, client.getEmail());
			ps.setDate(4, Date.valueOf(client.getNaissance()));
			ps.setInt(5, client.getClient_id());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException();
		}
	}

}
