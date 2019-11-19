package test;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.IOException;
import java.sql.SQLException;

import dao.RoutineDAO;
import exception.ButtonNotFoundException;
import exception.GameWindowNotFoundException;
import exception.ScreenMismatchException;
import model.Routine;
import model.UnicornImage;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TestNewRoutine {

	public static void main(String[] args) throws SQLException, IOException, ButtonNotFoundException, GameWindowNotFoundException, AWTException, ScreenMismatchException, InterruptedException, TesseractException {
//

//		
		Routine routine = RoutineDAO.getRoutine("Spend Arena Chances");
		
		routine.run();
	}
	
	public static void a(Rectangle rec) {
		rec.x = rec.x+1;
				
	}
	
	
}
