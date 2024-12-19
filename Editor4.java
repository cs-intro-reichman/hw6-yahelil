import java.awt.Color;

/**
 * Morphs a colored image into its grayscale version.
 * The program receives two command-line arguments: 
 * 1. A string representing the name of the PPM file of the source image
 * 2. An integer specifying the number of morphing steps
 * 
 * For example, to transform thor.ppm into its grayscale version in 50 steps, use:
 * java Editor4 thor.ppm 50
 */
public class Editor4 {
    public static void main(String[] args) {
        String source = args[0];
        int n = Integer.parseInt(args[1]);
        
        Color[][] sourceImage = Runigram.read(source);
        Runigram.setCanvas(sourceImage);
        Color[][] targetImage = Runigram.grayScaled(sourceImage);
        Runigram.morph(sourceImage, targetImage, n);
    }
}
