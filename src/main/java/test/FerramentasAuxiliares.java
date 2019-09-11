package test;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jna.UnicornWindowControl;
import model.UnicornImage;

public class FerramentasAuxiliares {

	public static void main(String[] args) throws AWTException, IOException {
		gravarTelaAtual("exemploTelaAtual.png");
	}
	
	public static void gravarTelaAtual(String imagePath) throws AWTException, IOException {
		UnicornWindowControl.setForegroundWindowByTitle("BlueStacks");
		
		UnicornImage telaAtual = UnicornWindowControl.getGameScreen();
		
		ImageIO.write(telaAtual, "png", new File(imagePath));
	}
	
}
