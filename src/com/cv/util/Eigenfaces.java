/**
 * 
 */
package com.cv.util;

import static org.bytedeco.opencv.global.opencv_core.CV_32SC1;
import static org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import java.util.logging.Logger;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.opencv.imgproc.Imgproc;

import com.cv.main.Defaults;

/**
 * @author Daniel Fesalbon
 *
 */
public class Eigenfaces {

	Logger logger = Log.getLogger(Eigenfaces.class.getName());

	File root;

	MatVector images;

	Mat labels;

	public FaceRecognizer getEigenfaces(FaceRecognizer faceRecognizer) {
		try {
			/* ! [Training dataset(model)] */
			root = new File(Defaults.IMAGES);

			/* ! [Filter file get all images] */
			FilenameFilter imgFilter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					name = name.toLowerCase();
					return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
				}
			};

			File[] imageFiles = root.listFiles(imgFilter);

			this.images = new MatVector(imageFiles.length);

			this.labels = new Mat(imageFiles.length, 1, CV_32SC1);
		

			IntBuffer labelsBuf = labels.createBuffer();

			int counter = 0;

			/* ! [Loop all images to train] */
			for (File image : imageFiles) {
				Mat img = imread(image.getAbsolutePath(), IMREAD_GRAYSCALE);
				int label = Integer.parseInt(image.getName().split("\\.")[0]);
				images.put(counter, img);
				labelsBuf.put(counter, label);
				counter++;
			}

			faceRecognizer.train(images, labels);

			return faceRecognizer;

		} catch (Exception e) {
			logger.warning(e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	public FaceRecognizer train(FaceRecognizer fr) {
		return fr;
	}

	public Integer predict(Mat src, FaceRecognizer faceRecognizer) {
		try {
			cvtColor(src, src, Imgproc.COLOR_RGB2GRAY);
			IntPointer label = new IntPointer(1);
			DoublePointer confidence = new DoublePointer(1);
			faceRecognizer.predict(src, label, confidence);
			int predictedLabel = label.get(0);
//			logger.info("Eigen prediction: Detected face recognized as " + predictedLabel);
			return predictedLabel;
		} catch (Exception e) {
			logger.warning(e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	public Integer predict(byte[] data, FaceRecognizer faceRecognize) {
		Mat mat = new Mat(data);
		try {
			cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY);
			IntPointer label = new IntPointer(1);
			DoublePointer confidence = new DoublePointer(1);
			faceRecognize.predict(mat, label, confidence);
			int predictedLabel = label.get(0);
//			logger.info("Eigen prediction: Detected face recognized as " + predictedLabel);
			return predictedLabel;
		} catch (Exception e) {
			logger.warning(e.getMessage());
			e.printStackTrace();
			// TODO: handle exception
		}

		return null;
	}

}
