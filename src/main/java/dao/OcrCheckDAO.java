package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.Database;
import model.OcrCheck;

public class OcrCheckDAO {

	public static OcrCheck getOcrCheck(String ocrName) throws SQLException, IOException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "SELECT * FROM ocr_check "
					+ "WHERE name = ?;" ; 
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, ocrName);
				statement.execute();
				
				ResultSet result = statement.getResultSet();
				
				if (result.next() == false) {
					throw new SQLException("Ocr not found: " + ocrName);
				}

				OcrCheck ocr = new OcrCheck();
				
				ocr.setName(result.getString("name"));
				ocr.setRelativeX(result.getDouble("x"));
				ocr.setRelativeY(result.getDouble("y"));
				ocr.setRelativeWidth(result.getDouble("width"));
				ocr.setRelativeHeight(result.getDouble("height"));
				ocr.setMask(result.getString("mask"));
				ocr.setOcrType(result.getString("type"));
				ocr.setOcrComparator(result.getString("comparator"));
				ocr.setOutlined(result.getInt("outlined"));
				
				return ocr;
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public static void addOcrCheck(OcrCheck ocr) throws SQLException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO ocr_check ("
					+ "name, "
					+ "x, "
					+ "y, "
					+ "width, "
					+ "height, "
					+ "mask,"
					+ "type, "
					+ "comparator, "
					+ "outlined) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?);"; 
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, ocr.getName());
				statement.setDouble(2, ocr.getRelativeX());
				statement.setDouble(3, ocr.getRelativeY());
				statement.setDouble(4, ocr.getRelativeWidth());
				statement.setDouble(5, ocr.getRelativeHeight());
				statement.setString(6, ocr.getMask());
				statement.setString(7, ocr.getOcrType());
				statement.setString(8, ocr.getOcrComparator());
				statement.setInt(9, ocr.getOutlinedInteger());
				
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
			
			String sql = "DELETE FROM ocr_check;"; 
					
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
