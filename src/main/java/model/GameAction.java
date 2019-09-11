package model;

import java.awt.AWTException;

import exception.GameWindowNotFoundException;
import net.sourceforge.tess4j.TesseractException;

public interface GameAction {

	public Boolean execute() throws GameWindowNotFoundException, AWTException, InterruptedException;
	
	public Boolean execute(Integer target) throws GameWindowNotFoundException, AWTException, InterruptedException, TesseractException;
	
}
