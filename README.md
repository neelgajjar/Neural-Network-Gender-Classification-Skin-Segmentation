# Gender Classification -

Face detection using haar cascade classifier carried out on standard database using OpenCV. Achieved 100% face detection rate on simple background and 93.24% detection rate on complex background. Haar cascade classifier provides high accuracy even the images are highly affected by the illumination. The haar cascade classifier has shown superior performance with simple background images.

### Gender detection  
Gender detection is a part of face identification. When we see at the person’s face, can get the information such as the expression, gender, age and ethnicity. Face detection is useful in many applications such as surveillance system, human machine interaction, biometrics, gender classification etc.
### Idea!  
A digital image is made up of finite number of elements each of which has a particular location and value. These elements are known as pixel and picture element. These elements take participation to find out the face. Face detection method can be broadly classified into two categories: Appearance based approach and Feature based approach. In the Appearance-based approach, the whole image is used as a input to the face detector. In Feature-based approach face detection is based on the features extracted from an image. Features can be skin color or edges and sometimes they have a knowledge of the face geometry. The appearance-based approach which I used in this project to identify the face from an image using haar cascade classifier.
Initially, the algorithm needs a lot of positive images (images of faces) and negative images (images without faces) to train the classifier. Then we need to extract features from it. For this, haar features shown in below image are used. They are just like our convolutional kernel. Each feature is a single value obtained by subtracting sum of pixels under white rectangle from sum of pixels under black rectangle  
![haar_features](https://user-images.githubusercontent.com/8587332/35300761-52519922-003e-11e8-9384-6c7bccef5a9b.jpg)  
Each feature result in a single value which is calculated by subtracting the sum of pixels under white rectangle from the sum of pixels under black rectangle as shown in Haar like features are the rectangle features for rapid face detection.
The Haar feature starts scanning the image for the detection of the face from the top left corner and ends the face
detection process bottom right corner of the image as shown in fig 2. The image is scanned from top left corner to the bottom right corner several times through the haar like features in order to detect the face.  
![image](https://user-images.githubusercontent.com/8587332/35301180-b3cdf5b4-003f-11e8-8694-2e74cdb83683.png)   
OpenCV is used to implement the haar cascade classifier and Raw pixel data is fed to the Neural Network to train classifier using Template Matching feature extraction.  
### Input-
![sample3](https://user-images.githubusercontent.com/8587332/35301446-67eb07c6-0040-11e8-8c88-cdc2ab300b97.jpg) 
### Result-
![1_male](https://user-images.githubusercontent.com/8587332/35301447-68028626-0040-11e8-9b4b-233cf5439d68.jpg) 1. Male
![2_female](https://user-images.githubusercontent.com/8587332/35301448-6819e082-0040-11e8-94ba-8b0f56da3707.jpg) 1. Female

# Skin Segmentation -

### Detects Human Skin From Image
This is a simple machine learning implementation for image region segmentation. Only by altering training data it can detect any type of region based on pixel value.

## How it's done?
Used Naive Bayes here for classification (skin or non-skin pixel). As it is a colour image there are 256*256*256 types of pixels.

In the training phase, pixel frequencies of being skin or non-skin is calculated. We take every pixel of the image and see if it is a pixel of skin by using the mask. If the pixel is on skin, we increase its skin-frequency. Else we increase the non-skin-frequency. After processing all images, probability of a skin-pixels is calculated from the frequency using Bayes Theorem. We store this data in a file.

During testing, we simply map each pixel with the probability we calculated in training phase. If the probability is greater than a certain threshold, we mark that pixel as skin.

1. For training the system run SkinDetectorTrainer.java. After training a knowledge file is created.
![cbaa3d56-bb48-11e6-9277-c236eaf23b9b](https://user-images.githubusercontent.com/8587332/35302040-118fc9fa-0042-11e8-9d1d-834e8b89c283.png)
2. Then run SkinDetectorTester.java or SkinDetectorTester2.java for getting output (change file-paths in the main method according to the need).  

### Input-<br>  <img src="https://user-images.githubusercontent.com/8587332/35302077-2aced3b6-0042-11e8-900c-701c6a3bba4e.jpg" width="250" height="300">
### Output-<br>
<img src="https://user-images.githubusercontent.com/8587332/35302124-4624f58c-0042-11e8-886b-7dcb0c594407.png" width="300" height="300"> <img src="https://user-images.githubusercontent.com/8587332/35303047-7f0db214-0045-11e8-9b66-9add53e41d96.png" width="300" height="300"> </br>  
