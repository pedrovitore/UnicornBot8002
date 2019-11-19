package old;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import exception.ButtonNotFoundException;
import exception.GameWindowNotFoundException;
import exception.ScreenMismatchException;
import jna.UnicornWindowControl;
import model.UnicornImage;

/**
 * Performs an action of navigation.
 * @author Pon
 *
 */
public class NavigationAction {

	private UnicornImage previousScreen;
	private UnicornImage nextScreen;
	private double buttonRelativeX;
	private double buttonRelativeY;
	private double buttonRelativeWidth;
	private double buttonRelativeHeight;
	private UnicornImage buttonMask;
	private double previousScreenPrecision;
	private double nextScreenPrecision;
	private double buttonPrecision;
	private boolean skipPreviousValidation = false;
	private boolean skipNextValidation = false;
	private boolean skipButtonValidation = false;
	
	public boolean isScreenOK(UnicornImage maskImage, double precision) throws AWTException {
		UnicornImage gameImage = UnicornWindowControl.getGameScreen();
		
		//resizes game image to the same size of the mask
		gameImage = gameImage.resize(maskImage.getWidth(), maskImage.getHeight());
		
		//compares image similarity
		double similarity = gameImage.compare(maskImage);
		
		if ( similarity >= precision ) {
			return true;
		}
		
		return false;
	}

	public boolean isPreviousScreenOK() throws AWTException {

		return isScreenOK(previousScreen, previousScreenPrecision);
		
	}
	
	public boolean isNextScreenOK() throws AWTException {

		return isScreenOK(nextScreen, nextScreenPrecision);
		
	}
	
	
	
	
	public void execute() throws ButtonNotFoundException, GameWindowNotFoundException, AWTException, ScreenMismatchException, InterruptedException {
		if (!UnicornWindowControl.isGameRunning()) {
			throw new GameWindowNotFoundException("BlueStacks is not running!");
		}

		UnicornWindowControl.setForegroundWindowByTitle("BlueStacks");

		if (!this.skipPreviousValidation) {
			if (!isPreviousScreenOK()) {
				throw new ScreenMismatchException("Previous screen not ready!");
			}
		}
		
		
		Rectangle buttonRectangle = new Rectangle();
		
		if ( buttonRelativeX < 0 ) {
			
			//se x < 0 significa que a tela é scrollável e ele pode estar em qualquer X. � necess�rio uma varredura.
			int scrollCounter = 0;
			boolean buttonFound = false;
			
			//procura pelo bot�o. Se n�o encontrar, tenta scrollar a tela na horizontal 3 vezes. Se mesmo assim n�o encontrar, devolve a exce��o.
			while ( !buttonFound ) {
				try {
					buttonRectangle =  UnicornWindowControl.findButtonHorizontal(buttonRelativeY, buttonRelativeWidth, buttonRelativeHeight, buttonMask, buttonPrecision);
					buttonFound = true;
				} catch (ButtonNotFoundException e) {
					if ( scrollCounter >= 4 ) {
						throw e;
					}
					
					//faz o scroll para a direita 2 vezes, depois desfaz os 2 scrolls da direita e faz 2 scrolls para a esquerda
					if ( scrollCounter <= 1 ) {
						UnicornWindowControl.scrollHorizontal(0.25);
						UnicornWindowControl.scrollHorizontal(0.25);
					} else if ( scrollCounter == 2 ) {
						UnicornWindowControl.scrollHorizontal(-0.25);
						UnicornWindowControl.scrollHorizontal(-0.25);
						UnicornWindowControl.scrollHorizontal(-0.25);
						UnicornWindowControl.scrollHorizontal(-0.25);
						UnicornWindowControl.scrollHorizontal(-0.25);
						UnicornWindowControl.scrollHorizontal(-0.25);
					} else {
						UnicornWindowControl.scrollHorizontal(-0.25);
						UnicornWindowControl.scrollHorizontal(-0.25);
					}
					
					scrollCounter++;
				}
			}
			
		} else {
			//Se x e y são >= 0, então o botão fica em ponto específico
			
			if (this.skipButtonValidation) {
				//pass mask as null, so findButton function will skip image validation.
				buttonRectangle =  UnicornWindowControl.findButton(buttonRelativeX, buttonRelativeY, buttonRelativeWidth, buttonRelativeHeight, null, buttonPrecision);
			} else {
				buttonRectangle =  UnicornWindowControl.findButton(buttonRelativeX, buttonRelativeY, buttonRelativeWidth, buttonRelativeHeight, buttonMask, buttonPrecision);
			}
			
		}
		
		UnicornWindowControl.clickOnButton(buttonRectangle);

		//wait 2 seconds for click to resolve. keeps trying until screen is found
		TimeUnit.SECONDS.sleep(2);
		if (!this.skipNextValidation) {
			while (!isNextScreenOK()) {
				TimeUnit.SECONDS.sleep(5);
			}
		}
	}

	public UnicornImage getPreviousScreen() {
		return previousScreen;
	}

	public void setPreviousScreen(UnicornImage previousScreen) {
		this.previousScreen = previousScreen;
	}

	/**
	 * Image must be inside folder "images" on project root directory.
	 * @param imageName
	 * @throws IOException
	 */
	public void setPreviousScreen(String imageName) throws IOException {
		UnicornImage previousImage = new UnicornImage("images//" + imageName);
		this.previousScreen = previousImage;
	}

	public UnicornImage getNextScreen() {
		return nextScreen;
	}

	public void setNextScreen(UnicornImage nextScreen) {
		this.nextScreen = nextScreen;
	}

	/**
	 * Image must be inside folder "images" on project root directory.
	 * @param imageName
	 * @throws IOException
	 */
	public void setNextScreen(String imageName) throws IOException {
		UnicornImage nextImage = new UnicornImage("images//" + imageName);
		this.nextScreen = nextImage;
	}

	public double getButtonRelativeX() {
		return buttonRelativeX;
	}

	public void setButtonRelativeX(double buttonRelativeX) {
		this.buttonRelativeX = buttonRelativeX;
	}

	public double getButtonRelativeY() {
		return buttonRelativeY;
	}

	public void setButtonRelativeY(double buttonRelativeY) {
		this.buttonRelativeY = buttonRelativeY;
	}

	public double getButtonRelativeWidth() {
		return buttonRelativeWidth;
	}

	public void setButtonRelativeWidth(double buttonRelativeWidth) {
		this.buttonRelativeWidth = buttonRelativeWidth;
	}

	public double getButtonRelativeHeight() {
		return buttonRelativeHeight;
	}

	public void setButtonRelativeHeight(double buttonRelativeHeight) {
		this.buttonRelativeHeight = buttonRelativeHeight;
	}

	public UnicornImage getButtonMask() {
		return buttonMask;
	}

	public void setButtonMask(UnicornImage buttonMask) {
		this.buttonMask = buttonMask;
	}

	/**
	 * Image must be inside folder "images" on project root directory.
	 * @param imageName
	 * @throws IOException
	 */
	public void setButtonMask(String buttonName) throws IOException {
		UnicornImage buttonImage = new UnicornImage("images//" + buttonName);
		this.buttonMask = buttonImage;
	}

	public double getPreviousScreenPrecision() {
		return previousScreenPrecision;
	}

	public void setPreviousScreenPrecision(double previousScreenPrecision) {
		this.previousScreenPrecision = previousScreenPrecision;
	}

	public double getNextScreenPrecision() {
		return nextScreenPrecision;
	}

	public void setNextScreenPrecision(double nextScreenPrecision) {
		this.nextScreenPrecision = nextScreenPrecision;
	}

	public double getButtonPrecision() {
		return buttonPrecision;
	}

	public void setButtonPrecision(double buttonPrecision) {
		this.buttonPrecision = buttonPrecision;
	}

	public boolean isSkipPreviousValidation() {
		return skipPreviousValidation;
	}

	public void setSkipPreviousValidation(boolean skipPreviousValidation) {
		this.skipPreviousValidation = skipPreviousValidation;
	}

	public void setSkipPreviousValidation(Integer skipPreviousValidation) {
		boolean skip = false;
		if (skipPreviousValidation == 1) {
			skip = true;
		}
		
		this.skipPreviousValidation = skip;
	}

	public boolean isSkipNextValidation() {
		return skipNextValidation;
	}

	public void setSkipNextValidation(boolean skipNextValidation) {
		this.skipNextValidation = skipNextValidation;
	}

	public void setSkipNextValidation(Integer skipNextValidation) {
		boolean skip = false;
		if (skipNextValidation == 1) {
			skip = true;
		}
		
		this.skipNextValidation = skip;
	}

	public boolean isSkipButtonValidation() {
		return skipButtonValidation;
	}

	public void setSkipButtonValidation(boolean skipButtonValidation) {
		this.skipButtonValidation = skipButtonValidation;
	}

	public void setSkipButtonValidation(Integer skipButtonValidation) {
		boolean skip = false;
		if (skipButtonValidation == 1) {
			skip = true;
		}
		
		this.skipButtonValidation = skip;
	}


	
	
}
