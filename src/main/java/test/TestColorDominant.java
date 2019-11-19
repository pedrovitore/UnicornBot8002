package test;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import model.UnicornImage;
import util.ImageDominantColor;

public class TestColorDominant {

	public static void main(String[] args) throws IOException {
		UnicornImage ocrImage = new UnicornImage("ocr-sample.png");
		ArrayList<Color> colors = ImageDominantColor.getMostCommonColors(ocrImage, 3);

		Color background = colors.get(0);
		Color outline = colors.get(1);
		Color letter = colors.get(2);
		
		
		for (int x = 0; x < ocrImage.getWidth(); x++) {
			for (int y = 0; y < ocrImage.getHeight(); y++) {
				if ( ocrImage.compareColor(ocrImage.getRGB(x, y), outline.getRGB(), ocrImage.HIGH_SENSIBILITY) &&
						!ocrImage.compareColor(ocrImage.getRGB(x, y), letter.getRGB(), ocrImage.HIGH_SENSIBILITY)) {
					ocrImage.setRGB(x, y, background.getRGB());
				}
				if ( ocrImage.compareColor(ocrImage.getRGB(x, y), background.getRGB(), ocrImage.HIGH_SENSIBILITY)) {
					ocrImage.setRGB(x, y, background.getRGB());
				}
			}
		}
		
		ImageIO.write(ocrImage, "png", new File("ocr-removed-outline.png"));
		
		colors.forEach((Color color) -> {
			System.out.println(color.getRed() + ", " + color.getGreen() + ", " + color.getBlue());
		});
	}
	
}
