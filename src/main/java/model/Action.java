package model;

import java.awt.AWTException;

import exception.ButtonNotFoundException;
import exception.GameWindowNotFoundException;
import net.sourceforge.tess4j.TesseractException;

public interface Action {

	String name = "";
	Double relativeX = 0.0;
	Double relativeY = 0.0;
	Double relativeWidth = 0.0;
	Double relativeHeight = 0.0;
	Boolean mandatory = true;
	
	public Boolean run() throws GameWindowNotFoundException, TesseractException, AWTException, ButtonNotFoundException, InterruptedException;

	public void setName(String string);

	public void setRelativeX(Double double1);

	public void setRelativeY(Double double1);

	public void setRelativeWidth(Double double1);

	public void setRelativeHeight(Double double1);

	public void setMandatory(Integer int1);

	String getName();

	void setMandatory(Boolean mandatory);

	Integer getMandatoryInteger();

	Boolean getMandatory();

	Double getRelativeWidth();

	Double getRelativeY();

	Double getRelativeX();
	

	
	
}
