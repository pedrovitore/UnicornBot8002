package test;

import java.awt.image.BufferedImage;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TestTess4J {
	
	public static String lerImagem(BufferedImage image) throws TesseractException {
		System.out.println(System.getenv("TESSDATA_PREFIX"));
		ITesseract instance = new Tesseract();
		//instance.setDatapath("C:\\Users\\Pon\\tessdata");
		String text = instance.doOCR(image);
		
		return text;
	}
}
