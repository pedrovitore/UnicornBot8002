package model;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.io.IOException;
import java.sql.SQLException;

import dao.ButtonCheckDAO;
import dao.OcrCheckDAO;
import exception.GameWindowNotFoundException;
import net.sourceforge.tess4j.TesseractException;
import test.GuiConsole;

public class Requirement {

	private String actionName;
	private String checkName;
	private String checkType;
	private Integer value;
	
	public Boolean check(Rectangle buttonRectangle) throws GameWindowNotFoundException, TesseractException {
		
		try {
			if (this.checkType.equals("BUTTON")) {
				ButtonCheck buttonCheck = ButtonCheckDAO.getButtonMap(this.checkName);
				return buttonCheck.execute(this.value, buttonRectangle);
			}
			

			if (this.checkType.equals("OCR")) {
				OcrCheck ocrCheck = OcrCheckDAO.getOcrCheck(this.checkName);
				return ocrCheck.execute(this.value);
			}
			
			GuiConsole.println("No type informed for requirement " + this.checkName + " of action " + this.actionName);
			return false;
		} catch (GameWindowNotFoundException e) {
			throw e;
		} catch (AWTException | InterruptedException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			GuiConsole.println("Check object not found for requirement " + this.checkName + " of action " + this.actionName);
			return false;
		} catch (IOException e) {
			GuiConsole.println("Mask not found for button: " + this.checkName);
			return false;
		}
	}
	
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
}
