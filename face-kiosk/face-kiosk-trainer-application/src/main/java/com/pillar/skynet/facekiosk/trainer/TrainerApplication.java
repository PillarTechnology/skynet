package com.pillar.skynet.facekiosk.trainer;

import com.google.inject.Guice;
import com.pillar.skynet.facekiosk.capture.CaptureLifecycle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrainerApplication extends Application implements CaptureLifecycle {

    private static final Logger LOG = LoggerFactory.getLogger(TrainerApplication.class);
    private Button btn;
    private boolean running = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final TrainerRunner trainerRunner = Guice.createInjector(new TrainerInjector()).getInstance(TrainerRunner.class);

        primaryStage.setTitle("Can You See it?");
        btn = new Button();

        btn.setText("Do Stuff?'");
        btn.setOnAction(event -> {
            try {
                if (!running) {
                    trainerRunner.run(TrainerApplication.this);
                } else {
                    LOG.info("Already running... ignoring.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    @Override
    public void onStartCapture() {
        Platform.runLater(() -> {
            this.running = true;
            btn.setText("Running...");
        });
    }

    @Override
    public void onEndCapture() {
        Platform.runLater(() -> {
            this.running = false;
            btn.setText("Go again?");
        });
    }
}
