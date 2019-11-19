package util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Pedro Vitore - forked from jittagornp (https://gist.github.com/jittagornp/6c7fcdab388fe4863c34)
 */
public class ImageDominantColor {

	/**
	 * Gets pixel count by color.
	 * @param image	The image to scan
	 * @return
	 */
    public static Map<Integer, Integer> getPixelCount(BufferedImage image) {

        Map<Integer, Integer> colorMap = new HashMap<>();
        int height = image.getHeight();
        int width = image.getWidth();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);
                
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
    public static ArrayList<Color> getMostCommonColors(BufferedImage image, Integer limit) {
    	Map<Integer, Integer> map = getPixelCount(image);
    	
        return getMostCommonColors(map, limit);
    }
    
    /**
     * 
     * @param map
     * @param limit		The number of colors to return.
     * @return
     */
    private static ArrayList<Color> getMostCommonColors(Map<Integer, Integer> map, Integer limit) {
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

    private static int[] getRGBArr(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        
        return new int[]{red, green, blue};
    }

    private static boolean isGray(int[] rgbArr) {
        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];
        // Filter out black, white and grays...... (tolerance within 10 pixels)
        int tolerance = 10;
        if (rgDiff > tolerance || rgDiff < -tolerance) {
            if (rbDiff > tolerance || rbDiff < -tolerance) {
                return false;
            }
        }
        return true;
    }
}
