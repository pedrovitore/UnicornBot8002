package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.Database;
import model.Button;
import model.ButtonCheck;

public class ButtonMapDAO {


	public static ButtonCheck getButtonMap(String buttonName) throws SQLException, IOException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "SELECT * FROM button_map "
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
	
//	OLD CODE
//	public static Button getButtonMap(String buttonName) throws SQLException {
//		try (Connection connection = Database.getConnection()) {
//			connection.setAutoCommit(false);
//			
//			String sql = "SELECT * FROM button_map "
//					+ "WHERE name = ?;"; 
//			
//			try (PreparedStatement statement = connection.prepareStatement(sql)) {
//				statement.setString(1, buttonName);
//				statement.execute();
//				
//				ResultSet result = statement.getResultSet();
//				
//				if (result.next() == false) {
//					throw new SQLException("Button not found: " + buttonName);
//				}
//				
//				Button button = new Button();
//				button.setName(result.getString("name"));
//				button.setX(result.getDouble("x"));
//				button.setY(result.getDouble("y"));
//				button.setWidth(result.getDouble("width"));
//				button.setHeight(result.getDouble("height"));
//				button.setFileName(result.getString("file_name"));
//				
//				return button;
//			} catch (SQLException e) {
//				throw e;
//			}
//		}
//	}
	
	
}
