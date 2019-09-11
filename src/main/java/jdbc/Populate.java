package jdbc;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;

import model.Action;
import model.Button;
import model.OcrCheck;

public class Populate {

	public static void main(String[] args) {
		//buttonMapFromCsv("button-map.csv");
		//actionsFromCsv("actions.csv");
		ocrMapFromCsv("ocr-map.csv");
		
		System.out.println("Concluído");
	}
	
	public static void buttonMapFromCsv(String filePath) {
		try {
			Scanner scanner = new Scanner(new File(filePath));
			scanner.useLocale(Locale.US);
			scanner.useDelimiter(";|\r\n|\n");
			
//			0 Name;
//			1 x;
//			2 y;
//			3 width;
//			4 height;
//			5 gameWidth;
//			6 gameHeight;
//			7 xPercent;
//			8 yPercent;
//			9 widthPercent;
//			10 heightPercent;
//			11 File Name
			if (scanner.hasNext()) {
				scanner.nextLine(); //skip header
				
				while (scanner.hasNext()) {
					Button button = new Button();
					button.setName(scanner.next().trim());
					scanner.next(); //skip x pixel
					scanner.next(); //skip y pixel
					scanner.next(); //skip width pixel
					scanner.next(); //skip height pixel
					scanner.next(); //skip gameWidth
					scanner.next(); //skip gameHeight
					button.setX(scanner.nextDouble());
					button.setY(scanner.nextDouble());
					button.setWidth(scanner.nextDouble());
					button.setHeight(scanner.nextDouble());
					button.setFileName(scanner.next().trim());
					
					Database.addButtonMap(button);
				}
			}
			
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro lendo CSV");
		}
	}
	
	public static void actionsFromCsv(String filePath) {
		try {
			Scanner scanner = new Scanner(new File(filePath));
			scanner.useLocale(Locale.US);
			scanner.useDelimiter(";|\\r\\n|\\n");
			
//			0 Name;
//			1 prev_screen;
//			2 next_screen;
//			3 button_name;
//			4 prev_precision;
//			5 next_precision;
//			6 button_precision;
//			7 prev_skip;
//			8 next_skip;
//			9 button_skip;
			if (scanner.hasNext()) {
				scanner.nextLine(); //skip header
				
				while (scanner.hasNext()) {
					Action action = new Action();
					action.setName(scanner.next().trim());
					action.setPrevScreen(scanner.next().trim());
					action.setNextScreen(scanner.next().trim());
					action.setButtonName(scanner.next().trim());
					action.setPrevPrecision(scanner.nextDouble());
					action.setNextPrecision(scanner.nextDouble());
					action.setButtonPrecision(scanner.nextDouble());
					action.setPrevSkip(scanner.nextInt());
					action.setNextSkip(scanner.nextInt());
					action.setButtonSkip(scanner.nextInt());
					
					Database.addAction(action);
				}
			}
			
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro lendo CSV");
		}
	}
	
	public static void ocrMapFromCsv(String filePath) {
		try {
			Scanner scanner = new Scanner(new File(filePath));
			scanner.useLocale(Locale.US);
			scanner.useDelimiter(";|\r\n|\n");
			
//			0 Name;
//			1 x;
//			2 y;
//			3 width;
//			4 height;
//			5 gameWidth;
//			6 gameHeight;
//			7 xPercent;
//			8 yPercent;
//			9 widthPercent;
//			10 heightPercent;
//			11 Mask
//			12 Type	
//			13 Comparator
			if (scanner.hasNext()) {
				scanner.nextLine(); //skip header
				
				while (scanner.hasNext()) {
					OcrCheck ocr = new OcrCheck();
					ocr.setName(scanner.next().trim());
					scanner.next(); //skip x pixel
					scanner.next(); //skip y pixel
					scanner.next(); //skip width pixel
					scanner.next(); //skip height pixel
					scanner.next(); //skip gameWidth
					scanner.next(); //skip gameHeight
					ocr.setRelativeX(scanner.nextDouble());
					ocr.setRelativeY(scanner.nextDouble());
					ocr.setRelativeWidth(scanner.nextDouble());
					ocr.setRelativeHeight(scanner.nextDouble());
					ocr.setMask(scanner.next());
					ocr.setOcrType(scanner.next());
					ocr.setOcrComparator(scanner.next());
					
					Database.addOcrMap(ocr);
				}
			}
			
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro lendo CSV");
		}
	}
	
}
