package test;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;

import dao.NavigationActionDAO;
import exception.ButtonNotFoundException;
import exception.GameWindowNotFoundException;
import exception.ScreenMismatchException;
import jdbc.Database;
import model.Button;
import model.NavigationAction;
import model.Routine;

public class TesteActionJDBC {

	
	public static void main(String[] args) throws SQLException, IOException, ButtonNotFoundException, GameWindowNotFoundException, AWTException, ScreenMismatchException, InterruptedException {
		
		//Button button = Database.getButtonMap("Open Crystal Dungeon");
		Routine routine = new Routine();

		routine.add(NavigationActionDAO.getNavigationAction("Open Astrologer"));
		routine.add(NavigationActionDAO.getNavigationAction("Do Horoscope"));
		
		routine.run();
	}

	private static void sweepProvingGrounds() throws SQLException, IOException, ButtonNotFoundException,
			GameWindowNotFoundException, AWTException, ScreenMismatchException, InterruptedException {
		Routine routine = new Routine();

		routine.add(NavigationActionDAO.getNavigationAction("Open Proving Grounds"));
		routine.add(NavigationActionDAO.getNavigationAction("Sweep Proving Grounds"));
		
		routine.run();
	}
	
	public static void sweepCrystalDungeon() throws SQLException, IOException, ButtonNotFoundException, GameWindowNotFoundException, AWTException, ScreenMismatchException, InterruptedException {
		Routine routine = new Routine();
		

		routine.add(NavigationActionDAO.getNavigationAction("Open Crystal Dungeon"));
		routine.add(NavigationActionDAO.getNavigationAction("Open Crystal Dungeon Sweep"));
		routine.add(NavigationActionDAO.getNavigationAction("Sweep Crystal Dungeon"));
		
		routine.run();
	}
}
