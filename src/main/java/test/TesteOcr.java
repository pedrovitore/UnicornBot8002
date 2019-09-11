package test;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.OcrMapDAO;
import exception.GameWindowNotFoundException;
import model.OcrCheck;
import net.sourceforge.tess4j.TesseractException;

public class TesteOcr {

	public static void main(String[] args) throws SQLException, IOException, GameWindowNotFoundException, AWTException, TesseractException {
//		OcrCheck ocr = OcrMapDAO.getOcrCheck("Stamina");
//		
//		Integer stamina = ocr.execute();
//		
//		List<List> asifhgasiufh = new ArrayList<List>();
//		Criar uma interface chamada "executable" com o procedimento "execute", e a lista da rotina será então composta desses elementos ao invés da NavAct direto
//		
//		System.out.println(stamina.toString());
		
		OcrCheck ocr = OcrMapDAO.getOcrCheck("Stamina");
		
		Boolean success = ocr.execute(60);
		
		System.out.println(success.toString());
	}
	
}
