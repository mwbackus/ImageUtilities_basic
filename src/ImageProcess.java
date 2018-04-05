import java.awt.Color;
import java.util.Random;

/** 
 * Image Processing
 * Michael Backus
 * CS110 4/3/2018
 */

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
		utils.addImage(grayscale_img, "Scalar RGB");
	
		Color[][] invert_img = rgb_inversion(orig);
		utils.addImage(invert_img, "Invert RGB");
		
		Color[][] noisy_img = rgb_addnoise(orig);
		utils.addImage(noisy_img, "Add Noise");

		Color[][] reducecolors_img = rgb_reducecolors(orig);
		utils.addImage(reducecolors_img, "Reduce Depth");
		
		Color[][] replacecolors_img = rgb_replacecolors(orig);
		utils.addImage(replacecolors_img, "Replace Hue");
		
		Color[][] blur_img = rgb_blur(orig);
		utils.addImage(blur_img, "Gaussian Blur");
		
		Color[][] sharpen_img = rgb_sharpen(orig);
		utils.addImage(sharpen_img, "Unsharp Mask");
		
		Color[][] edgedetect_img = rgb_edges(orig);
		utils.addImage(edgedetect_img, "Edge Detect");
		
		Color[][] makewarm_img = rgb_warm(orig);
		utils.addImage(makewarm_img, "Make Warm");
		
		Color[][] makecool_img = rgb_cool(orig);
		utils.addImage(makecool_img, "Make Cool");
		
		
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
	
	public static Color[][] rgb_addnoise(Color [][] img) { // **** DONE ****
		/** Adds noise to the input image */
		
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);

		// Iterate through the pixels
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				
				// Get pixel information
				Color pixel = tmp[i][j];
				int[] rgb = { pixel.getRed(), pixel.getGreen(), pixel.getBlue() };
				
				// For every RGB value
				for (int v = 0; v < rgb.length; v++) {
					
					// If the pixel value is not too close to the edge
					int tmpColor = rgb[v]; int min = 0; int max = 0;
					if (tmpColor >= 30 && tmpColor <= 225) {
						
						// Assign new color values
						try {
							Random rand = new Random();
							min = tmpColor - 20;
							max = tmpColor + 20;
							int newColor = (max + (int)(Math.random() * ((max-min) + 1)));
							rgb[v] = newColor - 40;
						} catch (Exception ex) {
							continue; //RGB out of range
						}
					}
				}
				// Assign new pixel value
				tmp[i][j] = new Color(rgb[0], rgb[1], rgb[2]);
			}
		} 
		return tmp;
	}
	
	public static Color[][] rgb_reducecolors(Color [][] img) {
		/** Reduce the image colors */
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);
		
		// Iterate through the pixels
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				
				//Check whether the selected value is within range

				
					
					// Assign new color values
					Color pixel = tmp[i][j];
					int r = pixel.getRed()/2;
					int g = pixel.getGreen();
					int b = pixel.getBlue();
					
				// Assign new pixel value
				tmp[i][j] = new Color(r, 0, 0);
				
			}
		}
		return tmp;
	}
	
	public static Color[][] rgb_replacecolors(Color [][] img) {
		/** Replace the image colors with another */
		
		// SAMPLE VALUE
		int red = 151; int green = 112; int blue = 158; // A purple-ish color
		int OFFSET = 30;
		
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);
		
		// Iterate through the pixels
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				
				//Check if the selected value is within range
				Color pixel = tmp[i][j];
				int r = pixel.getRed();
				int g = pixel.getGreen();
				int b = pixel.getBlue();
				
				if ( ((r <= red + OFFSET) && (r >= red - OFFSET)) && ((g <= green + OFFSET) && 
						(g >= green - OFFSET)) && ((b <= blue + OFFSET) && (b >= blue - OFFSET)) ) {
					
					// Assign a new color value to the pixel
					tmp[i][j] = new Color(r-10, g-20 ,b+60);
					//tmp[i][j] = new Color(r, g+10 ,b+50);
				} else {
					//tmp[i][j] = new Color (0, 0, 0);
				}
			}
		}
		return tmp;
	}
	
	public static Color[][] rgb_blur(Color [][] img) {
		/** Blur the image */
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);
		
		double v = 1.0 / 9.0;
		double [][] kernel = {{v, v, v}, {v, v, v}, {v, v, v}};
		

		for (int y = 1; y < tmp.length-1; y++) { //Skip top and bottom edges
			for (int x = 1; x < tmp.length-1; x++) { //Skip left and right edges
				
				float sum = 0; // Kernel sum for this pixel
				
				// Get pixel information
				Color pixel = tmp[y][x];
				int r = pixel.getRed();
				int g = pixel.getGreen();
				int b = pixel.getBlue();

				/**
				for (int ky = -1; ky <= 1; ky++) {
					for (int kx = -1; kx <= 1; kx++) {
					
					
						
						// Calculate the adjacent pixel for this kernel point
						int pos = (y + ky) *512 + (x + kx);
						// Image is grayscale, red/green/blue are identical
						r = pos;
						// Multiply adjacent pixels based on the kernel values
						sum+=kernel[ky+1][kx+1]*r;
						
					}
				}
				*/

					
				
				tmp[y][x] = new Color(r, r, r);
				
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
	
	public static Color[][] rgb_edges(Color [][] img) {
		/** Detects the image edges */
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
	
	public static Color[][] rgb_warm(Color [][] img) {
		/** Shift the hue to a warmer color */
		
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);
		
		// Iterate through the pixels
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				
				//Check if the selected value is within range
				Color pixel = tmp[i][j];
				int r = pixel.getRed();
				int g = pixel.getGreen();
				int b = pixel.getBlue();
				try {
				tmp[i][j] = new Color(r+20, g, b);
				
				// If value is out of range, keep at original color
				} catch (Exception ex) {
					continue;
				}
			}
		}
		return tmp;
	}

	
	public static Color[][] rgb_cool(Color [][] img) {
		/** Shift the hue to a cooler color */
		
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);
		
		// Iterate through the pixels
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				
				//Check if the selected value is within range
				Color pixel = tmp[i][j];
				int r = pixel.getRed();
				int g = pixel.getGreen();
				int b = pixel.getBlue();
				try {
				tmp[i][j] = new Color(r, g, b+20);
				
				// If value is out of range, keep at original color
				} catch (Exception ex) {
					continue;
				}
			}
		}
		return tmp;
	}
	
	
	
	
	// Additional utilities
	
	
}
