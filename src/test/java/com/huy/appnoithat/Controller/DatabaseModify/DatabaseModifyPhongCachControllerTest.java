package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseModifyPhongCachControllerTest extends Application {
    DatabaseModifyPhongCachController databaseModifyPhongCachController;
    private Thread thread;
    @BeforeEach
    void setUp() {
        thread = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(DatabaseModifyPhongCachControllerTest.class, new String[0]);
            }
        };
        thread.start();
        System.out.println("FX App thread started");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void initialize() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(DatabaseModifyPhongCachScene.getInstance().getScene());
        stage.setTitle("App Noi That");
        stage.show();
    }
}