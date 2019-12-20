/**
 * 
 */
package com.cv.demo;



import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_objdetect.*;

import com.cv.main.Defaults;


/**
 * @author Daniel Fesalbon
 *
 */
public class FaceDetection {
	
	CascadeClassifier faceDetector = new CascadeClassifier(Defaults.LBPCSCDE_FRNTLFACE_CLASSFR);
	RectVector face = new RectVector();
	Mat img = new Mat();
	
	public void test() {
		faceDetector.detectMultiScale(img, face);
	}

}
