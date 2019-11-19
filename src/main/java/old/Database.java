package old;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import model.OcrCheck;


public class Database {
	
	public static void main(String[] args) {
		try {
			createDatabaseTables();	
			
			System.out.println("BD OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERRO AO CRIAR BANCO DE DADOS!!!!!!!!!!");
		}
		
	}
	
	
	public static Connection getConnection() throws SQLException {
		//Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/loja-virtual", "SA", "");
		Connection connection = DriverManager.getConnection("jdbc:sqlite:magicrush.db");
		
		return connection;
	}
	
	public static void runQuery(String sql) throws SQLException {
		try (Connection connection = getConnection()) {
			connection.setAutoCommit(false);
					
			try (Statement statement = connection.createStatement()) {
				statement.execute(sql);
			} catch (SQLException e) {
				connection.rollback();
				throw e;
			};
			
			connection.commit();
			
		}
	}

	public static void createDatabaseTables() throws SQLException {

		createButtonMap();
			
		createOcrMap();
		
		createActions();
		
	}

	private static void createButtonMap() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS button_map "
				+ "(id INTEGER PRIMARY KEY, "
				+ " name VARCHAR(255), "
				+ " x DECIMAL(10,4), "
				+ " y DECIMAL(10,4), "
				+ " width DECIMAL(10,4), "
				+ " height DECIMAL(10,4), "
				+ " file_name VARCHAR(255) );";
		
		runQuery(sql);
	}

	private static void createActions() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS actions "
				+ "(id INTEGER PRIMARY KEY, "
				+ " name VARCHAR(255), "
				+ " prev_screen VARCHAR(255), "
				+ " next_screen VARCHAR(255), "
				+ " button_name VARCHAR(255), "
				+ " prev_precision DECIMAL(10,4), "
				+ " next_precision DECIMAL(10,4), "
				+ " button_precision DECIMAL(10,4), "
				+ " prev_skip INT(1), "
				+ " next_skip INT(1), "
				+ " button_skip INT(1) );";
		
		runQuery(sql);
	}
	
	private static void createOcrMap() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS ocr_map "
				+ "(id INTEGER PRIMARY KEY, "
				+ " name VARCHAR(255), "
				+ " x DECIMAL(10,4), "
				+ " y DECIMAL(10,4), "
				+ " width DECIMAL(10,4), "
				+ " height DECIMAL(10,4), "
				+ " mask VARCHAR(255), "
				+ " type VARCHAR(50), "
				+ " comparator VARCHAR(50) );";
		
		runQuery(sql);
	}
	
	public static void addButtonMap(Button button) throws SQLException {
		try (Connection connection = getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO button_map ("
					+ "name, "
					+ "x, "
					+ "y, "
					+ "width, "
					+ "height, "
					+ "file_name) "
					+ "values (?, ?, ?, ?, ?, ?);"; 
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, button.getName());
				statement.setDouble(2, button.getX());
				statement.setDouble(3, button.getY());
				statement.setDouble(4, button.getWidth());
				statement.setDouble(5, button.getHeight());
				statement.setString(6, button.getFileName());
				
				statement.execute();
			} catch (SQLException e) {
				connection.rollback();
				throw e;
			};
			
			connection.commit();
			
		}
	}
	
	public static void addOcrMap(OcrCheck ocr) throws SQLException {
		try (Connection connection = getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO ocr_map ("
					+ "name, "
					+ "x, "
					+ "y, "
					+ "width, "
					+ "height, "
					+ "mask,"
					+ "type, "
					+ "comparator) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?);"; 
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, ocr.getName());
				statement.setDouble(2, ocr.getRelativeX());
				statement.setDouble(3, ocr.getRelativeY());
				statement.setDouble(4, ocr.getRelativeWidth());
				statement.setDouble(5, ocr.getRelativeHeight());
				statement.setString(6, ocr.getMask());
				statement.setString(7, ocr.getOcrType());
				statement.setString(8, ocr.getOcrComparator());
				
				statement.execute();
			} catch (SQLException e) {
				connection.rollback();
				throw e;
			};
			
			connection.commit();
			
		}
	}
	
	public static void addAction(Action action) throws SQLException {
		try (Connection connection = getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO actions ("
					+ "name, " + 
					" prev_screen, " + 
					" next_screen, " + 
					" button_name, " + 
					" prev_precision, " + 
					" next_precision, " + 
					" button_precision, " + 
					" prev_skip, " + 
					" next_skip, " + 
					" button_skip) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"; 
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, action.getName());
				statement.setString(2, action.getPrevScreen());
				statement.setString(3, action.getNextScreen());
				statement.setString(4, action.getButtonName());
				statement.setDouble(5, action.getPrevPrecision());
				statement.setDouble(6, action.getNextPrecision());
				statement.setDouble(7, action.getButtonPrecision());
				statement.setInt(8, action.getPrevSkip());
				statement.setInt(9, action.getNextSkip());
				statement.setInt(10, action.getButtonSkip());
				
				statement.execute();
			} catch (SQLException e) {
				connection.rollback();
				throw e;
			};
			
			connection.commit();
			
		}
	}
	
	
	
	
	
}
