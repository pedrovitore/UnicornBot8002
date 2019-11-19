package model;

import java.awt.AWTException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dao.RoutineDAO;
import exception.ButtonNotFoundException;
import exception.GameWindowNotFoundException;
import exception.ScreenMismatchException;
import net.sourceforge.tess4j.TesseractException;
import test.GuiConsole;

public class Routine {
	
	private String name;
	
	public void run() throws ButtonNotFoundException, GameWindowNotFoundException, AWTException, ScreenMismatchException, InterruptedException, TesseractException, SQLException {
		GuiConsole.println("=========ROUTINE START=========");
		GuiConsole.println("Routine: " + this.name);
		
		List<Action> actions = RoutineDAO.getActions(this.name);
		
		Iterator<Action> iterator = actions.iterator();
		
		while (iterator.hasNext()) {
			Action action = iterator.next();
			
			if (!action.run()) {
				if (action.getMandatory()) {
					GuiConsole.println("Mandatory action not completed - ending routine");
					GuiConsole.println("========ROUTINE FAILED========");
					GuiConsole.println("Routine: " + this.name);
					return;
				} else {
					GuiConsole.println("*Optional action not completed: " + action.getName());
				}
			}
			
			//wait 3 seconds for action to resolve
			TimeUnit.SECONDS.sleep(3);
		}
		
		GuiConsole.println("========ROUTINE SUCCESS========");
	}

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
