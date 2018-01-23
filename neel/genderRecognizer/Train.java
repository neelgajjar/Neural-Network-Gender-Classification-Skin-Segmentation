package genderRecognizer;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import org.opencv.core.Core;

import weightedPixal.WeightedStandardImage;
import weightedPixal.WeightedStandardPixelTrainer;

public class Train {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//list of image files//////////////////////////////////////////////////////////////////////////////////////////
		String trainningFolderPath = "E:/JAVA/MainProjects/GenderSearch/res/traningData";
		
		File trainningFolder = new File(trainningFolderPath);
		String[] trainningSubfolderPaths = trainningFolder.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		
		
		
		ArrayList<String> filePathList = new ArrayList<String>();
		ArrayList<Integer> idList = new ArrayList<Integer>();
		
		int id=0;	//label
		for(String SubfolderPath: trainningSubfolderPaths){
			File[] files = new File(trainningFolderPath+"\\"+SubfolderPath).listFiles();
			
			int limitedNumberOfSamples = 0;
			for(File file: files){
				filePathList.add(file.getAbsolutePath());
				idList.add(id);
				
				limitedNumberOfSamples++;
				if(limitedNumberOfSamples > 1000) break;
			}
			
			id++;
		}
		
		String[] filePaths = new String[filePathList.size()];
		filePathList.toArray(filePaths);
		Integer[] ids = new Integer[idList.size()];
		idList.toArray(ids);
		
	
		
		//train////////////////////////////////////////////////////////////////////////////////////////////////////////
		WeightedStandardPixelTrainer weightedStandardPixelTrainer = new WeightedStandardPixelTrainer();
		weightedStandardPixelTrainer.train(filePaths, ids);
		WeightedStandardImage weightedStandardImage = weightedStandardPixelTrainer.getWeightedStandardImage();
		
		weightedStandardImage.saveKnowledge("E:/JAVA/MainProjects/GenderSearch/res/knowledge/knowledge.log");
		
		System.out.println("Operation successful!!!");
	}
}
