package old;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.Database;

public class NavigationActionDAO {

	public static NavigationAction getNavigationAction(String actionName) throws SQLException, IOException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			
			String sql = "SELECT * FROM actions "
					+ "WHERE name = ?;" ; 
					
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, actionName);
				statement.execute();
				
				ResultSet result = statement.getResultSet();
				
				if (result.next() == false) {
					throw new SQLException("Action not found: " + actionName);
				}

				NavigationAction navigationAction = new NavigationAction();

				navigationAction.setPreviousScreen(result.getString("prev_screen"));
				navigationAction.setNextScreen(result.getString("next_screen"));
				navigationAction.setPreviousScreenPrecision(result.getDouble("prev_precision"));
				navigationAction.setNextScreenPrecision(result.getDouble("next_precision"));
				navigationAction.setButtonPrecision(result.getDouble("button_precision"));
				navigationAction.setSkipPreviousValidation(result.getInt("prev_skip"));
				navigationAction.setSkipNextValidation(result.getInt("next_skip"));
				navigationAction.setSkipButtonValidation(result.getInt("button_skip"));
//
//				Button button = ButtonMapDAO.getButtonMap(result.getString("button_name"));
//
//				navigationAction.setButtonRelativeX(button.getX());
//				navigationAction.setButtonRelativeY(button.getY());
//				navigationAction.setButtonRelativeWidth(button.getWidth());
//				navigationAction.setButtonRelativeHeight(button.getHeight());
//				navigationAction.setButtonMask(button.getFileName());
				
				return navigationAction;
			} catch (SQLException e) {
				throw e;
			}
		}
	}
	
}
