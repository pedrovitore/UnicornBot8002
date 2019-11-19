package model;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import test.GuiConsole;
public class UnicornImage extends java.awt.image.BufferedImage {

	public double HIGH_SENSIBILITY = 100.0;
	public double LOW_SENSIBILITY = 20.0;
	public double ZERO_SENSIBILITY = 0.0;
	
	public UnicornImage(int width, int height, int imageType) {
		super(width, height, imageType);
	}

	public UnicornImage(BufferedImage image) {
		super(image.getWidth(), image.getHeight(), image.getType());
				
		Graphics2D g2d = this.createGraphics();
        g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g2d.dispose();
	}

	public UnicornImage(String imagePath) throws IOException {
		super(ImageIO.read(new File(imagePath)).getWidth(), ImageIO.read(new File(imagePath)).getHeight(), ImageIO.read(new File(imagePath)).getType());
		
		BufferedImage bufferedImage = ImageIO.read(new File(imagePath));

		Graphics2D g2d = this.createGraphics();
        g2d.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        g2d.dispose();
	}

	/**
	 * 
	 * @param	scaleWidth	The width of the resized image.
	 * @param	scaleHeight	The height of the resized image.
	 * @return	a UnicornImage of specified size.
	 */
	public UnicornImage resize(int scaleWidth, int scaleHeight) {
		UnicornImage resizedImage = new UnicornImage(scaleWidth, scaleHeight, this.getType());
		
		// scales the input image to the output image
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(this, 0, 0, scaleWidth, scaleHeight, null);
        g2d.dispose();

        return resizedImage;
	}
	
	/**
	 * Compara com a imagem atual e retorna a porcentagem de similaridade entre as duas imagens.
	 * @param mask A m�scara a ser comparada com a imagem atual
	 * @return um double com a porcentagem de similaridade
	 */
	public double compare(UnicornImage mask) {
		try {
			//carrega as duas imagens a se comparar
			//BufferedImage imgMenu1 = ImageIO.read(new File(imgPath1));
			//BufferedImage imgMenu2 = ImageIO.read(new File(imgPath2));
			
			//redimensiona a imagem do par�metro para ser do mesmo tamanho que esta imagem
			BufferedImage resizedMask = mask.resize(this.getWidth(), this.getHeight());
			
			//esses contadores ser�o utilizados para contar a porcentagem de afinidade
			int contadorPixelMesmaCor = 0;
			int contadorPixelTotal = 0;
			
			//repassa pixel por pixel para verificar se s�o da mesma cor
			for (int coordX = 0; coordX < this.getWidth(); coordX++) {
				for (int coordY = 0; coordY < this.getHeight(); coordY++) {
					//Se o pixel da m�scara for verde puro significa que esse pixel n�o deve ser comparado
					if ( compareColor(resizedMask.getRGB(coordX, coordY), Color.GREEN.getRGB(), ZERO_SENSIBILITY) ) {
						continue;
					}
					
					if ( compareColor(this.getRGB(coordX, coordY), resizedMask.getRGB(coordX, coordY), HIGH_SENSIBILITY) ) {
						contadorPixelMesmaCor++;
					}
					contadorPixelTotal++;
					continue;
				}
			}

			//TEST MODE - uncomment to save image on disk for debugging
			//WARNING: VERY SLOW
//			try {
//				GuiConsole.println("TEST MODE: Button image comparation saved");
//				ImageIO.write(this, "png", new File("compare-screen.png"));
//				ImageIO.write(resizedMask, "png", new File("compare-mask.png"));
//			} catch (Exception e) {
//				GuiConsole.println("TEST MODE: Failed to save comparation images");
//			}
			
			
			//Exibe a porcentagem de afinidade
			double porcentagemAfinidade = (double) contadorPixelMesmaCor / (double) contadorPixelTotal;
			GuiConsole.println(contadorPixelMesmaCor + " " + contadorPixelTotal + " (" + porcentagemAfinidade + "%)");
			return porcentagemAfinidade;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.0;
	}

	public void transformToGrayscale() {
		try {
			//repassa pixel por pixel e altera cor
			for (int coordX = 0; coordX < this.getWidth(); coordX++) {
				for (int coordY = 0; coordY < this.getHeight(); coordY++) {
					int color = this.getRGB(coordX, coordY);
					color = getColorGrayScale(color);
					this.setRGB(coordX, coordY, color);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *  Returns a boolean value indicating if the color is similar or not. Uses Euclidean distance to determine color difference. 
	 * 
	 * @param pixel1		The first color to be compared.
	 * @param pixel2		The second color to be compared.
	 * @param sensibility	Ranges from 0 to 441.673. 0 = 100% similar, 441.673 = 100% different.
	 * @return True if color is similar, false if color is different.  
	 */
	public boolean compareColor(int color1, int color2, double sensibility) {
		Color c1 = new Color(color1);
		Color c2 = new Color(color2);
		
		//gets each color channel individually
		long r1 = (long) c1.getRed();
		long g1 = (long) c1.getGreen();
		long b1 = (long) c1.getBlue();
		long r2 = (long) c2.getRed();
		long g2 = (long) c2.getGreen();
		long b2 = (long) c2.getBlue();
		
		//euclidean formula
		double distance = Math.sqrt(Math.pow(r1-r2, 2) + Math.pow(g1-g2, 2) + Math.pow(b1-b2, 2));

		if (distance > sensibility) {
			return false;
		}
		
		return true;
	}
	

	
	/**
	 *  Returns specified color in grayscale
	 */
	private int getColorGrayScale(int color) {
		Color c1 = new Color(color);
		
		//gets each color channel individually
		int r = c1.getRed();
		int g = c1.getGreen();
		int b = c1.getBlue();
		
		//gets color average (gray)
		int av = (r+g+b)/3;
		
		Color gray = new Color(av, av, av);
		
		return gray.getRGB();
	}
	

	/**
	 * Gets pixel count by color.
	 * @param image	The image to scan
	 * @return
	 */
    public Map<Integer, Integer> getPixelCount() {

        Map<Integer, Integer> colorMap = new HashMap<>();
        int height = this.getHeight();
        int width = this.getWidth();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = this.getRGB(i, j);
                
                //no need to remove gray pixels in this case
                //if (!isGray(getRGBArr(rgb))) {
                Integer counter = colorMap.get(rgb);
                if (counter == null) {
                    counter = 0;
                }

                colorMap.put(rgb, ++counter);
                //}
            }
        }

        return colorMap;
    }

    
    /**
     * Scans given image and returns a list with the most common colors in it.
     * @param image		The image for scanning.
     * @param limit		The number of colors to return.
     * @return
     */
    public ArrayList<Color> getMostCommonColors(Integer limit) {
    	Map<Integer, Integer> map = getPixelCount();
    	
        return getMostCommonColors(map, limit);
    }
    
    /**
     * 
     * @param map
     * @param limit		The number of colors to return.
     * @return
     */
    private ArrayList<Color> getMostCommonColors(Map<Integer, Integer> map, Integer limit) {
        List<Map.Entry<Integer, Integer>> list = new LinkedList<>(map.entrySet());

        Collections.sort(list, (Map.Entry<Integer, Integer> obj1, Map.Entry<Integer, Integer> obj2)
                -> ((Comparable) obj1.getValue()).compareTo(obj2.getValue()));

        ArrayList<Color> colors = new ArrayList<>();
        for ( int i = 1; i <= limit && i < list.size(); i++ ) {
            Map.Entry<Integer, Integer> entry = list.get(list.size() - i);
            
        	colors.add(new Color(entry.getKey()));
        }

        return colors;
    }
	
}
