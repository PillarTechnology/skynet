package com.pillar.skynet.facekiosk.capture;

import com.google.inject.Provider;
import com.google.inject.ProvisionException;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class OpenCVFrameGrabberProvider implements Provider<OpenCVFrameGrabber> {

    @Override
    public OpenCVFrameGrabber get() {
        OpenCVFrameGrabber openCVFrameGrabber = new OpenCVFrameGrabber(0);

        try {
            openCVFrameGrabber.start();
        } catch (FrameGrabber.Exception e) {
            throw new ProvisionException("Unable to start OpenCVFrameGrabber", e);
        }

        return openCVFrameGrabber;
    }

}
