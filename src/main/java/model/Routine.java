package model;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import exception.ButtonNotFoundException;
import exception.GameWindowNotFoundException;
import exception.ScreenMismatchException;

public class Routine {

	private List<GameAction> actions = new ArrayList<>();
	
	public void run() throws ButtonNotFoundException, GameWindowNotFoundException, AWTException, ScreenMismatchException, InterruptedException {
		Iterator<GameAction> iterator = actions.iterator();
		
		while (iterator.hasNext()) {
			GameAction action = iterator.next();
			
			action.execute();
		}
	}
	
	public void add(NavigationAction action) {
		actions.add(action);
	}
	
	
}
