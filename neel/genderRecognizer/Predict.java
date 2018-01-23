package genderRecognizer;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;

import weightedPixal.WeightedStandardPixelTrainer;



public class Predict {
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		WeightedStandardPixelTrainer weightedStandardPixelTrainer = new WeightedStandardPixelTrainer();

		//sample file
		String imageFilePath = "E:/JAVA/MainProjects/GenderSearch/res/genderTest/sample/sample3.jpg";
		Mat[] faces = new FaceDetector().snipFace(imageFilePath, new Size(90, 90));
		
		
		//experience file
		weightedStandardPixelTrainer.load("E:/JAVA/MainProjects/GenderSearch/res/knowledge/knowledge.log");
		
		int faceNo=1;
		for(Mat face: faces){
			
			int prediction = weightedStandardPixelTrainer.predict(face);
			
			if(prediction==-1){
				System.out.println("I think " + faceNo + " is not a face.");
				Highgui.imwrite("E:/JAVA/MainProjects/GenderSearch/res/genderTest/sample/" + faceNo + "_noface.jpg", face);
			}else if(prediction==0){
				System.out.println("I think " + faceNo + " is a female.");
				Highgui.imwrite("E:/JAVA/MainProjects/GenderSearch/res/genderTest/sample/" + faceNo + "_female.jpg", face);
			}else{
				System.out.println("I think " + faceNo + " is a male.");
				Highgui.imwrite("E:/JAVA/MainProjects/GenderSearch/res/genderTest/sample/" + faceNo + "_male.jpg", face);
			}
			
			faceNo++;
		}
		
		System.out.println("Operation Successful!!!");
	}
}
