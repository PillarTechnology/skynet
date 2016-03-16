package com.pillar.skynet.facekiosk.detection;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.bytedeco.javacpp.helper.opencv_objdetect.cvHaarDetectObjects;
import static org.bytedeco.javacpp.opencv_core.cvLoad;


public class FaceDetector {

	private CvHaarClassifierCascade faceCascade;

	public FaceDetector() throws IOException {
		faceCascade = buildClassifierCascade();
	}

	public CvSeq detectFaces(IplImage image) {
		CvMemStorage storage = CvMemStorage.create();

		System.out.println("Trying to detect a face.");

		// Detect faces in the image.
		CvSeq sign = cvHaarDetectObjects(image, faceCascade, storage, 1.5, 3, opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING);

		return sign;
	}

	private CvHaarClassifierCascade buildClassifierCascade() throws IOException {
		//TODO: Put this in our file system.
		URL url = new URL("https://raw.github.com/Itseez/opencv/2.4.0/data/haarcascades/haarcascade_frontalface_alt.xml");
		File file = Loader.extractResource(url, null, "classifier", ".xml");
		file.deleteOnExit();
		String classifierName = file.getAbsolutePath();

		// We can "cast" Pointer objects by instantiating a new object of the desired class.
		CvHaarClassifierCascade classifier = new CvHaarClassifierCascade(cvLoad(classifierName));
		if (classifier.isNull()) {
			System.err.println("Error loading classifier file \"" + classifierName + "\".");
			System.exit(1);
		}
		return classifier;
	}
}
