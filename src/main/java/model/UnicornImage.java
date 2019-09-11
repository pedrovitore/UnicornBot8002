package model;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class UnicornImage extends java.awt.image.BufferedImage {

	private double HIGH_SENSIBILITY = 100.0;
	private double LOW_SENSIBILITY = 20.0;
	private double ZERO_SENSIBILITY = 0.0;
	
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
	 * @param mask A máscara a ser comparada com a imagem atual
	 * @return um double com a porcentagem de similaridade
	 */
	public double compare(UnicornImage mask) {
		try {
			//carrega as duas imagens a se comparar
			//BufferedImage imgMenu1 = ImageIO.read(new File(imgPath1));
			//BufferedImage imgMenu2 = ImageIO.read(new File(imgPath2));
			
			//redimensiona a imagem do parâmetro para ser do mesmo tamanho que esta imagem
			BufferedImage resizedMask = mask.resize(this.getWidth(), this.getHeight());
			
			//esses contadores serão utilizados para contar a porcentagem de afinidade
			int contadorPixelMesmaCor = 0;
			int contadorPixelTotal = 0;
			
			//repassa pixel por pixel para verificar se são da mesma cor
			for (int coordX = 0; coordX < this.getWidth(); coordX++) {
				for (int coordY = 0; coordY < this.getHeight(); coordY++) {
					//Se o pixel da máscara for verde puro significa que esse pixel não deve ser comparado
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
			
			//Exibe a porcentagem de afinidade
			double porcentagemAfinidade = (double) contadorPixelMesmaCor / (double) contadorPixelTotal;
			System.out.println(contadorPixelMesmaCor + " " + contadorPixelTotal);
			return porcentagemAfinidade;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.0;
	}
	
	/**
	 *  Returns a boolean value indicating if the color is similar or not. Uses Euclidean distance to determine color difference. 
	 * 
	 * @param pixel1		The first color to be compared.
	 * @param pixel2		The second color to be compared.
	 * @param sensibility	Ranges from 0 to 441.673. 0 = 100% similar, 441.673 = 100% different.
	 * @return True if color is similar, false if color is different.  
	 */
	private boolean compareColor(int color1, int color2, double sensibility) {
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
	
}
