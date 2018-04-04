import java.awt.Color;


import javax.swing.JButton;
import javax.swing.JFrame;


public class ImageProcess {

	public static void main(String[] args) {
		/** Run the program */
		
		// Create new utility object for loading and viewing images
		ImageUtils utils = new ImageUtils();
				
		// Load the test image
		Color[][] orig = utils.loadImage("src/LennaCV.png");
		
		// Add the original image button
		utils.addImage(orig, "Original");

		// Add the process methods
		Color[][] grayscale_img = rgb_to_grayscale(orig);
		utils.addImage(grayscale_img, "Grayscale");
	
		Color[][] invert_img = rgb_inversion(orig);
		utils.addImage(invert_img, "Invert");
		
		Color[][] noisy_img = rgb_addnoise(orig);
		utils.addImage(noisy_img, "Noise");
		
		Color[][] reducecolors_img = rgb_reducecolors(orig);
		utils.addImage(reducecolors_img, "Reduce");
		
		Color[][] sharpen_img = rgb_sharpen(orig);
		utils.addImage(sharpen_img, "Sharpen");
		
		Color[][] blur_img = rgb_blur(orig);
		utils.addImage(blur_img, "Blur");
		
		// Display the image
		utils.display();
	}
	
	
	// Processing Methods
	
	public static Color[][] rgb_to_grayscale(Color [][] img) {
		/** Converts the RGB input image to a scalar image */
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);

		// Iterate through the pixels
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				
				// Get pixel information
				Color pixel = tmp[i][j];
				int r = pixel.getRed();
				int g = pixel.getGreen();
				int b = pixel.getBlue();
				
				// Average out the pixels
				int avg = (r+g+b)/3;
				
				// Assign new pixel value
				tmp[i][j] = new Color(avg, avg, avg);
			}
		} 
		return tmp;
	}
	
	public static Color[][] rgb_inversion(Color [][] img) {
		/** Inverts RGB input image color values */
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);
		
		// Iterate though the pixels
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				
				// Get and modify pixels
				Color pixel = tmp[i][j];
				int r = 255 - pixel.getRed();
				int g = 255 - pixel.getGreen();
				int b = 255 - pixel.getBlue();
				
				// Assign new pixel value
				tmp[i][j] = new Color(r, g, b);
			}
		}
		return tmp;
	}
	
	public static Color[][] rgb_addnoise(Color [][] img) {
		/** Adds noise to the input image */
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);
		
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				//Check whether row is less than half the image
				if (i < tmp.length/2) {
					//tmp[i][j] = new Color(0,0,0); //RGB 0-255
					Color pixel = tmp[i][j];
					int r = pixel.getRed()/2;
					int g = pixel.getGreen();
					int b = pixel.getBlue();
				tmp[i][j] = new Color(r, 0, 0);
				}
			}
		}
		return tmp;
	}
	
	public static Color[][] rgb_reducecolors(Color [][] img) {
		/** Reduce the image colors */
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);
		
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				//Check whether row is less than half the image
				if (i < tmp.length/2) {
					//tmp[i][j] = new Color(0,0,0); //RGB 0-255
					Color pixel = tmp[i][j];
					int r = pixel.getRed()/2;
					int g = pixel.getGreen();
					int b = pixel.getBlue();
				tmp[i][j] = new Color(r, 0, 0);
				}
			}
		}
		return tmp;
	}
	
	public static Color[][] rgb_sharpen(Color [][] img) {
		/** Sharpen the image */
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);
		
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				//Check whether row is less than half the image
				if (i < tmp.length/2) {
					//tmp[i][j] = new Color(0,0,0); //RGB 0-255
					Color pixel = tmp[i][j];
					int r = pixel.getRed()/2;
					int g = pixel.getGreen();
					int b = pixel.getBlue();
				tmp[i][j] = new Color(r, 0, 0);
				}
			}
		}
		return tmp;
	}
	
	public static Color[][] rgb_blur(Color [][] img) {
		/** Blur the image */
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);
		
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				//Check whether row is less than half the image
				if (i < tmp.length/2) {
					//tmp[i][j] = new Color(0,0,0); //RGB 0-255
					Color pixel = tmp[i][j];
					int r = pixel.getRed()/2;
					int g = pixel.getGreen();
					int b = pixel.getBlue();
				tmp[i][j] = new Color(r, 0, 0);
				}
			}
		}
		return tmp;
	}
	
	
	
}
