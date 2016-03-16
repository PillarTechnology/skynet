package com.pillar.skynet.facekiosk.camera;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

public class BuiltInCamera {

	private static final ToIplImage TO_IPL_IMAGE = new ToIplImage();
	private static final ToMat TO_MAT = new ToMat();

	public Frame takePicture() throws FrameGrabber.Exception {
        OpenCVFrameGrabber openCVFrameGrabber = new OpenCVFrameGrabber(0);
        openCVFrameGrabber.start();
        return openCVFrameGrabber.grab();
    }

    public Mat convertToDetectableFrame(Frame frame) {
		return TO_MAT.convert(frame);
	}

    public void takePicture(String saveLocationFileName) throws FrameGrabber.Exception {
        Frame frame = takePicture();
        IplImage iplImage = TO_IPL_IMAGE.convert(frame);
        cvSaveImage(saveLocationFileName, iplImage);
    }

}
