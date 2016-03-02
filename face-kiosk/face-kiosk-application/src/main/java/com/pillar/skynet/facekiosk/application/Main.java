package com.pillar.skynet.facekiosk.application;

import com.pillar.skynet.facekiosk.detection.FaceDetector;
import org.opencv.core.Core;
import org.opencv.core.Rect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static nu.pattern.OpenCV.loadShared;
import static org.opencv.core.Core.NATIVE_LIBRARY_NAME;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] s) {
        LOG.info("Eye see you...");
		loadShared();
		System.loadLibrary(NATIVE_LIBRARY_NAME);

		FaceDetector faceDetector =  new FaceDetector();

		Rect[] faces = faceDetector.detectFaces("/lena.png");

		if (faces.length > 0) {
			System.out.println("Found a face tho.");
		}
    }
}
