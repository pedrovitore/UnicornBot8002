package test;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;

import exception.ButtonNotFoundException;
import exception.GameWindowNotFoundException;
import exception.ScreenMismatchException;
import jdbc.Database;
import model.Routine;
import old.Button;
import old.NavigationAction;
import old.NavigationActionDAO;
import old.OldRoutine;

public class TesteActionJDBC {

	
	public static void main(String[] args) throws SQLException, IOException, ButtonNotFoundException, GameWindowNotFoundException, AWTException, ScreenMismatchException, InterruptedException {
		
		//Button button = Database.getButtonMap("Open Crystal Dungeon");
		OldRoutine routine = new OldRoutine();

		routine.add(NavigationActionDAO.getNavigationAction("Open Astrologer"));
		routine.add(NavigationActionDAO.getNavigationAction("Do Horoscope"));
		
		routine.run();
	}

	private static void sweepProvingGrounds() throws SQLException, IOException, ButtonNotFoundException,
			GameWindowNotFoundException, AWTException, ScreenMismatchException, InterruptedException {
		OldRoutine routine = new OldRoutine();

		routine.add(NavigationActionDAO.getNavigationAction("Open Proving Grounds"));
		routine.add(NavigationActionDAO.getNavigationAction("Sweep Proving Grounds"));
		
		routine.run();
	}
	
	public static void sweepCrystalDungeon() throws SQLException, IOException, ButtonNotFoundException, GameWindowNotFoundException, AWTException, ScreenMismatchException, InterruptedException {
		OldRoutine routine = new OldRoutine();
		

		routine.add(NavigationActionDAO.getNavigationAction("Open Crystal Dungeon"));
		routine.add(NavigationActionDAO.getNavigationAction("Open Crystal Dungeon Sweep"));
		routine.add(NavigationActionDAO.getNavigationAction("Sweep Crystal Dungeon"));
		
		routine.run();
	}
}
