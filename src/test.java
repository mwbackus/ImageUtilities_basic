import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class test {

	public static void main(String[] args) {
		int[] arr = {5, 6, 2, 2, 3, 1, 5, 6, 4};
		int SIZE = 1;
		
		int sum = 0;
		
		// Iterate through the pixels
		for (int i = 0; i < arr.length; i++) {
			if ( i < SIZE ) {
				System.out.println("i - not normal. Value: " + i);
				sum+=arr[i+SIZE];

			} else if (i>(arr.length-1-SIZE)) {
				System.out.println("i + not normal. Value: " + i);
				sum+=arr[i-SIZE];
				
			} else { // Pixel is within range, add
				sum+=arr[i];
				System.out.println("i is normal. Value: "+ i);
			}
			
			
			//int num = (int) Math.floor((arr[i-1] + arr[i] + arr[i+1]) / 3 );
			//System.out.println((arr[i-1] + " + " + arr[i] + " + " + arr[i+1] + " = " + num ));
		}		
		System.out.println("Sum = "+sum);
	}

}
