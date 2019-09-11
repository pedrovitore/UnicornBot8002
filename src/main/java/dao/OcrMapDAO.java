package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.Database;
import model.Button;
import model.NavigationAction;
import model.OcrCheck;

public class OcrMapDAO {

	public static OcrCheck getOcrCheck(String ocrName) throws SQLException, IOException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "SELECT * FROM ocr_map "
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
				
				return ocr;
			} catch (SQLException e) {
				throw e;
			}
		}
	}
	
}
