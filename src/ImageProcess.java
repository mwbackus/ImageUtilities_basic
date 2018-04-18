import java.awt.BorderLayout;
import java.awt.Color;

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
		int blurSize = 6; // MODIFY this for BLUR SIZE!
		Color[][] blur_img = rgb_boxblur(orig, blurSize);
		utils.addImage(blur_img, "Box Blur "+blurSize+"px");
		
		int pixelateSize = 8; // MODIFY this for PIXELATE SIZE! Must be a POWER of 2!
		Color[][] edgedetect_img = rgb_pixelate(orig, pixelateSize);
		utils.addImage(edgedetect_img, "Pixelate "+pixelateSize+"px");	
		
		int reductionSize = 64; // MODIFY this for REDUCTION AMOUNT! Must be a POWER of 2!
		Color[][] reducecolors_img = rgb_reducecolors(orig, reductionSize);
		utils.addImage(reducecolors_img, "Reduce Depth "+reductionSize+"x");
		
		Color[][] add_ssao_img = rgb_screenbased_ambient_occlusion(orig);
		utils.addImage(add_ssao_img, "Screen-Based Ambient Occlusion");
		
		Color[][] replacecolors_img = rgb_replacecolors(orig);
		utils.addImage(replacecolors_img, "Enhance Feathers");

		Color[][] grayscale_img = rgb_to_grayscale(orig);
		utils.addImage(grayscale_img, "Scalar RGB");
	
		Color[][] invert_img = rgb_inversion(orig);
		utils.addImage(invert_img, "Invert RGB");
		
		Color[][] noisy_img = rgb_addnoise(orig);
		utils.addImage(noisy_img, "Add Noise");

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
	
	public static Color[][] rgb_reducecolors(Color [][] img, int reduceAmount) {
		/** Reduce the image colors */
		
		int FACTOR = reduceAmount;
		
		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);

		// Iterate though the pixels
		for (int i=0; i <tmp.length; i++) {
			for (int j=0; j<tmp[i].length; j++) {
				
				// Get pixel information
				Color pixel = tmp[i][j];
					
				//Divide the color by the factor, truncate the value
				int r = (int)pixel.getRed()/FACTOR;
				int g = (int)pixel.getGreen()/FACTOR;
				int b = (int)pixel.getBlue()/FACTOR;

				// Assign new pixel value
				tmp[i][j] = new Color(r*=FACTOR, g*=FACTOR, b*=FACTOR);
				
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
	
	public static Color[][] rgb_pixelate(Color [][] img, int pixelAmount) {
		/** Pixelate the image */
		
		int SIZE = pixelAmount;

		// Copy the array and store it as tmp
		Color[][] tmp = ImageUtils.cloneArray(img);

		// Iterate through the pixels
		for (int x=0; x <tmp.length; x+=SIZE) {
			for (int y=0; y<tmp[x].length; y+=SIZE) {
				
				// Replace horizontal pixels
				for (int i = 0; i < SIZE; i++) {
					tmp[x+i][y] = new Color (tmp[x][y].getRed(), tmp[x][y].getGreen(), tmp[x][y].getBlue());
					// Replace vertical pixels
					for (int j = 0; j < SIZE; j++){
						tmp[x+i][y+j] = new Color (tmp[x][y].getRed(), tmp[x][y].getGreen(), tmp[x][y].getBlue());
					}
				}				
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
	
	public static Color[][] rgb_screenbased_ambient_occlusion(Color [][] img) {
		/** Adds pseudo SSAO to the image and shift the white balance a little towards blue */
		
		// Stack the effects: Make grayscale, add blur
		Color[][] original = ImageUtils.cloneArray(img);
		Color[][] rgb_gray = rgb_to_grayscale(original);
		Color[][] rgb_gray_blurred = rgb_boxblur(rgb_gray, 8);
		Color[][] rgb_final_img = ImageUtils.cloneArray(img);
		
		// Iterate through the pixels
		for (int i=0; i <rgb_gray_blurred.length; i++) {
			for (int j=0; j<rgb_gray_blurred[i].length; j++) {
				int r = 0; int g = 0; int b = 0;
				
				// Create two temporary pixels
				Color pixelGray = rgb_gray_blurred[i][j];
				Color pixelOrig = rgb_final_img[i][j];
				
				// Multiply the blurred grayscale image over the original
				// Note: This strategy works best for the Lenna image and will break for others
				r = (int)pixelGray.getRed() * pixelOrig.getRed() / 200 - 5;
				g = (int)pixelGray.getGreen() * pixelOrig.getGreen() / 200 + 5;
				b = (int)pixelGray.getBlue() * pixelOrig.getBlue() / 200 + 5;

				// Assign a new color value to the pixel
				rgb_final_img[i][j] = new Color (r, g, b);
				}
			}
		return rgb_final_img;
	}
	
	
}
