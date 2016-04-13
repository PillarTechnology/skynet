package com.pillar.skynet.facekiosk.capture;

import com.google.inject.Inject;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

public class FrameWriter {

    private static final Logger LOG = LoggerFactory.getLogger(FrameWriter.class);

    private final OpenCVFrameConverter.ToIplImage toIplImage;
    private final CaptureConfiguration captureConfiguration;

    @Inject
    public FrameWriter(CaptureConfiguration captureConfiguration, OpenCVFrameConverter.ToIplImage toIplImage) {
        this.captureConfiguration = captureConfiguration;
        this.toIplImage = toIplImage;
    }

    void write(Frame frame, String id) {
        String saveLocationFileName = new File(captureConfiguration.getCaptureRoot(), id + ".jpg").getAbsolutePath();
        opencv_core.IplImage iplImage = toIplImage.convert(frame);

        LOG.debug("Writing frame to {}", saveLocationFileName);

        cvSaveImage(saveLocationFileName, iplImage);
    }

}
