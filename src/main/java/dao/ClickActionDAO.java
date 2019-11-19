package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.Database;
import model.ClickAction;

public class ClickActionDAO {

	public static ClickAction getClickAction(String clickActionName) throws SQLException, IOException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "SELECT * FROM click_action "
					+ "WHERE name = ?;"; 
			
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, clickActionName);
				statement.execute();
				
				ResultSet result = statement.getResultSet();
				
				if (result.next() == false) {
					throw new SQLException("Click action not found: " + clickActionName);
				}
				
				ClickAction action = new ClickAction();
				action.setName(result.getString("name"));
				action.setRelativeX(result.getDouble("x"));
				action.setRelativeY(result.getDouble("y"));
				action.setRelativeWidth(result.getDouble("width"));
				action.setRelativeHeight(result.getDouble("height"));
				
				return action;
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public static void addClickAction(ClickAction action, String type) throws SQLException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO click_action ("
					+ "name, "
					+ "x, "
					+ "y, "
					+ "width, "
					+ "height, "
					+ "mandatory, "
					+ "type) "
					+ "values (?, ?, ?, ?, ?, ?, ?);"; 
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, action.getName());
				statement.setDouble(2, action.getRelativeX());
				statement.setDouble(3, action.getRelativeY());
				statement.setDouble(4, action.getRelativeWidth());
				statement.setDouble(5, action.getRelativeHeight());
				statement.setInt(6, action.getMandatoryInteger());
				statement.setString(7, type);
				
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
			
			String sql = "DELETE FROM click_action;"; 
					
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
