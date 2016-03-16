package com.pillar.skynet.facekiosk.recognition;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_core.MatVector;
import static org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import static org.bytedeco.javacpp.opencv_face.createFisherFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

public class OpenCVFaceRecognizer {

	private FaceRecognizer faceRecognizer;

	public OpenCVFaceRecognizer() {
		faceRecognizer = createFisherFaceRecognizer();
//		faceRecognizer.load("/trainingdata.xml");
		//createEigenFaceRecognizer();
		//createLBPHFaceRecognizer();
	}

	private Map<Integer, String> labelMap = new HashMap<Integer, String>();

	public void train(String trainingDirectoryPath) {
		String trainingDir = trainingDirectoryPath;

		File root = new File(trainingDir);

		FilenameFilter imgFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				name = name.toLowerCase();
				return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
			}
		};

		File[] imageFiles = root.listFiles(imgFilter);

		MatVector images = new MatVector(imageFiles.length);

		Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);

		IntBuffer labelsBuf = labels.createBuffer();

		int counter = 0;
		for (File image : imageFiles) {
			Mat img = imread(image.getAbsolutePath(), CV_LOAD_IMAGE_GRAYSCALE);
			String[] fileNameSegments = image.getName().split("\\-");

			int labelId = Integer.parseInt(fileNameSegments[0]);

			//If it doesn't exist, make a new mapping for the label id
			if (!labelMap.containsKey(labelId)) {
				labelMap.put(labelId, fileNameSegments[1]);
			}

			images.put(counter, img);

			labelsBuf.put(counter, labelId);

			counter++;
		}

		faceRecognizer.train(images, labels);
//		faceRecognizer.save("/trainingdata.xml");
	}

	public String predict(Mat faceImage) {
		//predict returns an int, so we need to map it to a name
		return labelMap.getOrDefault(faceRecognizer.predict(faceImage), "Anonymous");
	}
}
