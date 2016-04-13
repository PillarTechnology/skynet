package com.pillar.skynet.facekiosk.application;

import org.bytedeco.javacv.FrameGrabber;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class MainTest {

    @Test
    @Ignore
    public void shouldHavePublicStaticVoidMainMethod() throws IOException, FrameGrabber.Exception {
        Main.main(new String[] {});
    }

}