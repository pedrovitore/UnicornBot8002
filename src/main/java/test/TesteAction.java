package test;
import jna.UnicornWindowControl;
import model.NavigationAction;
import model.UnicornImage;

public class TesteAction {

	
	public static void main(String[] args) throws InterruptedException {
//		openGoldPurchaseScreen();
//		buyGold();
//		
//		TimeUnit.MICROSECONDS.sleep(10);
//
//		buyGold();
		
		openCrystalDungeon();
		//openCrystalDungeonSweep();
	}

	public static void openCrystalDungeon() {
		try {
			UnicornWindowControl.setForegroundWindowByTitle("BlueStacks");
			
			UnicornImage previousImage = new UnicornImage("images//screen-home.png");
			UnicornImage nextImage = new UnicornImage("images//screen-crystaldungeon.png");
			UnicornImage buttonImage = new UnicornImage("images//button-crystaldungeon.png");
			
			System.out.println(TestTess4J.lerImagem(buttonImage));
			System.exit(0);//apagar
			//NavigationAction crystalDungeonAction = new NavigationAction(previousImage, nextImage, -1, 0.5372, 0.1669, 0.2752, buttonImage, 0.8);

			//crystalDungeonAction.execute();
			
			System.out.println("Ação concluída com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void openCrystalDungeonSweep() {
		try {
			UnicornWindowControl.setForegroundWindowByTitle("BlueStacks");
			
			UnicornImage previousImage = new UnicornImage("images//screen-crystaldungeon.png");
			UnicornImage nextImage = new UnicornImage("images//screen-crystaldungeonsweep.png");
			UnicornImage buttonImage = new UnicornImage("images//button-opencrystaldungeonsweep.png");
			
			//NavigationAction crystalDungeonAction = new NavigationAction(previousImage, nextImage, 0.2874, 0.8484, 0.0830, 0.1449, buttonImage, 0.8);

			//crystalDungeonAction.execute();
			
			System.out.println("Ação concluída com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void openGoldPurchaseScreen() {
		try {
			UnicornWindowControl.setForegroundWindowByTitle("BlueStacks");
			
			UnicornImage previousImage = new UnicornImage("images//screen-home.png");
			UnicornImage nextImage = new UnicornImage("images//screen-goldpurchase.png");
			UnicornImage buttonImage = new UnicornImage("images//button-opengoldpuchase.png");
			
			//NavigationAction openGoldPurchaseScreenAction = new NavigationAction(previousImage, nextImage, 0.5486, 0.0212, 0.0396, 0.0718, buttonImage, 0.8);

			//openGoldPurchaseScreenAction.execute();
			
			System.out.println("Ação concluída com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void buyGold() {
		try {
			UnicornWindowControl.setForegroundWindowByTitle("BlueStacks");
			
			UnicornImage previousImage = new UnicornImage("images//screen-goldpurchase.png");
			UnicornImage nextImage = new UnicornImage("images//screen-goldpurchase.png");
			UnicornImage buttonImage = new UnicornImage("images//button-buygold.png");
			
			//NavigationAction buyGoldAction = new NavigationAction(previousImage, nextImage, 0.1010, 0.6329, 0.1923, 0.0797, buttonImage, 0.8);

			//buyGoldAction.execute();
			
			System.out.println("Ação concluída com sucesso");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

//	public static void sweepCrystalDungeon() {
//		try {
//			UnicornWindowControl.setForegroundWindowByTitle("BlueStacks");
//			
//			BufferedImage previousBufferedImage = ImageIO.read(new File("images//screen-home.png"));
//			UnicornImage previousImage = new UnicornImage(previousBufferedImage);
//			
//			BufferedImage nextBufferedImage = ImageIO.read(new File("images//screen-crystaldungeon.png"));
//			UnicornImage nextImage = new UnicornImage(nextBufferedImage);
//			
//			int buttonY = (int) 0.296 * 
//			
//			Button button = new Button(new Rectangle(0, 462, 259, 228), "images//button-crystaldungeon.png");
//			
//			NavigationAction crystalDungeonAction = new NavigationAction(previousImage, button, nextImage, 0.95, 0.95, 0.95);
//
//			
//			System.out.println(crystalDungeonAction.findButtonHorizontal());
//			
//			// find button est� retornando o pixel com sucesso, s� ainda n�o compara com a taxa de 95%. implementar agora a rotina de clique
//			
//			System.out.println("A��o conclu�da com sucesso");
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (AWTException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
