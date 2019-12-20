/**
 * 
 */
package com.cv.main;

import java.util.logging.Logger;

import org.bytedeco.opencv.opencv_face.EigenFaceRecognizer;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;

import com.cv.facerec.FaceLogin;
import com.cv.facerec.Login;
import com.cv.util.Log;

/**
 * @author Daniel Fesalbon
 *
 */
@SuppressWarnings("unused")
public class Main {
	private static Logger logger = Log.getLogger(Main.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Log.LoadProject();
			new Login().setVisible(true);
		} catch (Exception e) {
			logger.info("Loading Project failed.");
			logger.warning(e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	public static FaceRecognizer FR = EigenFaceRecognizer.create();

}
