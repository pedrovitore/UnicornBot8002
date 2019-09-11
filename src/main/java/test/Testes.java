package test;
import java.awt.Color;
import java.io.FileInputStream;
import java.util.Properties;

import javax.imageio.ImageIO;

public class Testes {

	public static void main(String[] args) {
//		ImageComparator imageComparator = new ImageComparator();
//		//System.out.println(imageComparator.compara("C:\\Users\\Pon\\Documents\\UnicornBot8001\\images\\menu1.png", "C:\\Users\\Pon\\Documents\\UnicornBot8001\\images\\menu2.png"));
//
//		try {
////			BufferedImage imgMenu1 = ImageIO.read(new File("C:\\Users\\Pon\\Documents\\UnicornBot8001\\images\\menu1.png"));
////			Color cor;
////			ImageClusterer imageClusterer = new ImageClusterer();
////			cor = imageClusterer.getAverageColor(imgMenu1);
////			
////			System.out.println(cor);
////			
////			//BufferedImage clusteredImage = imageClusterer.clusterizar(imgMenu1, 50);
////			BufferedImage clusteredImage = new BufferedImage(27, 15, imgMenu1.getType());
////			
////
////			File outputfile = new File("testeeeee.png");
////			//ImageIO.write(clusteredImage, "png", outputfile);
////			
////			// scales the input image to the output image
////	        Graphics2D g2d = clusteredImage.createGraphics();
////	        g2d.drawImage(imgMenu1, 0, 0, 27, 15, null);
////	        g2d.dispose();
////			
////	     // writes to output file
////	        ImageIO.write(clusteredImage, "png", outputfile);
//	        
//			BufferedImage bufferedImgMenu1 = ImageIO.read(new File("C:\\Users\\Pon\\Documents\\UnicornBot8001\\images\\campaign.png"));
//			BufferedImage bufferedImgMenu2 = ImageIO.read(new File("C:\\Users\\Pon\\Documents\\UnicornBot8001\\images\\islandcrusade.png"));
//
//			UnicornImage imgMenu1 = new UnicornImage(bufferedImgMenu1);
//			UnicornImage imgMenu2 = new UnicornImage(bufferedImgMenu2);
//			
//			//imgMenu1 = ((UnicornImage) bufferedImgMenu1);
//			//imgMenu2 = (UnicornImage) bufferedImgMenu2;
//			
//			System.out.println(imgMenu1.compare(imgMenu2));
//	        
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
//			System.out.println(UnicornWindowControl.isRunning("Campo minado"));
//			System.out.println(UnicornWindowControl.getForegroundWindowTitle());
//			
//			UnicornWindowControl.setForegroundWindowByTitle("BlueStacks");
			
			//[x, y, width, height]
			//int[] rect = UnicornWindowControl.getWindowRectangleByTitle("BlueStacks");
			//System.out.println(String.valueOf(rect[0]) + ":" + String.valueOf(rect[1]) + ":" + String.valueOf(rect[2]) + ":" + String.valueOf(rect[3]));
			
//			Properties properties = new Properties();
//			properties.load(new FileInputStream("properties//buttons.properties"));
//			System.out.println(properties.getProperty("testButton"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
