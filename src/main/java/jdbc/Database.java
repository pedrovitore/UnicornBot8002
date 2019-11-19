package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import old.Action;


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
		//Connection connection = DriverManager.getConnection("jdbc:sqlite:magicrush.db");
		Connection connection = DriverManager.getConnection("jdbc:sqlite:unicorn.db");
		
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
		createOcrCheck();
		createButtonCheck();
		createClickAction();
		createRoutine();
		createRequirement();
		createRoutineAction();
	}


	
	private static void createOcrCheck() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS ocr_check "
				+ "(id INTEGER PRIMARY KEY, "
				+ " name VARCHAR(255), "
				+ " x DECIMAL(10,4), "
				+ " y DECIMAL(10,4), "
				+ " width DECIMAL(10,4), "
				+ " height DECIMAL(10,4), "
				+ " mask VARCHAR(255), "
				+ " type VARCHAR(50), "
				+ " comparator VARCHAR(50), "
				+ " outlined INTEGER );";
		
		runQuery(sql);
	}


	
	private static void createButtonCheck() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS button_check "
				+ "(id INTEGER PRIMARY KEY, "
				+ " name VARCHAR(255), "
				+ " x DECIMAL(10,4), "
				+ " y DECIMAL(10,4), "
				+ " width DECIMAL(10,4), "
				+ " height DECIMAL(10,4), "
				+ " file_name VARCHAR(255), "
				+ " precision DECIMAL(10,4) );";
		
		runQuery(sql);
	}


	
	private static void createClickAction() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS click_action "
				+ "(id INTEGER PRIMARY KEY, "
				+ " name VARCHAR(255), "
				+ " x DECIMAL(10,4), "
				+ " y DECIMAL(10,4), "
				+ " width DECIMAL(10,4), "
				+ " height DECIMAL(10,4),"
				+ " mandatory INTEGER, "
				+ " type VARCHAR(50) );";
		
		runQuery(sql);
	}


	
	private static void createRoutine() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS routine "
				+ "(id INTEGER PRIMARY KEY, "
				+ " name VARCHAR(255) );";
		
		runQuery(sql);
	}


	
	private static void createRequirement() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS requirement "
				+ "(id INTEGER PRIMARY KEY, "
				+ " action_name VARCHAR(255), "
				+ " check_name VARCHAR(255), "
				+ " check_type VARCHAR(255), "
				+ " value INTEGER );";
		
		runQuery(sql);
	}
	

	
	private static void createRoutineAction() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS routine_action "
				+ "(id INTEGER PRIMARY KEY, "
				+ " routine_name VARCHAR(255), "
				+ " action_name VARCHAR(255), "
				+ " action_order INTEGER );";
		
		runQuery(sql);
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
