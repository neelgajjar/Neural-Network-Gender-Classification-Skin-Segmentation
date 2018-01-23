package skinDetector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SkinDetectorTrainer {
	public SkinDetectorTrainer(){
		
	}
	public void train(String imageFolderPath, String maskFolderPath) throws IOException{
		train(imageFolderPath, maskFolderPath,imageFolderPath+"_knowledge.dat");
	}
	
	public void train(String imageFolderPath, String maskFolderPath,String outputFilePath) throws IOException{
		String[] imageFilePaths = getAllFiles(imageFolderPath);
		String[] maskFilePath = getAllFiles(maskFolderPath);
		
		Matrix matImageTemp;
		Matrix matMaskTemp;
		int[][][] skinPixelNumber = new int[256][256][256];
		int[][][] nonskinPixelNumber = new int[256][256][256];
		
		System.out.println("\n\n### \t\tProcessing images..."); 
		for(int i=0;i<imageFilePaths.length;i++){
			matImageTemp = new Matrix(imageFilePaths[i],Matrix.RED_GREEN_BLUE);
			matMaskTemp = new Matrix(maskFilePath[i],Matrix.RED_GREEN_BLUE);
			
			readSkinColor(matImageTemp,matMaskTemp,skinPixelNumber,nonskinPixelNumber);
			
			System.out.println("Image Processed: " + i);
		}
		System.out.println("\n\n### \t\tProcessing data...");
		caculteProbability(skinPixelNumber,nonskinPixelNumber,outputFilePath);
	}
	
	
	//return all file path in root folder
	
	private String[] getAllFiles(String folderPath){
		File[] files = new File(folderPath).listFiles();
		ArrayList<String> filePathList = new ArrayList<String>();
		
		for(File file: files){
			filePathList.add(file.getAbsolutePath());
		}
		String[] filePaths = new String[filePathList.size()];
		filePathList.toArray(filePaths);
		
		return filePaths;
	}
	
	// 	count pixels of skin and nonSkin in image
	
	private void readSkinColor(Matrix matImage, Matrix matMask, int[][][] skinPixelNumber, int[][][] nonskinPixelnumber){
		int rows = matImage.getRows();
		int cols = matImage.getCols();
		
		int red=0,green = 0,blue = 0;
		for(int row =0,col;row<rows;row++){
			for(col=0;col<cols;col++){
				red = matImage.pixels[row][col][0];
				green = matImage.pixels[row][col][1];
				blue = matImage.pixels[row][col][2];
				
				if(doesShowSkin(matMask.getPixel(row, col))){
					skinPixelNumber[red][green][blue]++;
				}else{
					nonskinPixelnumber[red][green][blue]++;
				}
			}
		}
	}
	
	private boolean doesShowSkin(int[] value){
		if(value[0]>250&&value[1]>250 && value[2]>250){
			return true;
		}else{
			return false;
		}
	}
	
	// calculate the probablity of the pixel resembling skin
	private void caculteProbability(int[][][] skinPixelNumber, int[][][] nonskinPixelNumber,
			String outputFilePath) throws IOException {
		int totalSkinPixelNumber=0;
		int totalNonskinPixelNumber=0;
		for(int i=0; i<256; i++){
			for(int j=0; j<256; j++){
				for(int k=0; k<256; k++){
					skinPixelNumber[i][j][k] ++;
					totalSkinPixelNumber += skinPixelNumber[i][j][k];
					
					nonskinPixelNumber[i][j][k] ++;
					totalNonskinPixelNumber += nonskinPixelNumber[i][j][k];
				}
			}
		}
		double probabilityOfSkin = (double) totalSkinPixelNumber/(totalNonskinPixelNumber+totalSkinPixelNumber);
		double probability=0;
		BufferedWriter mainBW = new BufferedWriter(new FileWriter(outputFilePath));
		mainBW.write("");
		for(int i=0; i<256; i++){
			for(int j=0; j<256; j++){
				for(int k=0; k<256; k++){
										
					probability = skinPixelNumber[i][j][k]*probabilityOfSkin
							/(skinPixelNumber[i][j][k]+nonskinPixelNumber[i][j][k]);
					mainBW.append(String.format("%.3f\n", probability));
				}
			}
			
			System.out.print(".");	//notification
		}
		mainBW.close();
		
		return;
	}
	
	public static void main(String[] args) {
		String imageFolderPath = "E:/JAVA/MainProjects/GenderSearch/res/skinDetector/images";
		String maskFolderPath = "E:/JAVA/MainProjects/GenderSearch/res/skinDetector/mask";
		
		try {
			new SkinDetectorTrainer().train(imageFolderPath, maskFolderPath);
			System.out.println("\n\n## \t\t Successful!!!");
		} catch (Exception e) {
			System.out.println("\n\n## \t\t File Path Error!!");
		}
	}
	

}
