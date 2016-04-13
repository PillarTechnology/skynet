package com.pillar.skynet.facekiosk.capture;

import com.google.inject.Inject;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuiltInCamera {

    private static final Logger LOG = LoggerFactory.getLogger(BuiltInCamera.class);

    private final OpenCVFrameGrabber openCVFrameGrabber;

    @Inject
    public BuiltInCamera(OpenCVFrameGrabber openCVFrameGrabber) {
        this.openCVFrameGrabber = openCVFrameGrabber;
    }

    public Frame grabFrame() throws FrameGrabber.Exception {
        LOG.trace("Grabbing frame.");
        return openCVFrameGrabber.grab();
    }
}
