import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.

		/*
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
		*/
	}
	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file into the image array. 
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				int red = in.readInt();
				int green = in.readInt();
				int blue = in.readInt();
				image[i][j] = new Color(red, green, blue);
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		//// Replace this comment with your code
		//// Notice that all you have to so is print every element (i,j) of the array using the print(Color) function.
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		int rows = image.length;
		int cols = image[0].length;
		Color[][] flippedImage = new Color[rows][cols];

		// Reverse the rows
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				flippedImage[i][j] = image[i][cols - j - 1];
			}
		}
		return flippedImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		int rows = image.length;
		int cols = image[0].length;
		Color[][] flippedImage = new Color[rows][cols];

		// Reverse the rows
		for (int i = 0; i < rows; i++) {
			flippedImage[i] = image[rows - i - 1];
		}
		return flippedImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	private static Color luminance(Color pixel) {
		double new_red = 0.299*pixel.getRed();
		double new_green = 0.587*pixel.getGreen();
		double new_blue = 0.114*pixel.getBlue();
		int greyValue = (int)(new_red + new_green + new_blue);
		return new Color(greyValue, greyValue, greyValue);
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color[][] grey_image = image;
		int image_row = image.length;
		int image_col = image[0].length;

		for(int i = 0; i < image_row; i++){
			for(int j = 0; j < image_col; j++){
				grey_image[i][j] = luminance(grey_image[i][j]);
			}
		}
		return grey_image;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] scaledImage = new Color[height][width];
		double rowScale = (double) image.length / height;
		double colScale = (double) image[0].length / width;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Calculate the corresponding position in the original image
				int sourceRow = (int) (i * rowScale);
				int sourceCol = (int) (j * colScale);
				
				// Ensure we don't exceed the bounds of the source image
				sourceRow = Math.min(sourceRow, image.length - 1);
				sourceCol = Math.min(sourceCol, image[0].length - 1);
				
				// Copy the color from the source to the scaled image
				scaledImage[i][j] = image[sourceRow][sourceCol];
			}
		}
		return scaledImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int red1 = c1.getRed();
		int green1 = c1.getGreen();
		int blue1 = c1.getBlue();
		
		int red2 = c2.getRed();
		int green2 = c2.getGreen();
		int blue2 = c2.getBlue();

		int new_red = (int) (alpha * red1 + (1-alpha)*red2);
		int new_green = (int) (alpha * green1 + (1-alpha)*green2);
		int new_blue = (int) (alpha * blue1 + (1-alpha)*blue2);

		return new Color(new_red,new_green,new_blue);
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
    int rows = image1.length;
    int cols = image1[0].length;
    Color[][] blendedImage = new Color[rows][cols];
    
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            Color c1 = image1[i][j];
            Color c2 = image2[i][j];
            
            blendedImage[i][j] = blend(c1, c2, alpha);
        }
    }
    
    return blendedImage;
}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		int rows = source.length;
		int cols = source[0].length;
		
		// Scale target image if dimensions don't match
		Color[][] scaledTarget = target;
		if (rows != target.length || cols != target[0].length) {
			scaledTarget = scaled(target, cols, rows);
		}
		
		// For each step i, blend with alpha = (n-i)/n
		for (int i = 0; i <= n; i++) {  // Note: <= n to include the final state
			// Calculate alpha for this step
			double alpha = (double)(n - i) / n;
			
			// Blend the images with current alpha
			Color[][] morphedImage = blend(source, scaledTarget, alpha);
			
			// Display the current morphed state
			Runigram.display(morphedImage);
			
			// Pause to show the animation
			StdDraw.pause(500);  // 500 milliseconds as specified
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image){
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				Color pixel = image[i][j];
				StdDraw.setPenColor( pixel.getRed(), pixel.getGreen(), pixel.getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
	
}

