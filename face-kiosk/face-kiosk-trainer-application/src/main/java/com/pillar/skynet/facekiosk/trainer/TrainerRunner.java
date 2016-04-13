package com.pillar.skynet.facekiosk.trainer;

import com.google.inject.Inject;
import com.pillar.skynet.facekiosk.capture.CaptureConsumer;
import com.pillar.skynet.facekiosk.capture.CaptureLifecycle;
import com.pillar.skynet.facekiosk.capture.CaptureProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

public class TrainerRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TrainerRunner.class);

    private final CaptureConsumer captureConsumer;
    private final CaptureProducer captureProducer;
    private final ExecutorService executorService;

    @Inject
    public TrainerRunner(CaptureConsumer captureConsumer, CaptureProducer captureProducer, ExecutorService executorService) {
        this.captureConsumer = captureConsumer;
        this.captureProducer = captureProducer;
        this.executorService = executorService;
    }

    public void run(final CaptureLifecycle captureLifecycle) throws InterruptedException {
        executorService.submit(new TrainerRunnerRunnable(captureLifecycle));
    }

    class TrainerRunnerRunnable implements Runnable {

        private final CaptureLifecycle captureLifecycle;

        public TrainerRunnerRunnable(CaptureLifecycle captureLifecycle) {
            this.captureLifecycle = captureLifecycle;
        }

        @Override
        public void run() {
            captureLifecycle.onStartCapture();

            LOG.info("Submitting Runnables");

            executorService.submit(captureProducer);
            executorService.submit(captureConsumer);

            LOG.info("Waiting for threads to run...");

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                LOG.warn("aborting...");
            }

            LOG.info("Shutting down.");

            captureProducer.stop();
            captureConsumer.stop();

            captureLifecycle.onEndCapture();
        }
    }
}
