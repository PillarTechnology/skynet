package com.pillar.skynet.facekiosk.camera;

import org.bytedeco.javacv.FrameGrabber;
import org.junit.Test;

public class BuiltInCameraTest {

    @Test
    public void itCanTakeAPictureAndSaveItSomewhere() throws FrameGrabber.Exception {
        BuiltInCamera camera = new BuiltInCamera();
        camera.takePicture("/tmp/selfie.png");
    }
}
