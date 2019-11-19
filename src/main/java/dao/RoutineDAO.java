package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.Database;
import model.Action;
import model.ClickAction;
import model.Routine;
import model.RoutineAction;
import model.ScrollAction;
import model.WaitAction;

public class RoutineDAO {

	public static Routine getRoutine(String routineName) throws SQLException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "SELECT * FROM routine "
					+ "WHERE name = ?;"; 
			
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, routineName);
				statement.execute();
				
				ResultSet result = statement.getResultSet();
				
				if (result.next() == false) {
					throw new SQLException("Routine not found: " + routineName);
				}
				
				Routine routine = new Routine();
				routine.setName(result.getString("name"));
				
				return routine;
			} catch (SQLException e) {
				throw e;
			}
		}
	}

	public static List<Routine> getRoutines() throws SQLException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "SELECT * FROM routine";
			
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.execute();
				
				ResultSet result = statement.getResultSet();
				List<Routine> routines = new ArrayList<>();
				
				while (result.next()) {
					Routine routine = new Routine();
					routine.setName(result.getString("name"));
					
					routines.add(routine);
				}

				if (routines.size() == 0) {
					throw new SQLException("No routines found in database");
				}
				
				return routines;
			} catch (SQLException e) {
				throw e;
			}
		}
	}
	

	public static List<Action> getActions(String routineName) throws SQLException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "SELECT * FROM click_action c " + 
					"INNER JOIN routine_action r on r.action_name = c.name " + 
					"WHERE r.routine_name = ? "
					+ "ORDER BY action_order ASC;"; 
			
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, routineName);
				statement.execute();
				
				ResultSet result = statement.getResultSet();
				List<Action> results = new ArrayList<>();
				
				while (result.next()) {
					String type = result.getString("type");
					
					Action action;
					
					if (type.equals("CLICK")) {
						action = new ClickAction();
					} else if (type.equals("SCROLL")) {
						action = new ScrollAction();
					} else if (type.equals("WAIT")) {
						action = new WaitAction();
					} else {
						throw new SQLException("No type informed for action: " + result.getString("name"));
					}
					
					action.setName(result.getString("name"));
					action.setRelativeX(result.getDouble("x"));
					action.setRelativeY(result.getDouble("y"));
					action.setRelativeWidth(result.getDouble("width"));
					action.setRelativeHeight(result.getDouble("height"));
					action.setMandatory(result.getInt("mandatory"));
					
					results.add(action);
				}
				
				if (results.size() == 0) {
					throw new SQLException("No actions found for routine: " + routineName);
				}
				
				return results;
			} catch (SQLException e) {
				throw e;
			}
		}
	}


	public static void addRoutine(Routine routine) throws SQLException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO routine ("
					+ "name) "
					+ "values (?);"; 
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, routine.getName());
				
				statement.execute();
			} catch (SQLException e) {
				connection.rollback();
				throw e;
			};
			
			connection.commit();
			
		}
	}

	public static void addAction(RoutineAction action) throws SQLException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "INSERT INTO routine_action ("
					+ "routine_name, "
					+ "action_name, "
					+ "action_order) "
					+ "values (?, ?, ?);"; 
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, action.getRoutineName());
				statement.setString(2, action.getActionName());
				statement.setInt(3, action.getActionOrder());
				
				statement.execute();
			} catch (SQLException e) {
				connection.rollback();
				throw e;
			};
			
			connection.commit();
			
		}
	}

	public static void clearTableAndChildren() throws SQLException {
		clearTable();
		clearChildren();
	}

	public static void clearChildren() throws SQLException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "DELETE FROM routine_action;"; 
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				
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
			
			String sql = "DELETE FROM routine;"; 
					
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
