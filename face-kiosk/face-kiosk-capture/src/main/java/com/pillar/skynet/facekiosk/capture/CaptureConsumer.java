package com.pillar.skynet.facekiosk.capture;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.bytedeco.javacv.Frame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CaptureConsumer extends StoppableRunnable {

    private static final Logger LOG = LoggerFactory.getLogger(CaptureConsumer.class);

    private final BlockingQueue<Frame> frameQueue;
    private final FrameWriter frameWriter;

    @Inject
    public CaptureConsumer(FrameWriter frameWriter, @Named("capture_queue") LinkedBlockingQueue<Frame> frameQueue) {
        this.frameQueue = frameQueue;
        this.frameWriter = frameWriter;
    }

    @Override
    public void run() {
        this.start();

        LOG.info("Starting consumer, using queue " + System.identityHashCode(frameQueue));

        int i = 0;
        try {
            Frame frame = pollFrame();

            while(isRunning()) {
                do {
                    LOG.debug("Writing frame {}", frame);

                    frameWriter.write(frame, i + "-thing");
                    frame = pollFrame();
                    i++;
                } while (frame != null);
            }
        } catch (InterruptedException e) {
            LOG.info("Shutting down...");
        }
    }

    private Frame pollFrame() throws InterruptedException {
        LOG.info("Polling frame.");
        return frameQueue.poll(100, TimeUnit.MILLISECONDS);
    }
}
