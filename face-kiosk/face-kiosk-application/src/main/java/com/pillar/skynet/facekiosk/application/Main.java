package com.pillar.skynet.facekiosk.application;

import com.pillar.skynet.facekiosk.camera.BuiltInCamera;
import com.pillar.skynet.facekiosk.detection.FaceDetector;
import com.pillar.skynet.facekiosk.recognition.OpenCVFaceRecognizer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
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
		BuiltInCamera camera = new BuiltInCamera();

		FaceDetector faceDetector =  new FaceDetector();
		Frame frame = camera.takePicture();
		ToIplImage toIplImage = new ToIplImage();
		IplImage photo = toIplImage.convert(frame);
		IplImage grayPhoto = IplImage.create(photo.width(), photo.height(), IPL_DEPTH_8U, 1);
		cvCvtColor(photo, grayPhoto, CV_BGR2GRAY);
		CvSeq faces = faceDetector.detectFaces(grayPhoto);

		if (faces.total() > 0) {
			LOG.info("Found a face tho.");
		}


//		OpenCVFaceRecognizer faceRecognizer = new OpenCVFaceRecognizer();
//		//TODO: Need to resize picture to match training data before attempting to predict which face it is.
//		faceRecognizer.train(Main.class.getResource("/faces/").getPath());
//		String personName = faceRecognizer.predict());
//		LOG.info("Hello " + personName);
    }
}
