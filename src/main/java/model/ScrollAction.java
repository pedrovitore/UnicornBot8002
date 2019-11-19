package model;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import dao.RequirementDAO;
import exception.ButtonNotFoundException;
import exception.GameWindowNotFoundException;
import jna.UnicornWindowControl;
import net.sourceforge.tess4j.TesseractException;
import test.GuiConsole;

public class ScrollAction implements Action {

	private String name;
	private Double relativeX;
	private Double relativeY;
	private Double relativeWidth;
	private Double relativeHeight;
	private Boolean mandatory;
	
	public Boolean run() throws GameWindowNotFoundException, TesseractException, AWTException, ButtonNotFoundException, InterruptedException {
		
		try {
			GuiConsole.println("Action start - " + this.name);
			
			List<Requirement> requirements = RequirementDAO.getRequirementsFromAction(this.name);
			Rectangle buttonRectangle = new Rectangle(-1,-1,-1,-1);
			
			Iterator<Requirement> it = requirements.iterator();
			while (it.hasNext()) {
				Requirement requirement = it.next();
				
				if (!requirement.check(buttonRectangle)) {
					GuiConsole.println(this.name + " - Requirement not met: " + requirement.getCheckName() + " " + requirement.getValue());
					return false;
				}
			}
			Double scrollYPosition = relativeY + (relativeHeight/2);
			
			GuiConsole.println("Scrolling right " + relativeX + "% at y  " + relativeY + "%");
			UnicornWindowControl.scrollHorizontal(relativeWidth, scrollYPosition);
			
			return true;
		} catch (SQLException e) {
			GuiConsole.println("Failed to get requirements from action: " + this.name);
			return false;
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getRelativeX() {
		return relativeX;
	}
	public void setRelativeX(Double relativeX) {
		this.relativeX = relativeX;
	}
	public Double getRelativeY() {
		return relativeY;
	}
	public void setRelativeY(Double relativeY) {
		this.relativeY = relativeY;
	}
	public Double getRelativeWidth() {
		return relativeWidth;
	}
	public void setRelativeWidth(Double relativeWidth) {
		this.relativeWidth = relativeWidth;
	}
	public Double getRelativeHeight() {
		return relativeHeight;
	}
	public void setRelativeHeight(Double relativeHeight) {
		this.relativeHeight = relativeHeight;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public Integer getMandatoryInteger() {
		if (mandatory) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	/**
	 * 
	 * @param mandatory 0 = false, 1 = true
	 */
	public void setMandatory(Integer mandatory) {
		switch (mandatory) {
		case 1:
			this.mandatory = true;
			break;
		case 0:
			this.mandatory = false;
			break;
		default:
			this.mandatory = true;
			break;
		}
	}
	
}
