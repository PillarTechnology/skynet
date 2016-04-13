package com.pillar.skynet.facekiosk.trainer;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.pillar.skynet.facekiosk.capture.CaptureConfiguration;
import com.pillar.skynet.facekiosk.capture.OpenCVFrameGrabberProvider;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


public class TrainerInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(CaptureConfiguration.class).toInstance(captureConfiguration());
        bind(OpenCVFrameGrabber.class).toProvider(OpenCVFrameGrabberProvider.class);
        bind(new TypeLiteral<LinkedBlockingQueue<Frame>>(){}).annotatedWith(Names.named("capture_queue")).toInstance(new LinkedBlockingQueue<Frame>(10));
        bind(ExecutorService.class).toInstance(Executors.newFixedThreadPool(5));
    }

    private CaptureConfiguration captureConfiguration() {
        long millis = System.currentTimeMillis();
        final File file = new File("/tmp/captures-" + millis);

        file.mkdir();

        return CaptureConfiguration.builder().captureRoot(file).build();
    }

}
