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

public class ClickAction implements Action {

	private String name;
	private Double relativeX;
	private Double relativeY;
	private Double relativeWidth;
	private Double relativeHeight;
	private Boolean mandatory;
	
	@Override
	public Boolean run() throws GameWindowNotFoundException, TesseractException, AWTException, ButtonNotFoundException {
		
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
			
			//Se estiver nulo neste ponto é porque a ação não tem buttonCheck, portanto vai buscar direto
			if ( buttonRectangle.equals(new Rectangle(-1,-1,-1,-1))  ) {
				buttonRectangle =  UnicornWindowControl.findButton(relativeX, relativeY, relativeWidth, relativeHeight, null, 0);
			}
			
			GuiConsole.println("Clicking on rectangle: " + buttonRectangle.x + ", " + buttonRectangle.y + ", " + buttonRectangle.width + ", " + buttonRectangle.height);
			UnicornWindowControl.clickOnButton(buttonRectangle);
			
			return true;
		} catch (SQLException e) {
			GuiConsole.println("Failed to get requirements from action: " + this.name);
			return false;
		}
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public Double getRelativeX() {
		return relativeX;
	}
	@Override
	public void setRelativeX(Double relativeX) {
		this.relativeX = relativeX;
	}
	@Override
	public Double getRelativeY() {
		return relativeY;
	}
	@Override
	public void setRelativeY(Double relativeY) {
		this.relativeY = relativeY;
	}
	@Override
	public Double getRelativeWidth() {
		return relativeWidth;
	}
	@Override
	public void setRelativeWidth(Double relativeWidth) {
		this.relativeWidth = relativeWidth;
	}
	public Double getRelativeHeight() {
		return relativeHeight;
	}
	@Override
	public void setRelativeHeight(Double relativeHeight) {
		this.relativeHeight = relativeHeight;
	}

	@Override
	public Boolean getMandatory() {
		return mandatory;
	}

	@Override
	public Integer getMandatoryInteger() {
		if (mandatory) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	/**
	 * 
	 * @param mandatory 0 = false, 1 = true
	 */
	@Override
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
