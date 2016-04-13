package com.pillar.skynet.facekiosk.capture;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CaptureProducer extends StoppableRunnable {

    private static final Logger LOG = LoggerFactory.getLogger(CaptureProducer.class);

    private final BuiltInCamera buildInCamera;
    private final BlockingQueue<Frame> frameQueue;

    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    @Inject
    public CaptureProducer(BuiltInCamera builtInCamera, @Named("capture_queue") LinkedBlockingQueue<Frame> frameQueue) {
        this.buildInCamera = builtInCamera;
        this.frameQueue = frameQueue;
    }

    @Override
    public void run() {
        this.start();

        LOG.info("Starting producer, using queue " + System.identityHashCode(frameQueue));

        try {
            while (isRunning()) {
                Frame frame = buildInCamera.grabFrame();

                LOG.debug("Offering frame: {}", frame);

                frameQueue.put(frame);

                timeUnit.sleep(100);
            }
        } catch (FrameGrabber.Exception e) {
            LOG.error("Unable to grab frame and add to queue.", e);
        } catch (InterruptedException e) {
            LOG.info("Aborting thread...");
        }
    }

}
