package test;

public class GuiConsole {

	private static HomeForm form;
	
	public static void setForm(HomeForm homeForm) {
		form = homeForm;
	}
	
	public static void println(String text) {
		System.out.println(text);
		form.console.append(text + System.lineSeparator());
	}
	
	
}
