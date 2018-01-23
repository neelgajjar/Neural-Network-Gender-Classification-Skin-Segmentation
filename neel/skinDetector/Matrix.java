package skinDetector;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Matrix {
	
	public static final int BLACK_WHITE =1,
			BLACK_WHITE_ALPHA = 2,
			RED_GREEN_BLUE = 3,
			RED_GREEN_BLUE_ALPHA = 4;
	
	// max-pixel = 255 higest value of the pixel
	// minPixel = 0 minimum value of the pixel
	
	public static final int MAX_PIXEL = 255,
			MIN_PIXEL =0;
	
	/* first diamention represent row or height from top to the bottum ranging from 0 to height-1
	 * second diamention represent colomn to width from lest to right ranging from 0 to width-1
	 * third diamention represent intensity value */
	
	public int[][][] pixels;
	
	// constructure
	
	public Matrix(int [][][] pixels ){
		this.pixels = pixels;
	}
	/*	 * When invalid info is provided Matrix is created with minimal size.
	 * @param row height of the image
	 * @param col width of the image */
	
	public Matrix(int row, int col, int type){
		if(row<1){
			row=1;
		}if(col<1){
			col=1;
		}if(type<Matrix.BLACK_WHITE || type>Matrix.RED_GREEN_BLUE_ALPHA){
			type=Matrix.BLACK_WHITE;
		}
		this.pixels = new int[row][col][type];
	}
	
	public Matrix(String imageFilePath, int type) throws IOException{
		BufferedImage bufferedImage = ImageIO.read(new File(imageFilePath));
		this.pixels = bufferedImageToMatrix(bufferedImage,type).pixels;
	}
	public Matrix(BufferedImage bufferedImage, int type){
		this.pixels = bufferedImageToMatrix(bufferedImage, type).pixels;
	}
	
	// getter setter
	
	//return height of image
	
	public int getRows(){
		return this.pixels.length;
	}
	
	// return width of the image
	
	public int getCols(){
		return this.pixels[0].length;
	}
	/* return 1 for black-white
	 * return 2 for black-white-alpha
	 * return 3 for red-green-blue
	 * return 4 for red-green-blue-alpha*/
	
	public int getType(){
		return this.pixels[0][0].length;
	}
	
	// no of rows and col
	
	public int[] getPixel(int rowNo, int colNo){
		return this.pixels[rowNo][colNo].clone();
	}
	
	public void setPixel(int rowNo, int colNo, int[] value){
		this.pixels[rowNo][colNo].clone();
	}
	
	//matrix bufferedImage method
	//create bufferedImage from matrix
	
	public static BufferedImage matrixToBufferedImage(Matrix matrix){
		BufferedImage bufferedImage = new BufferedImage(matrix.pixels[0].length, matrix.pixels.length,
				BufferedImage.TYPE_4BYTE_ABGR);
		
		int type = matrix.getType();
		if(type==Matrix.BLACK_WHITE){
			for(int i=0, j; i<matrix.pixels.length; i++){
				for(j=0; j<matrix.pixels[0].length; j++){
					int[] rgb = matrix.pixels[i][j];
					int color = (Matrix.MAX_PIXEL<<24) | (rgb[0]<<16) | (rgb[0]<<8) | rgb[0];
					bufferedImage.setRGB(j, i, color);
				}
			}
		}else if(type==Matrix.BLACK_WHITE_ALPHA){
			for(int i=0, j; i<matrix.pixels.length; i++){
				for(j=0; j<matrix.pixels[0].length; j++){
					int[] rgb = matrix.pixels[i][j];
					int color = (rgb[1]<<24) | (rgb[0]<<16) | (rgb[0]<<8) | rgb[0];
					bufferedImage.setRGB(j, i, color);
				}
			}
		}else if(type==Matrix.RED_GREEN_BLUE){
			for(int i=0, j; i<matrix.pixels.length; i++){
				for(j=0; j<matrix.pixels[0].length; j++){
					int[] rgb = matrix.pixels[i][j];
					int color = (Matrix.MAX_PIXEL<<24) | (rgb[0]<<16) | (rgb[1]<<8) | rgb[2];
					bufferedImage.setRGB(j, i, color);
				}
			}
		}else{
			for(int i=0, j; i<matrix.pixels.length; i++){
				for(j=0; j<matrix.pixels[0].length; j++){
					int[] rgb = matrix.pixels[i][j];
					int color = (rgb[3]<<24) | (rgb[0]<<16) | (rgb[1]<<8) | rgb[2];
					bufferedImage.setRGB(j, i, color);
				}
			}
		}
		
		return bufferedImage;
	}
	
	// create matrix from bufferedImage
	
	public static Matrix bufferedImageToMatrix(BufferedImage bufferedImage, int type){
		int row = bufferedImage.getHeight();
		int col = bufferedImage.getWidth();
		
		if(type<Matrix.BLACK_WHITE || type>Matrix.RED_GREEN_BLUE_ALPHA){
			type = Matrix.BLACK_WHITE;
		}
		
		Matrix matrix = new Matrix(row, col, type);
		
		if(type==BLACK_WHITE){
			for(int i=0, j; i<row; i++){
				for(j=0; j<col; j++){
					int rgb = bufferedImage.getRGB(j, i);
					matrix.pixels[i][j] = new int[]{rgb>>8 & 0xFF};
				}
			}
		}else if(type==BLACK_WHITE_ALPHA){
			for(int i=0, j; i<row; i++){
				for(j=0; j<col; j++){
					int rgb = bufferedImage.getRGB(j, i);
					matrix.pixels[i][j] = new int[]{rgb>>8 & 0xFF, rgb>>24 & 0xFF};
				}
			}
		}else if(type==RED_GREEN_BLUE){
			for(int i=0, j; i<row; i++){
				for(j=0; j<col; j++){
					int rgb = bufferedImage.getRGB(j, i);
					matrix.pixels[i][j] = new int[]{rgb>>16 & 0xFF, rgb>>8 & 0xFF, rgb>>0 & 0xFF};
				}
			}
		}else{
			for(int i=0, j; i<row; i++){
				for(j=0; j<col; j++){
					int rgb = bufferedImage.getRGB(j, i);
					matrix.pixels[i][j] = new int[]{rgb>>16 & 0xFF, rgb>>8 & 0xFF, rgb>>0 & 0xFF, rgb>>24 & 0xFF};
				}
			}
		}
		
		return matrix;
	}
	
	// crop a section out of matrix
	
	public Matrix subMatrix(int rowStart, int rowEnd, int colStart, int colEnd){
		if(rowStart>=0 && colStart>=0&&rowEnd<=getRows() && colEnd<=getCols()
				&& rowEnd>rowStart && colEnd>colStart){
			int row = rowEnd-rowStart;
			int col = colEnd-colStart;
			
			Matrix matrix = new Matrix(row,col, this.getType());
			
			for(int i=0;i<row;i++){
				for(int j=0;j<col;j++){
					matrix.pixels[i][j] = this.pixels[rowStart+i][rowEnd+j].clone();
				}
			}
			return matrix;
		}else{
			return null;
		}
	}
	
	//clone entire matrix into new instance
	
	public Matrix clone(){
		return subMatrix(0, getRows(), 0, getCols());
	}
	
	//io operations
	
	public void write (String filePath) throws IOException{
		BufferedImage bufferedImage = matrixToBufferedImage(this);
		ImageIO.write(bufferedImage, filePath.substring(filePath.lastIndexOf('.')+1), new File(filePath));
	}
	
	//return matrix as a text
	
	public String dump(){
		int row = this.pixels.length;
		int col = this.pixels[0].length;
		int type = this.pixels[0][0].length;
		
		String string = "(" + row + "," + col + "," + type + ")";
		string +="[";
		
		String pixelString, rowPixelString;
		
		for(int i=0,j;i<row;i++){
			rowPixelString ="";
			for(j=0;j<col;j++){
				pixelString="";
				for(int k=0;k<type;k++){
					pixelString+=this.pixels[i][j][k] +",";
				}
				rowPixelString +=pixelString;
			}
			string+=rowPixelString;
		}
		string+="]";
		return string;
	}
	
	//create matrix from text
	
	public static Matrix load(String string){
		int startIndex=0, stopIndex=0;
		int row=1, col=1, type=1;
		
		startIndex = 1;
		stopIndex = string.indexOf(',', startIndex);
		row = Integer.valueOf(string.substring(startIndex, stopIndex));
		
		startIndex = stopIndex+1;
		stopIndex = string.indexOf(',', startIndex);
		col = Integer.valueOf(string.substring(startIndex, stopIndex));
		
		startIndex = stopIndex+1;
		stopIndex = string.indexOf(')', startIndex);
		type = Integer.valueOf(string.substring(startIndex, stopIndex));
		
		
		
		Matrix matrix = new Matrix(row, col, type);
		
		stopIndex = string.indexOf('[', stopIndex);
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				
				for(int k=0; k<type; k++){
					startIndex = stopIndex+1;
					stopIndex = string.indexOf(',', startIndex);
					
					matrix.pixels[i][j][k] = Integer.valueOf(string.substring(startIndex, stopIndex));
				}
			}
		}
		
		return matrix;
	}
	
	//visualizes image as text
	
	public String toString(){
		int row = this.pixels.length;
		int col = this.pixels[0].length;
		int type = this.pixels[0][0].length;
		
		String string = "(" + row + "," + col + "," + type + ")\n";
		string += "[\n";
		
		String pixelString, rowPixelsString;
		for(int i=0, j, k; i<row; i++){
	
			rowPixelsString = "[";
			for(j=0; j<col; j++){
				
				pixelString = "("+this.pixels[i][j][0];
				for(k=1; k<type; k++){
					pixelString += "." + this.pixels[i][j][k];
				}
				
				rowPixelsString += pixelString + ") ";
			}
			
			string += rowPixelsString+"]\n";
		}
		
		string += "]";
		
		return string;
	}

}
