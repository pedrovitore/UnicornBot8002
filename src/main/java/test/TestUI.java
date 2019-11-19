package test;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import dao.RoutineDAO;
import exception.ButtonNotFoundException;
import exception.GameWindowNotFoundException;
import exception.ScreenMismatchException;
import model.Routine;
import net.sourceforge.tess4j.TesseractException;

public class TestUI {

	
	public static void main(String[] args) throws SQLException, IOException, ButtonNotFoundException, GameWindowNotFoundException, AWTException, ScreenMismatchException, InterruptedException, TesseractException {
		System.out.println("Welcome to the Unicorn Bot!");
		
		Scanner in = new Scanner(System.in);
        String input;
        
        ArrayList<Routine> routineQueue = new ArrayList<>();
        
        while (true) {
        	input = in.nextLine();
        	
        	if (input.equals("exit")) {
				break;
			}

    		Routine routine = RoutineDAO.getRoutine(input);
    		
    		routine.run();
        }
	}
	
	
}
