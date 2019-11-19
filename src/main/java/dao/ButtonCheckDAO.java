package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.Database;
import model.ButtonCheck;

public class ButtonCheckDAO {


	public static ButtonCheck getButtonMap(String buttonName) throws SQLException, IOException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "SELECT * FROM button_check "
					+ "WHERE name = ?;"; 
			
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, buttonName);
				statement.execute();
				
				ResultSet result = statement.getResultSet();
				
				if (result.next() == false) {
					throw new SQLException("Button not found: " + buttonName);
				}
				
				ButtonCheck button = new ButtonCheck();
				button.setName(result.getString("name"));
				button.setRelativeX(result.getDouble("x"));
				button.setRelativeY(result.getDouble("y"));
				button.setRelativeWidth(result.getDouble("width"));
				button.setRelativeHeight(result.getDouble("height"));
				button.setMask(result.getString("file_name"));
				button.setPrecision(result.getDouble("precision"));
				
				return button;
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public static void addButtonCheck(ButtonCheck button, String fileName) throws SQLException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO button_check ("
					+ "name, "
					+ "x, "
					+ "y, "
					+ "width, "
					+ "height, "
					+ "file_name,"
					+ "precision) "
					+ "values (?, ?, ?, ?, ?, ?, ?);"; 
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, button.getName());
				statement.setDouble(2, button.getRelativeX());
				statement.setDouble(3, button.getRelativeY());
				statement.setDouble(4, button.getRelativeWidth());
				statement.setDouble(5, button.getRelativeHeight());
				statement.setString(6, fileName);
				statement.setDouble(7, button.getPrecision());
				
				statement.execute();
			} catch (SQLException e) {
				connection.rollback();
				throw e;
			};
			
			connection.commit();
			
		}
	}

	public static void clearTable() throws SQLException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "DELETE FROM button_check;"; 
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				
				statement.execute();
				
			} catch (SQLException e) {
				connection.rollback();
				throw e;
			};
			
			connection.commit();
			
		}
	}
	
	
}
