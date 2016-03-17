package com.pillar.skynet.facekiosk.application;

import com.pillar.skynet.facekiosk.camera.BuiltInCamera;
import com.pillar.skynet.facekiosk.detection.FaceDetector;
import com.pillar.skynet.facekiosk.recognition.OpenCVFaceRecognizer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_core.cvCopy;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;
import static org.bytedeco.javacpp.opencv_core.cvSetImageROI;
import static org.bytedeco.javacpp.opencv_core.cvarrToMat;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvResize;


public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] s) throws IOException, FrameGrabber.Exception {
		LOG.info("Eye see you...");
		Loader.load(opencv_objdetect.class);
		BuiltInCamera camera = new BuiltInCamera();

		FaceDetector faceDetector = new FaceDetector();
		Frame frame = camera.takePicture();
		ToIplImage toIplImage = new ToIplImage();
		IplImage photo = toIplImage.convert(frame);
		IplImage grayPhoto = IplImage.create(photo.width(), photo.height(), IPL_DEPTH_8U, 1);
		cvCvtColor(photo, grayPhoto, CV_BGR2GRAY);
		CvSeq faces = faceDetector.detectFaces(grayPhoto);

		if (faces.total() > 0) {
			LOG.info("Found a face tho.");
		}

		CvRect r = new CvRect(cvGetSeqElem(faces, 0));
		cvSetImageROI(grayPhoto, r);

		IplImage cropped = cvCreateImage(cvGetSize(grayPhoto), grayPhoto.depth(), grayPhoto.nChannels());
		cvCopy(grayPhoto, cropped);

		IplImage resizedImage = IplImage.create(350, 425, cropped.depth(), cropped.nChannels());
		cvResize(cropped, resizedImage);
		Mat face = new Mat();

		face = cvarrToMat(resizedImage, true, true, 0, face);

		OpenCVFaceRecognizer faceRecognizer = new OpenCVFaceRecognizer();
		faceRecognizer.train(Main.class.getResource("/faces/").getPath());
		String personName = faceRecognizer.predict(face);
		LOG.info("Hello " + personName);
    }
}
