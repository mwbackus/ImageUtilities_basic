import java.awt.Color;

public class ImageProcess {

	public static void main(String[] args) {
		
		// Create new utility for loading/viewing images
		ImageUtils utils = new ImageUtils();
		
		// Load an image
		Color[][] originalImg = utils.loadImage("src/LennaCV.png");
		
		// Add and display the image
		utils.addImage(originalImg,  "original image");;
		utils.display();

		//2d arrays and printing example:
		int [][] mat = {{0,1,2}, {3,4,5}, {6,7,8}};
		for (int row = 0; row < mat.length; row ++) {
			for (int col = 0; col < mat[row].length; col++) {
				System.out.print(mat[row][col] + " ");
			} System.out.println();
		}
		
		// Is the image in night or day?
		
		// Sharpen the image
		// 
	}

}
