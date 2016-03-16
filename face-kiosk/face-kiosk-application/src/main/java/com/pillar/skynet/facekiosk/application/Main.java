package com.pillar.skynet.facekiosk.application;

import com.pillar.skynet.facekiosk.camera.BuiltInCamera;
import com.pillar.skynet.facekiosk.detection.FaceDetector;
import com.pillar.skynet.facekiosk.recognition.OpenCVFaceRecognizer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.FrameGrabber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;


public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] s) throws IOException, FrameGrabber.Exception {
        LOG.info("Eye see you...");
		Loader.load(opencv_objdetect.class);

		FaceDetector faceDetector =  new FaceDetector();
		String imagePath = Main.class.getResource("/lena.png").getPath();
		IplImage grayImage = IplImage.create(cvLoadImage(imagePath).width(), cvLoadImage(imagePath).height(), IPL_DEPTH_8U, 1);
		cvCvtColor(cvLoadImage(imagePath), grayImage, CV_BGR2GRAY);
		CvSeq faces = faceDetector.detectFaces(grayImage);

		if (faces.total() > 0) {
			LOG.info("Found a face tho.");
		}

		BuiltInCamera camera = new BuiltInCamera();

		OpenCVFaceRecognizer faceRecognizer = new OpenCVFaceRecognizer();

		faceRecognizer.train(Main.class.getResource("/faces/").getPath());

		String personName = faceRecognizer.predict(camera.convertToDetectableFrame(camera.takePicture()));

		LOG.info("Hello " + personName);
    }
}
