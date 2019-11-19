package test;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import exception.GameWindowNotFoundException;
import model.UnicornImage;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import util.OcrFormat;

public class TesteOcr {

	public static void main(String[] args) throws SQLException, IOException, GameWindowNotFoundException, AWTException, TesseractException {

		ITesseract instance = new Tesseract();
		UnicornImage ocrImage = new UnicornImage("test-russian-ocr.png");
		OcrFormat.removeOutlineFromText(ocrImage);
		//OcrFormat.removeTextOutOfBounds(ocrImage);
		//ocrImage.transformToGrayscale();
		String screenText = instance.doOCR(ocrImage);
		ImageIO.write(ocrImage, "png", new File("output.png"));
		System.out.println(screenText);
		
		
	}
	
	
}
