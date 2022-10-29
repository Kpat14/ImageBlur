import java.awt.Color;
import java.util.Arrays;

/**
 * @author Andriy Pavlovych
 * The class is meant to illustrate a couple of image-processing algorithms:
 * Gaussian blurring and simple edge detection
 */
public class ImageFilter{
	//TODO prevent object creation, as this is a utility class
	
	/**
	 * Method implements Gaussian blurring
	 * @param imageData array of image pixels
	 * @param width image width
	 * @param sigma parameter of the Gaussian distribution
	 */
	public static void blur (int[] imageData, int width, double sigma){
		//TODO your task is to replace this stub code with the proper implementation of the method
		//create a temporary array to store the result
		final int MAX_KERNEL_SIZE = 15;
		double kernel [] = new double [MAX_KERNEL_SIZE];
		// Creating and Populating 2D array
		int length = imageData.length/width;
		int[][] imageDataMatrix = new int[length][width];
		int counter = 0;
		for(int i = 0; i < imageDataMatrix.length; i++) {
			for(int j = 0; j < imageDataMatrix[i].length; j++) {
				imageDataMatrix[i][j] = imageData[counter];
				counter++;
			}
		}
		
		for (int i = 0; i <= MAX_KERNEL_SIZE / 2 ; i++){
			kernel [MAX_KERNEL_SIZE / 2 + i] = Math.exp(-0.5 * i * i / sigma / sigma);
			kernel [MAX_KERNEL_SIZE / 2 - i] = Math.exp(-0.5 * i * i / sigma / sigma);
		}
		double kernelSum = 0;
		for (int i = 0; i < MAX_KERNEL_SIZE; i++) kernelSum += kernel [i]; //compute the sum
		for (int i = 0; i < MAX_KERNEL_SIZE; i++) kernel [i] /= kernelSum; //normalize by that sum
		System.out.println(Arrays.toString(kernel));
		
		
		int [] resultImageData = null; //TODO NO, it should not be null!
		//TODO apply convolution in one dimension
		counter = 0;
		int traverse = 0;
		int red, green, blue, sum = 0;
		int[] holdColor = new int[35];
		for(int i = 0; i < imageDataMatrix.length; i++) {
			for(int j = 0; j < imageDataMatrix[0].length; j++) {
				if(j>7 && j < 633) {
					int stopLeft = j - 7;
					for(int k = j; k > stopLeft; k--) {
						red = (int) (((imageDataMatrix[i][k] & 0x00FF0000)>>16) * kernel[traverse]);
						green = (int) (((imageDataMatrix[i][k] & 0x0000FF00)>>8) * kernel[traverse]);
						blue = (int) ((imageDataMatrix[i][k] & 0x000000FF) * kernel[traverse]);
						holdColor[counter] = red<<16 | green <<8 | blue;
						counter++;
						traverse++;
					}
					int stopRight = j + 7;
					for(int k = j; k < stopRight; k++) {
						red = (int)  (((imageDataMatrix[i][k] & 0x00FF0000)>>16) * kernel[traverse]);
						green = (int) (((imageDataMatrix[i][k] & 0x0000FF00)>>8) * kernel[traverse]);
						blue = (int) ((imageDataMatrix[i][k] & 0x000000FF) * kernel[traverse]);
						holdColor[counter] = red<<16 | green <<8 | blue;
						counter++;
						traverse++;
					}	
				for(int k = 0; k < holdColor.length; k++) {
					sum+=holdColor[k];
				}
				imageDataMatrix[i][j]=sum;
				sum=0;
				counter=0;
				traverse=0;
			}			
			}
		}		
		//TODO repeat for the other dimension
		for(int i=0; i < imageDataMatrix.length; i++) {
			for(int j = 0; j < imageDataMatrix[0].length; j++) {
				if(i > 7 && i < 473) {
					int stopUp = i-7;
					for(int k = i; k > stopUp; k--) {
						red = (int) (((imageDataMatrix[k][j] & 0x00FF0000)>>16) * kernel[traverse]);
						green = (int) (((imageDataMatrix[k][j] & 0x0000FF00)>>8) * kernel[traverse]);
						blue = (int) ((imageDataMatrix[k][j] & 0x000000FF) * kernel[traverse]);
						holdColor[counter] = red<<16 | green <<8 | blue;
						counter++;
						traverse++;
					}
					int stopDown = i + 7;
					for(int k = i; k < stopDown; k++) {
						red = (int) (((imageDataMatrix[k][j] & 0x00FF0000)>>16) * kernel[traverse]);
						green = (int) (((imageDataMatrix[k][j] & 0x0000FF00)>>8) * kernel[traverse]);
						blue = (int) ((imageDataMatrix[k][j] & 0x000000FF) * kernel[traverse]);
						holdColor[counter] = red<<16 | green <<8 | blue;
						counter++;
						traverse++;
					}
					for(int k = 0; k < holdColor.length; k++) {
						sum+=holdColor[k];
					}
					imageDataMatrix[i][j]=sum;
					sum=0;
					counter=0;
					traverse=0;
				}
			}
		}
		
		
		//TODO store the result back in the original imageData array
		//one way to store the result back 
		//System.arraycopy(resultImageData, 0, imageData, 0, imageData.length);
		counter=0;
		for(int i = 0; i < imageDataMatrix.length; i++) {
			for(int j = 0; j < imageDataMatrix[i].length; j++) {
				imageData[counter] = imageDataMatrix[i][j];
				counter++;
			}
		}
		Color mycolor = new Color(imageData.length);
		System.out.println(mycolor.getRGB());
	
	}



	/**
	 * Method implements simple edge detection
	 * @param imageData imageData array of image pixels
	 * @param width image width
	 */
	public static void edgeDetection(int[] imageData, int width) {
		//TODO your task is to replace this stub code with the proper implementation of the method
		//The code below merely demonstrates how to extract RGB pixel values from the image and how to write them them back
				int[][] kernelMatrix = {{1, 0, -1,},{2, 0, -2}, {1,0, -1}};
				int[][] secondaryKernelMatrix = {{0, -2}, {0, -1}};
				int[][] thirdKernel = {{2, 0, -2,},{1, 0, -1}};
				int[][] fourthKernel = {{0,-1},{0,-2},{0,-1}};
				int[][] imageDataMatrix  = new int[480][640];
				int[][] secondaryDataMatrix = new int[480][640];
				int[] colorHolder = new int [9];
				int counter = 0;
				int sum = 0;
				int r,g, b = 0;
				for(int i = 0; i < imageDataMatrix.length; i++) {
					for(int j = 0; j < imageDataMatrix[i].length; j++) {
						imageDataMatrix[i][j] = imageData[counter];
						counter++;
					}
			}
				counter = 0;
			//Applying convolution
			
				for(int i = 0; i < imageDataMatrix.length; i++) {
					for(int j = 0; j < imageDataMatrix[i].length; j++) {
					
	
						int traverse = 0;
						if(i == 0 && j ==0 || i==0 && j==478) {
							traverse = 0;
							for(int k = 0; k < 2; k++) {
								r 	=  (int) ((0.0*((imageDataMatrix[i][j+k] & 0x00FF0000)>>16)) * secondaryKernelMatrix[0][traverse]);
								g 	= (int) ((((imageDataMatrix[i][j+k] & 0x0000FF00)>>8)*secondaryKernelMatrix[0][traverse]));
								b 	= (int) ((((imageDataMatrix[i][j+k] & 0x000000FF))*secondaryKernelMatrix[0][traverse]));
								colorHolder[counter] = r<<16 | g <<8 | b;
								counter++;
								traverse++;
							}
							traverse = 0;
							for(int k = 0; k < 2; k++) {
								r 	= (int) ((0.0*((imageDataMatrix[i+1][j+k] & 0x00FF0000)>>16)) * secondaryKernelMatrix[1][traverse]);
								g 	= (int) ((((imageDataMatrix[i+1][j+k] & 0x0000FF00)>>8))*secondaryKernelMatrix[1][traverse]);
								b 	= (int) ((((imageDataMatrix[i+1][j+k] & 0x000000FF))*secondaryKernelMatrix[1][traverse]));
								colorHolder[counter] = r<<16 | g <<8 | b;
								counter++;
								traverse++;
							}
						}else if(i==0 && j >1 && j <478) {
							for(int k = 0; k < 3; k++) {
								r 	=  (int) ((0.0*((imageDataMatrix[i][j+k] & 0x00FF0000)>>16) * thirdKernel[0][k]));
								g 	= (int) ((((imageDataMatrix[i][j+k] & 0x0000FF00)>>8))*thirdKernel[0][k]);
								b 	= (int) ((imageDataMatrix[i][j+k] & 0x000000FF)*thirdKernel[0][k]);
								colorHolder[counter] = r<<16 | g <<8 | b;
								counter++;
							}
							for(int k = 0; k < 3; k++) {
								r 	=  (int) ((0.0*((imageDataMatrix[i+1][j+k] & 0x00FF0000)>>16) * thirdKernel[1][k]));
								g 	= (int) ((((imageDataMatrix[i+1][j+k] & 0x0000FF00)>>8))*thirdKernel[1][k]);
								b 	= (int) ((imageDataMatrix[i+1][j+k] & 0x000000FF)*thirdKernel[1][k]);
								colorHolder[counter] = r<<16 | g <<8 | b;
								counter++;
							}
						}else if(i>3 && j>3 && i < 475 && j<635) {
							traverse=0;
							for(int k = 0; k < 2; k++) {
								r =  (int) ((((imageDataMatrix[i][j-k] & 0x00FF0000)>>16) * kernelMatrix[0][traverse]));
								g = (int) ((((imageDataMatrix[i][j-k] & 0x0000FF00)>>8))*kernelMatrix[0][traverse]);
								b = (int) ((imageDataMatrix[i][j-k] & 0x000000FF)*kernelMatrix[0][traverse]);
								colorHolder[counter] = r<<16 | g <<8 | b;
								counter++;
								traverse++;
							}
							traverse=2;
							for(int k = 1; k < 2; k++) {
								r =  (int) ((((imageDataMatrix[i][j+k] & 0x00FF0000)>>16) * kernelMatrix[0][traverse]));
								g = (int) ((((imageDataMatrix[i][j+k] & 0x0000FF00)>>8))*kernelMatrix[0][traverse]);
								b = (int) ((imageDataMatrix[i][j+k] & 0x000000FF)*kernelMatrix[0][traverse]);
								colorHolder[counter] = r<<16 | g <<8 | b;
								counter++;
							}
			
							traverse=0;
							for(int k = 0; k <2; k++) {
								r =  (int) ((((imageDataMatrix[i+1][j-k] & 0x00FF0000)>>16) * kernelMatrix[1][traverse]));
								g = (int) ((((imageDataMatrix[i+1][j-k] & 0x0000FF00)>>8))*kernelMatrix[1][traverse]);
								b = (int) ((imageDataMatrix[i+1][j-k] & 0x000000FF)*kernelMatrix[1][traverse]);
								colorHolder[counter] = r<<16 | g <<8 | b;
								counter++;
								traverse++;
							}
							traverse=2;
							for(int k = 1; k < 2; k++) {
								r =  (int) ((((imageDataMatrix[i+1][j+k] & 0x00FF0000)>>16) * kernelMatrix[1][traverse]));
								g = (int) ((((imageDataMatrix[i+1][j+k] & 0x0000FF00)>>8))*kernelMatrix[1][traverse]);
								b = (int) ((imageDataMatrix[i+1][j+k] & 0x000000FF)*kernelMatrix[1][traverse]);
								colorHolder[counter] = r<<16 | g <<8 | b;
								counter++;
							}
							traverse=0;
							for(int k = 0; k < 2; k++) {
								r =  (int) ((((imageDataMatrix[i+2][j-k] & 0x00FF0000)>>16) * kernelMatrix[2][traverse]));
								g = (int) ((((imageDataMatrix[i+2][j-k] & 0x0000FF00)>>8))*kernelMatrix[2][traverse]);
								b = (int) ((imageDataMatrix[i+2][j-k] & 0x000000FF)*kernelMatrix[2][traverse]);
								colorHolder[counter] = r<<16 | g <<8 | b;
								counter++;
								traverse++;
							}
							traverse=2;
							for(int k = 1; k < 2; k++) {
							r =  (int) ((((imageDataMatrix[i+2][j+k] & 0x00FF0000)>>16) * kernelMatrix[2][traverse]));
							g = (int) ((((imageDataMatrix[i+2][j+k] & 0x0000FF00)>>8))*kernelMatrix[2][traverse]);
							b = (int) ((imageDataMatrix[i+2][j+k] & 0x000000FF)*kernelMatrix[2][traverse]);
							colorHolder[counter] = r<<16 | g <<8 | b;
							counter++;
							}
						}
						for(int k = 0 ; k< colorHolder.length; k++) {
							sum += colorHolder[k];
						}
						secondaryDataMatrix[i][j] = sum/25;
						sum=0;
						counter=0; 
						
					}
					
				}
			
			
			counter = 0;
			
				for(int i = 0; i < imageDataMatrix.length; i++) {
					for(int j = 0; j < imageDataMatrix[i].length; j++) {
						imageData[counter] = secondaryDataMatrix[i][j];
						counter++;
					}
			}	
			}
			
			
			}