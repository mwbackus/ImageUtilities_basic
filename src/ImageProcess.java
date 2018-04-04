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
		
		// Add the image
		utils.addImage(orig, "Original Image");

		
		
		// Add the process methods
		Color [][] halfBlack = process(orig);
		utils.addImage(halfBlack, "Half Black");
	
		
		// Display the image
		utils.display();
		
		
		/**
		//2d arrays and printing example:
		int [][] mat = {{0,1,2}, {3,4,5}, {6,7,8}};
		for (int row = 0; row < mat.length; row ++) {
			for (int col = 0; col < mat[row].length; col++) {
				System.out.print(mat[row][col] + " ");
			} System.out.println();
		}
		*/
		
		
		// Is the image in night or day?
		
		// Sharpen the image
		// 
	}
	
	// Other methods
	
	public static Color[][] process(Color [][] img) {
		/** */
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
