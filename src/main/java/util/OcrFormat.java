package util;

import model.OcrType;

public class OcrFormat {

	public static Integer formatNumber(String text, String mask, String type) {
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

}
