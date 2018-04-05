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

		//Color[][] reducecolors_img = rgb_reducecolors(orig);
		//utils.addImage(reducecolors_img, "Reduce Depth");
		
		Color[][] replacecolors_img = rgb_replacecolors(orig);
		utils.addImage(replacecolors_img, "Replace Hue");
		
		int blurSize = 8; // MODIFY this for BLUR SIZE!
		Color[][] blur_img = rgb_boxblur(orig, blurSize);
		utils.addImage(blur_img, "Box Blur "+blurSize+"px");
		
		Color[][] sharpen_img = rgb_sharpen(orig);
		utils.addImage(sharpen_img, "Unsharp Mask");
		
		Color[][] edgedetect_img = rgb_edges(orig);
		utils.addImage(edgedetect_img, "Edge Detect");	
		
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
				
				// Get pixel information
				Color pixel = tmp[i][j];
				int[] rgb = { pixel.getRed(), pixel.getGreen(), pixel.getBlue() };
				
				// Iterate through the pixel channels and replace with new colors
				for (int v = 0; v > rgb.length; v++) {
					
					//Get and set the appropriate color for the channel
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
				} else {
					continue;
				}
			}
		}
		return tmp;
	}
	
	public static Color[][] rgb_gaussianblur(Color [][] img) {
		/** Blur the image */
		
		double v = 1.0 / 9.0;
		double[][] blur_kernel = { 	{v, v, v}, 
									{v, v, v}, 
									{v, v, v} };
		
		
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);
		

		// Iterate through the pixels
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				
				// Get pixel information
				Color pixel = tmp[i][j];
				//int[] rgb = { pixel.getRed(), pixel.getGreen(), pixel.getBlue() };
				
				double sum = 0; // Kernel sum for this pixel
				
				
				
				
				// Assign new pixel value
				tmp[i][j] = new Color(0, 0, 0);
			}
		} 
		return tmp;
	}
	
	
	

	public static Color[][] rgb_boxblur(Color [][] img, int blurAmount) {
		/** Blurs the image using the box blur concept */
		
		// Initialize method variables
		int SIZE = blurAmount;
		
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);

		// Iterate through the pixels
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				
				// Create pixel object to store pixel information
				Color pixel = tmp[i][j];
				
				// Sum the horizontal pixel RGB channel values. Mirror edge pixels
				int x_red = 0; int x_green = 0; int x_blue = 0;
				// Iterate through the current span of horizontal pixel samples
				for (int x = -1*SIZE; x < SIZE+1; x++) {
					if (i < SIZE ) { // If index less than sample size
						x_red+=(int) Math.floor( tmp[x+SIZE][j].getRed() );
						x_green+=(int) Math.floor( tmp[x+SIZE][j].getGreen() );
						x_blue+=(int) Math.floor( tmp[x+SIZE][j].getBlue() );
					} else if (i > tmp[i].length-1-SIZE) { // If index is larger than sample size
						x_red+= (int) Math.floor( tmp[i-2][j].getRed() );
						x_green+= (int) Math.floor( tmp[i-2][j].getGreen() );
						x_blue+= (int) Math.floor( tmp[i-2][j].getBlue() );
					} else { // Index is within the sample size
						x_red+=(int) Math.floor( tmp[i+x][j].getRed() );
						x_green+=(int) Math.floor( tmp[i+x][j].getGreen() );
						x_blue+=(int) Math.floor( tmp[i+x][j].getBlue() );
					}
				} 	
				// Get the average of the horizontal pixel channel values
				x_red = (int) Math.floor(x_red / (SIZE*2 + 1) );
				x_green = (int) Math.floor(x_green / (SIZE*2 + 1) );
				x_blue = (int) Math.floor(x_blue / (SIZE*2 + 1) );
				
				// Sum the vertical pixel RGB channel values. Mirror edge pixels
				int y_red = 0; int y_green = 0; int y_blue = 0;
				// Iterate through the current span of horizontal pixel samples
				for (int y = -1*SIZE; y < SIZE+1; y++) {
					if (j < SIZE ) { // If index less than sample size
						y_red+=(int) Math.floor( tmp[i][y+SIZE].getRed() );
						y_green+=(int) Math.floor( tmp[i][y+SIZE].getGreen() );
						y_blue+=(int) Math.floor( tmp[i][y+SIZE].getBlue() );
					} else if (j > tmp[i].length-1-SIZE) { // If index is larger than sample size
						y_red+= (int) Math.floor( tmp[i][j-1].getRed() );
						y_green+= (int) Math.floor( tmp[i][j-1].getGreen() );
						y_blue+= (int) Math.floor( tmp[i][j-1].getBlue() );
					} else { // Index is within the sample size
						y_red+=(int) Math.floor( tmp[i][j+y].getRed() );
						y_green+=(int) Math.floor( tmp[i][j+y].getGreen() );
						y_blue+=(int) Math.floor( tmp[i][j+y].getBlue() );
					}
				} 
				// Get the average of the vertical pixel channel values
				y_red = (int) Math.floor(y_red / (SIZE*2 + 1) );
				y_green = (int) Math.floor(y_green / (SIZE*2 + 1) );
				y_blue = (int) Math.floor(y_blue / (SIZE*2 + 1) );
				
				// Average pixels out
				int newRed = (int) Math.floor( (x_red + y_red) / 2);
				int newGreen = (int) Math.floor( (x_green + y_green) / 2);
				int newBlue = (int) Math.floor( (x_blue + y_blue) / 2);	
				
				// Assign new pixel value
				tmp[i][j] = new Color(newRed, newGreen, newBlue);
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
	

		
	// Additional utilities
	
	
}
