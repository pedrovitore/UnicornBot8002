package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.Database;
import model.Requirement;

public class RequirementDAO {

	public static List<Requirement> getRequirementsFromAction(String actionName) throws SQLException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "SELECT * FROM requirement "
					+ "WHERE action_name = ?;"; 
			
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, actionName);
				statement.execute();
				
				ResultSet result = statement.getResultSet();
				List<Requirement> results = new ArrayList<>();
				
				while (result.next()) {
					Requirement requirement = new Requirement();
					
					requirement.setActionName(result.getString("action_name"));
					requirement.setCheckName(result.getString("check_name"));
					requirement.setCheckType(result.getString("check_type"));
					requirement.setValue(result.getInt("value"));
					
					results.add(requirement);
				}
				
				return results;
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public static void addRequirement(Requirement requirement) throws SQLException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO requirement ("
					+ "action_name, "
					+ "check_name, "
					+ "check_type, "
					+ "value) "
					+ "values (?, ?, ?, ?);"; 
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, requirement.getActionName());
				statement.setString(2, requirement.getCheckName());
				statement.setString(3, requirement.getCheckType());
				statement.setInt(4, requirement.getValue());
				
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
			
			String sql = "DELETE FROM requirement;"; 
					
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
