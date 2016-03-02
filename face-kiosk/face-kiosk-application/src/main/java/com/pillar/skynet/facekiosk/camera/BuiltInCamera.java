package com.pillar.skynet.facekiosk.camera;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

public class BuiltInCamera {

    public Frame takePicture() throws FrameGrabber.Exception {
        OpenCVFrameGrabber openCVFrameGrabber = new OpenCVFrameGrabber(0);
        openCVFrameGrabber.start();
        return openCVFrameGrabber.grab();
    }

    public void takePicture(String saveLocationFileName) throws FrameGrabber.Exception {
        Frame frame = takePicture();
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        opencv_core.IplImage iplImage = converter.convert(frame);
        cvSaveImage(saveLocationFileName, iplImage);
    }

}
