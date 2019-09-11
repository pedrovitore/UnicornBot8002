package jna;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.*;

import exception.ButtonNotFoundException;
import model.OcrType;
import model.UnicornImage;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import util.OcrFormat;

public class UnicornWindowControl {

	public interface User32 extends StdCallLibrary {
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

		HWND FindWindow(String lpClassName, String lpWindowName);

		int GetWindowRect(HWND handle, int[] rect);

		HWND GetForegroundWindow();

		int GetWindowText(HWND hWnd, char[] lpString, int nMaxCount);

		boolean SetForegroundWindow(HWND hWnd);

		boolean EnumWindows(WinUser.WNDENUMPROC lpEnumFunc, Pointer arg);
	}

	/**
	 * Checks if certain application is running on Windows.
	 * 
	 * @param windowName The name of the desired application.
	 * @return True if program is running. False if not.
	 */
	public static boolean isRunning(String windowName) {
		HWND hwnd = User32.INSTANCE.FindWindow(null, windowName);
		if (hwnd == null) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a String containing the title of the active window.
	 * 
	 * @return
	 */
	public static String getForegroundWindowTitle() {
		// buffer onde ser� gravado o nome da janela ativa
		char[] buffer = new char[2048];

		// comando JNA que pega o t�tulo da janela
		HWND hwnd = User32.INSTANCE.GetForegroundWindow();
		User32.INSTANCE.GetWindowText(hwnd, buffer, 1024);

		// retorna o t�tulo
		return Native.toString(buffer);
	}

	/**
	 * Focuses on window with specified title.
	 * 
	 * @return
	 */
	public static void setForegroundWindowByTitle(final String title) {
		final User32 user32 = User32.INSTANCE;

		// pega todas as janelas abertas e executa uma fun��o an�nima passando a janela
		// da itera��o como par�metro (hWnd, userData n�o sei o que �)
		user32.EnumWindows(new WinUser.WNDENUMPROC() {

			public boolean callback(HWND hWnd, Pointer userData) {
				// buffer onde ser� gravado o nome da janela ativa
				char[] buffer = new char[2048];

				// comando JNA que pega o t�tulo da janela
				user32.GetWindowText(hWnd, buffer, 1024);
				String windowTitle = Native.toString(buffer);

				// compara o t�tulo selecionado e verifica se � a janela desejada, ent�o foca
				if (title.equals(windowTitle)) {
					user32.SetForegroundWindow(hWnd);
				}

				return true;
			}
		}, null);
	}

	/**
	 * 
	 * @param title
	 * @return An int array containing [x, y, width, height].
	 */
	public static Rectangle getWindowRectangleByTitle(String title) {
		HWND hwnd = User32.INSTANCE.FindWindow(null, title);

		if (hwnd == null) {
			throw new RuntimeException("Window not found.");
		}

		int[] rect = { 0, 0, 0, 0 };
		int result = User32.INSTANCE.GetWindowRect(hwnd, rect);
		if (result == 0) {
			throw new RuntimeException("Cant get rect from window.");
		}

		Rectangle rectangle = new Rectangle(rect[0], rect[1], rect[2] - rect[0], rect[3] - rect[1]);
		return rectangle;
	}

	/**
	 * 
	 * @return the Rectangle of BlueStacks.
	 */
	public static Rectangle getGameRectangle() {

		return getWindowRectangleByTitle("BlueStacks");
	}

	/**
	 * 
	 * @return An UnicornImage containing the current game screen. BlueStacks
	 *         template included on the image.
	 * @throws AWTException
	 */
	public static UnicornImage getGameScreen() throws AWTException {
		// capture screen image
		Robot unicornBot = new Robot();
		Rectangle screenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		UnicornImage screenImage = new UnicornImage(unicornBot.createScreenCapture(screenRectangle));

		// get game rectangle and cut game screen into a new image
		Rectangle gameRectangle = getGameRectangle();
		// BlueStacks Template = top: 47, left: 7, bottom: 47, right: 7
		UnicornImage gameImage = new UnicornImage(screenImage.getSubimage(gameRectangle.x + 7, gameRectangle.y + 47,
				gameRectangle.width - 14, gameRectangle.height - 94));

		return gameImage;
	}

	/**
	 * Checks if BlueStacks is running.
	 * 
	 * @return True if is running. False if not.
	 */
	public static boolean isGameRunning() {

		if (!isRunning("BlueStacks")) {
			return false;
		}

		return true;

	}

	/**
	 * 
	 * @param distance Value between 0 and 1. Percentage of the screen to be
	 *                 scrolled.
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public static void scrollHorizontal(double distance) throws AWTException, InterruptedException {
		Rectangle gameRectangle = getGameRectangle();

		int initialX = (int) (((gameRectangle.getMaxX() - gameRectangle.getMinX()) / 2)
				+ (gameRectangle.width * distance / 2));
		int finalX = (int) (((gameRectangle.getMaxX() - gameRectangle.getMinX()) / 2)
				- (gameRectangle.width * distance / 2));
		int y = (int) ((gameRectangle.getMaxY() - gameRectangle.getMinY()) / 2);

		Robot bot = new Robot();
		bot.mouseMove(initialX, y);
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		bot.mouseMove(finalX, y);
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

		// Sleeps 3 seconds after action to wait for scroll delay on android
		TimeUnit.SECONDS.sleep(3);
	}

	/**
	 * Checks if specified button is on expected position.
	 * 
	 * @param yPercent      Ranges from 0 to 1. The y value of the button Rectangle,
	 *                      in percentage relative to game screen height.
	 * @param widthPercent  Ranges from 0 to 1. The width of the button Rectangle,
	 *                      in percentage relative to game screen width.
	 * @param heightPercent Ranges from 0 to 1. The height of the button Rectangle,
	 *                      in percentage relative to game screen height.
	 * @param buttonMask    The image of the in-game art of the button.
	 * @param precision     Ranges from 0 to 1. 1 = 100% equal, 0 = 100% different.
	 * @return The button Rectangle.
	 * @throws AWTException
	 * @throws ButtonNotFoundException
	 */
	public static Rectangle findButtonHorizontal(double yPercent, double widthPercent, double heightPercent,
			UnicornImage buttonMask, double precision) throws AWTException, ButtonNotFoundException {
		UnicornImage gameImage = getGameScreen();

		int buttonWidth = (int) (widthPercent * gameImage.getWidth());
		int buttonHeight = (int) (heightPercent * gameImage.getHeight());
		int buttonY = (int) (yPercent * gameImage.getHeight());

		double highestSimilarity = 0.0;
		int highestX = 0;

		for (int x = 0; x < (gameImage.getWidth() - buttonWidth); x++) {
			UnicornImage buttonImage = new UnicornImage(gameImage.getSubimage(x, buttonY, buttonWidth, buttonHeight));

			double similarity = buttonImage.compare(buttonMask);

			if (similarity > highestSimilarity) {
				highestSimilarity = similarity;
				highestX = x;
			}
		}

		if (highestSimilarity < precision) {
			throw new ButtonNotFoundException("Button not found on current screen.");
		}

		return new Rectangle(highestX, buttonY, buttonWidth, buttonHeight);
	}

	/**
	 * Checks if specified button is on expected position.
	 * 
	 * @param xPercent      Ranges from 0 to 1. The x value of the button Rectangle,
	 *                      in percentage relative to game screen width.
	 * @param yPercent      Ranges from 0 to 1. The y value of the button Rectangle,
	 *                      in percentage relative to game screen height.
	 * @param widthPercent  Ranges from 0 to 1. The width of the button Rectangle,
	 *                      in percentage relative to game screen width.
	 * @param heightPercent Ranges from 0 to 1. The height of the button Rectangle,
	 *                      in percentage relative to game screen height.
	 * @param buttonMask    The image of the in-game art of the button.
	 * @param precision     Ranges from 0 to 1. 1 = 100% equal, 0 = 100% different.
	 * @return The button Rectangle.
	 * @throws AWTException
	 * @throws ButtonNotFoundException
	 */
	public static Rectangle findButton(double xPercent, double yPercent, double widthPercent, double heightPercent,
			UnicornImage buttonMask, double precision) throws AWTException, ButtonNotFoundException {
		UnicornImage gameImage = getGameScreen();

		int buttonWidth = (int) (widthPercent * gameImage.getWidth());
		int buttonHeight = (int) (heightPercent * gameImage.getHeight());
		int buttonX = (int) (xPercent * gameImage.getWidth());
		int buttonY = (int) (yPercent * gameImage.getHeight());

		// buttonMask == null means button has no image validation (button art may vary)
		if (buttonMask == null) {
			return new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
		}
		
		UnicornImage buttonImage = new UnicornImage(gameImage.getSubimage(buttonX, buttonY, buttonWidth, buttonHeight));

		double similarity = buttonImage.compare(buttonMask);

		if (similarity < precision) {
			throw new ButtonNotFoundException("Button not found on current screen.");
		}

		return new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
	}

	/**
	 * Uses OCR to read text on game screen on specified position and converts it to a number.
	 * 
	 * @param xPercent      Ranges from 0 to 1. The x value of the button Rectangle,
	 *                      in percentage relative to game screen width.
	 * @param yPercent      Ranges from 0 to 1. The y value of the button Rectangle,
	 *                      in percentage relative to game screen height.
	 * @param widthPercent  Ranges from 0 to 1. The width of the button Rectangle,
	 *                      in percentage relative to game screen width.
	 * @param heightPercent Ranges from 0 to 1. The height of the button Rectangle,
	 *                      in percentage relative to game screen height.
	 * @param mask    		Text mask to aid finding the right value. Varies according to OcrType:
	 * 
	 * 						NUMBER_SUFFIX: Specify the text that precedes the number. (Ex: Chances: 2, mask = "Chances:")
	 * 						NUMBER_SPLIT: Specify the that splits the number. (Ex: 76/128, mask = "/")
	 * 						NUMBER_PURE: Not needed.
	 * @param Type		    OcrType
	 * @return Field numeric value.
	 * @throws AWTException
	 * @throws ButtonNotFoundException
	 * @throws TesseractException 
	 */
	public static Integer findOcrInteger(double xPercent, double yPercent, double widthPercent, double heightPercent,
			String mask, String type) throws AWTException, TesseractException {
		UnicornImage gameImage = getGameScreen();

		int ocrWidth = (int) (widthPercent * gameImage.getWidth());
		int ocrHeight = (int) (heightPercent * gameImage.getHeight());
		int ocrX = (int) (xPercent * gameImage.getWidth());
		int ocrY = (int) (yPercent * gameImage.getHeight());

		UnicornImage ocrImage = new UnicornImage(gameImage.getSubimage(ocrX, ocrY, ocrWidth, ocrHeight));

		ITesseract instance = new Tesseract();
		String screenText = instance.doOCR(ocrImage);
		Integer number = OcrFormat.formatNumber(screenText, mask, type);
		
		//ainda falta implementar a leitura de texto.
		
		return number;
	}

	/**
	 * Clicks on specified button.
	 * 
	 * @param buttonRectangle The in-game button rectangle.
	 * @throws AWTException
	 */
	public static void clickOnButton(Rectangle buttonRectangle) throws AWTException {
		Rectangle gameRectangle = UnicornWindowControl.getGameRectangle();

		// BlueStacks template: top,bottom: 47, left,right: 7
		Rectangle clickableArea = new Rectangle(gameRectangle.x + buttonRectangle.x + 7,
				gameRectangle.y + buttonRectangle.y + 47, buttonRectangle.width, buttonRectangle.height);

		Robot bot = new Robot();
		bot.mouseMove(clickableArea.x + (clickableArea.width / 2), clickableArea.y + (clickableArea.height / 2));
		bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}

}
