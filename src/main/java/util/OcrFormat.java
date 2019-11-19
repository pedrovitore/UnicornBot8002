package util;

import java.awt.Color;
import java.util.ArrayList;

import model.OcrType;
import model.UnicornImage;

public class OcrFormat {

	public static Integer formatNumber(String text, String mask, String type) {
		System.out.println("Screen text - " + text);
		
		if (type.equals(OcrType.NUMBER_PURE.toString())) {
			text = text.replace(",", "").replace("K", "000").trim();
		}

		if (type.equals(OcrType.NUMBER_SUFFIX.toString())) {
			text = text.replace(mask, "").trim();
		}

		if (type.equals(OcrType.NUMBER_SPLIT.toString())) {
			text = text.split("/")[0].trim();
		}
		
		return Integer.parseInt(text);
	}

	public static String formatText(String text, String mask, String type) {
		if (type.equals(OcrType.TEXT.toString())) {
			//TEXT only reads plain text. Do nothing.
			//Use this funcion in case for possible future text types.
		}
		
		return text;
	}
	
	public static void removeOutlineFromText(UnicornImage image) {
		ArrayList<Color> colors = ImageDominantColor.getMostCommonColors(image, 3);

		Color background = colors.get(0);
		Color outline = colors.get(1);
		Color letter = colors.get(2);
		
		
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if ( image.compareColor(image.getRGB(x, y), outline.getRGB(), image.HIGH_SENSIBILITY) &&
						!image.compareColor(image.getRGB(x, y), letter.getRGB(), image.HIGH_SENSIBILITY)) {
					image.setRGB(x, y, background.getRGB());
				}
				if ( image.compareColor(image.getRGB(x, y), background.getRGB(), image.HIGH_SENSIBILITY)) {
					image.setRGB(x, y, background.getRGB());
				}
			}
		}
	}
	
	public static void removeTextOutOfBounds(UnicornImage image) {
		ArrayList<Color> colors = ImageDominantColor.getMostCommonColors(image, 1);

		Color background = colors.get(0);
		
		
		for (int y = 0; y < image.getHeight(); y++) {
			Integer backgroundCount = 0;
			Integer totalCount = 0;
			
			
			for (int x = 0; x < image.getWidth(); x++) {
				totalCount++;
				if ( image.compareColor(image.getRGB(x, y), background.getRGB(), image.HIGH_SENSIBILITY) ) {
					backgroundCount++;
				}
			}
			
			Double backgroundPercent = backgroundCount.doubleValue() / totalCount.doubleValue();
			if ( backgroundPercent >= 0.95 ) {
				for (int x = 0; x < image.getWidth(); x++) {
					image.setRGB(x, y, background.getRGB());
				}
			}
		}
		
		
		
	}

}
