package test;

import java.sql.SQLException;

public class TesteGui {

	public static void main(String[] args) throws SQLException {
		HomeForm form = new HomeForm();
		GuiConsole.setForm(form);
		
		form.show();
	}
	
}
