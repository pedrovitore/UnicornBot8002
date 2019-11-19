package model;

import java.awt.AWTException;

import exception.GameWindowNotFoundException;
import jna.UnicornWindowControl;
import net.sourceforge.tess4j.TesseractException;
import test.GuiConsole;

public class OcrCheck {

	private String name;
	private double relativeX;
	private double relativeY;
	private double relativeWidth;
	private double relativeHeight;
	private String mask;
	private String ocrType;
	private String ocrComparator;
	private Boolean outlined;
	
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

		if (ocrType.equals(OcrType.NUMBER_PURE.toString())
			|| ocrType.equals(OcrType.NUMBER_SPLIT.toString())
			|| ocrType.equals(OcrType.NUMBER_SUFFIX.toString())) {
			return doNumberCheck(target);
		}

		if (ocrType.equals(OcrType.TEXT.toString())) {
			return doTextCheck(mask);
		}
		
		GuiConsole.println("No Ocr Type found for " + this.name);
		return false;
	}

	private Boolean doNumberCheck(Integer target) throws AWTException, TesseractException {
		try {
			Integer value = UnicornWindowControl.findOcrInteger(relativeX, relativeY, relativeWidth, relativeHeight, mask, ocrType);
			GuiConsole.println("Comparing OCR Number");
			GuiConsole.println("Target: " + target);
			GuiConsole.println("Value on screen: " + value);
			
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
		} catch (NumberFormatException e) {
			GuiConsole.println("Parse failed for " + this.name + " check: Screen text is not a number.");
			return false;
		}
		
	}
	
	private Boolean doTextCheck(String target) throws AWTException, TesseractException {
		String value = UnicornWindowControl.findOcrText(relativeX, relativeY, relativeWidth, relativeHeight, mask, ocrType, outlined).trim();
		GuiConsole.println("Comparing OCR");
		GuiConsole.println("Mask: " + target);
		GuiConsole.println("Value on screen: " + value);
		
		if (ocrComparator.equals(
				OcrComparator.EQUAL_TO.toString())
				&& value.equals(target) ) {
			return true;
		}
		
		return false;
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

	public Boolean getOutlined() {
		return outlined;
	}

	public Integer getOutlinedInteger() {
		if (outlined) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public void setOutlined(Boolean outlined) {
		this.outlined = outlined;
	}
	
	/**
	 * 
	 * @param mandatory 0 = false, 1 = true
	 */
	public void setOutlined(Integer outlined) {
		switch (outlined) {
		case 1:
			this.outlined = true;
			break;
		case 0:
			this.outlined = false;
			break;
		default:
			this.outlined = false;
			break;
		}
	}
	
}
