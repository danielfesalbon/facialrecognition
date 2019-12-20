/**
 * 
 */
package com.cv.demo;

import static org.bytedeco.opencv.global.opencv_core.CV_32SC1;
import static org.bytedeco.opencv.global.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_face.EigenFaceRecognizer;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;

/**
 * @author Daniel Fesalbon
 *
 */
public class Recognizer {

	public File root;

	MatVector images;

	Mat labels;

	@SuppressWarnings("unused")
	public void init(String datasetdir, String sourcedir) {
//		String dir = "C:\\Users\\Daniel Fesalbon\\Desktop\\img";
//		String imgdir = "C:\\Users\\Daniel Fesalbon\\Desktop\\Pics\\1.jpg";

		String dir = datasetdir;
		String imgdir = sourcedir;
		Mat testImage = imread(imgdir, IMREAD_GRAYSCALE);
		root = new File(dir);

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

		for (File image : imageFiles) {
			Mat img = imread(image.getAbsolutePath(), IMREAD_GRAYSCALE);
			int label = Integer.parseInt(image.getName().split("\\.")[0]);
			images.put(counter, img);
			labelsBuf.put(counter, label);
			counter++;
		}

//		FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
//		 FaceRecognizer faceRecognizer = FisherFaceRecognizer.create();
		FaceRecognizer faceRecognizer = EigenFaceRecognizer.create();

		faceRecognizer.train(images, labels);

		IntPointer label = new IntPointer(1);
		DoublePointer confidence = new DoublePointer(1);
		faceRecognizer.predict(testImage, label, confidence);
		int predictedLabel = label.get(0);
	}
}
