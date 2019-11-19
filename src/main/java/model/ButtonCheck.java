package model;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.io.IOException;

import exception.ButtonNotFoundException;
import exception.GameWindowNotFoundException;
import jna.UnicornWindowControl;
import net.sourceforge.tess4j.TesseractException;

public class ButtonCheck {

	private String name;
	private Double relativeX;
	private Double relativeY;
	private Double relativeWidth;
	private Double relativeHeight;
	private UnicornImage mask;
	private Double precision;
	
	/**
	 * 
	 * @return
	 * @throws GameWindowNotFoundException
	 * @throws AWTException
	 * @throws TesseractException
	 * @throws InterruptedException 
	 */
	public Boolean execute(Integer target, Rectangle targetButtonRectangle) throws GameWindowNotFoundException, AWTException, InterruptedException {
		if (!UnicornWindowControl.isGameRunning()) {
			throw new GameWindowNotFoundException("BlueStacks is not running!");
		}

		UnicornWindowControl.setForegroundWindowByTitle("BlueStacks");

		
		if ( relativeX < 0 ) {
			
			//se x < 0 significa que a tela é scrollável e ele pode estar em qualquer X. � necess�rio uma varredura.
			int scrollCounter = 0;
			boolean buttonFound = false;
			
			//procura pelo bot�o. Se n�o encontrar, tenta scrollar a tela na horizontal 3 vezes. Se mesmo assim n�o encontrar, devolve a exce��o.
			while ( !buttonFound ) {
				try {
					Rectangle buttonRectangle =  UnicornWindowControl.findButtonHorizontal(relativeY, relativeWidth, relativeHeight, mask, precision);
					targetButtonRectangle.x = buttonRectangle.x;
					targetButtonRectangle.y = buttonRectangle.y;
					targetButtonRectangle.width = buttonRectangle.width;
					targetButtonRectangle.height = buttonRectangle.height;
					buttonFound = true;
				} catch (ButtonNotFoundException e) {
					if ( scrollCounter >= 4 ) {
						return false;
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
			try {
				Rectangle buttonRectangle =  UnicornWindowControl.findButton(relativeX, relativeY, relativeWidth, relativeHeight, mask, precision);
			} catch (ButtonNotFoundException e) {
				return false;
			}
		}


		return true;
		
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

	public UnicornImage getMask() {
		return mask;
	}

	public void setMask(UnicornImage mask) {
		this.mask = mask;
	}

	/**
	 * Image must be inside folder "images" on project root directory.
	 * @param imageName
	 * @throws IOException
	 */
	public void setMask(String imageName) throws IOException {
		UnicornImage image = new UnicornImage("images//" + imageName);
		this.mask = image;
	}

	public Double getPrecision() {
		return precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}
	
}
