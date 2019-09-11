package exception;

public class ButtonNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ButtonNotFoundException(String string) {
		super(string);
	}
}
