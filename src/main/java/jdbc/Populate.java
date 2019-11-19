package jdbc;

import java.io.File;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

import dao.ButtonCheckDAO;
import dao.ClickActionDAO;
import dao.OcrCheckDAO;
import dao.RequirementDAO;
import dao.RoutineDAO;
import model.ButtonCheck;
import model.ClickAction;
import model.OcrCheck;
import model.Requirement;
import model.Routine;
import model.RoutineAction;

public class Populate {

	public static void main(String[] args) throws SQLException {
		clearDb();
		
		ocrCheckFromCsv("OcrCheck.csv");
		buttonCheckFromCsv("ButtonCheck.csv");
		actionFromCsv("Actions.csv");
		routineFromCsv("Routines.csv");
		routineActionFromCsv("Routines_actions.csv");
		requirementFromCsv("Requirements.csv");
		
		System.out.println("Concluído");
	}
	
	private static void clearDb() throws SQLException {
		RoutineDAO.clearTableAndChildren();
		ClickActionDAO.clearTable();
		ButtonCheckDAO.clearTable();
		OcrCheckDAO.clearTable();
		RequirementDAO.clearTable();
	}

	public static void buttonCheckFromCsv(String filePath) {
		try {
			Scanner scanner = new Scanner(new File(filePath));
			scanner.useLocale(Locale.US);
			scanner.useDelimiter(",|;|\r\n|\n");
			
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
//			12 Precision
			if (scanner.hasNext()) {
				scanner.nextLine(); //skip header
				
				while (scanner.hasNext()) {
					ButtonCheck button = new ButtonCheck();
					String filename;
					button.setName(scanner.next().trim());
					scanner.next(); //skip x pixel
					scanner.next(); //skip y pixel
					scanner.next(); //skip width pixel
					scanner.next(); //skip height pixel
					scanner.next(); //skip gameWidth
					scanner.next(); //skip gameHeight
					button.setRelativeX(scanner.nextDouble());
					button.setRelativeY(scanner.nextDouble());
					button.setRelativeWidth(scanner.nextDouble());
					button.setRelativeHeight(scanner.nextDouble());
					filename = scanner.next().trim();
					button.setPrecision(scanner.nextDouble());
					
					ButtonCheckDAO.addButtonCheck(button, filename);
				}
			}
			
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro lendo CSV: " + filePath);
		}
	}
	
	public static void ocrCheckFromCsv(String filePath) {
		try {
			Scanner scanner = new Scanner(new File(filePath));
			scanner.useLocale(Locale.US);
			scanner.useDelimiter(",|;|\r\n|\n");
			
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
//			14 Outlined
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
					ocr.setOutlined(scanner.nextInt());
					
					OcrCheckDAO.addOcrCheck(ocr);
				}
			}
			
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro lendo CSV: " + filePath);
		}
	}
	
	public static void actionFromCsv(String filePath) {
		try {
			Scanner scanner = new Scanner(new File(filePath));
			scanner.useLocale(Locale.US);
			scanner.useDelimiter(",|;|\r\n|\n");

//			0 Type;
//			1 Name;
//			2 x;
//			3 y;
//			4 width;
//			5 height;
//			6 gameWidth;
//			7 gameHeight;
//			8 xPercent;
//			9 yPercent;
//			10 widthPercent;
//			11 heightPercent;
//			12 mandatory;
			if (scanner.hasNext()) {
				scanner.nextLine(); //skip header
				
				while (scanner.hasNext()) {
					String type = scanner.next();
					
					ClickAction action = new ClickAction();
					action.setName(scanner.next().trim());
					scanner.next(); //skip x pixel
					scanner.next(); //skip y pixel
					scanner.next(); //skip width pixel
					scanner.next(); //skip height pixel
					scanner.next(); //skip gameWidth
					scanner.next(); //skip gameHeight
					action.setRelativeX(scanner.nextDouble());
					action.setRelativeY(scanner.nextDouble());
					action.setRelativeWidth(scanner.nextDouble());
					action.setRelativeHeight(scanner.nextDouble());
					action.setMandatory((Integer) scanner.nextInt());
					
					ClickActionDAO.addClickAction(action, type);
				}
			}
			
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro lendo CSV: " + filePath);
		}
	}
	
	public static void routineActionFromCsv(String filePath) {
		try {
			Scanner scanner = new Scanner(new File(filePath));
			scanner.useLocale(Locale.US);
			scanner.useDelimiter(",|;|\r\n|\n");
			
//			0 Routine Name;
//			1 Action Name;
//			2 Action Order;
			if (scanner.hasNext()) {
				scanner.nextLine(); //skip header
				
				while (scanner.hasNext()) {
					RoutineAction routineAction = new RoutineAction();
					routineAction.setRoutineName(scanner.next().trim());
					routineAction.setActionName(scanner.next().trim());
					routineAction.setActionOrder(scanner.nextInt());
					
					RoutineDAO.addAction(routineAction);
				}
			}
			
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro lendo CSV: " + filePath);
		}
	}
	
	public static void routineFromCsv(String filePath) {
		try {
			Scanner scanner = new Scanner(new File(filePath));
			scanner.useLocale(Locale.US);
			scanner.useDelimiter(",|;|\r\n|\n");
			
//			0 Routine Name;
			if (scanner.hasNext()) {
				scanner.nextLine(); //skip header
				
				while (scanner.hasNext()) {
					Routine routine = new Routine();
					routine.setName(scanner.next().trim());
					
					RoutineDAO.addRoutine(routine);
				}
			}
			
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro lendo CSV: " + filePath);
		}
	}
	
	public static void requirementFromCsv(String filePath) {
		try {
			Scanner scanner = new Scanner(new File(filePath));
			scanner.useLocale(Locale.US);
			scanner.useDelimiter(",|;|\r\n|\n");
			
//			0 Action Name;
//			1 Check Name;
//			2 Check type;
//			3 Value;
			if (scanner.hasNext()) {
				scanner.nextLine(); //skip header
				
				while (scanner.hasNext()) {
					Requirement requirement = new Requirement();
					requirement.setActionName(scanner.next().trim());
					requirement.setCheckName(scanner.next().trim());
					requirement.setCheckType(scanner.next().trim());
					requirement.setValue(scanner.nextInt());
					
					RequirementDAO.addRequirement(requirement);
				}
			}
			
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Erro lendo CSV: " + filePath);
		}
	}
	
}
