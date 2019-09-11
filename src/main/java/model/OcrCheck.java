package model;

import java.awt.AWTException;

import exception.GameWindowNotFoundException;
import jna.UnicornWindowControl;
import net.sourceforge.tess4j.TesseractException;

public class OcrCheck implements GameAction {

	private String name;
	private double relativeX;
	private double relativeY;
	private double relativeWidth;
	private double relativeHeight;
	private String mask;
	private String ocrType;
	private String ocrComparator;
	
	/**
	 * 
	 * @param target	Expected value.
	 * @return
	 * @throws GameWindowNotFoundException
	 * @throws AWTException
	 * @throws TesseractException
	 */
	public Boolean execute(Integer target) throws GameWindowNotFoundException, AWTException, TesseractException {
		if (!UnicornWindowControl.isGameRunning()) {
			throw new GameWindowNotFoundException("BlueStacks is not running!");
		}

		UnicornWindowControl.setForegroundWindowByTitle("BlueStacks");

		Integer value = UnicornWindowControl.findOcrInteger(relativeX, relativeY, relativeWidth, relativeHeight, mask, ocrType);
		
		if (ocrComparator.equals(
				OcrComparator.HIGHER_THAN.toString())
				&& value >= target) {
			return true;
		}
		
		if (ocrComparator.equals(
				OcrComparator.LOWER_THAN.toString())
				&& value <= target) {
			return true;
		}
		
		if (ocrComparator.equals(
				OcrComparator.EQUAL_TO.toString())
				&& value == target) {
			return true;
		}
		
		return false;
	}
	
	public Boolean execute() {
		throw new UnsupportedOperationException("Error on ocr check: " + this.name + "\r\n "
												+ "OcrCheck needs a target value and a OcrComparator.");
	}
	
	public double getRelativeX() {
		return relativeX;
	}

	public void setRelativeX(double relativeX) {
		this.relativeX = relativeX;
	}

	public double getRelativeY() {
		return relativeY;
	}

	public void setRelativeY(double relativeY) {
		this.relativeY = relativeY;
	}

	public double getRelativeWidth() {
		return relativeWidth;
	}

	public void setRelativeWidth(double relativeWidth) {
		this.relativeWidth = relativeWidth;
	}

	public double getRelativeHeight() {
		return relativeHeight;
	}

	public void setRelativeHeight(double relativeHeight) {
		this.relativeHeight = relativeHeight;
	}

	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getOcrType() {
		return ocrType;
	}
	public void setOcrType(String ocrType) {
		this.ocrType = ocrType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOcrComparator() {
		return ocrComparator;
	}

	public void setOcrComparator(String ocrComparator) {
		this.ocrComparator = ocrComparator;
	}
	
}
