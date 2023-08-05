package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.LuaChonNoiThatScene;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
public class LuaChonNoiThatControllerTest extends Application {
    LuaChonNoiThatController luaChonNoiThatController;
    private Thread thread;
    @Test
    void initialize() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testInitialize() {
    }

    @BeforeEach
    void setUp() {
        thread = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(LuaChonNoiThatControllerTest.class, new String[0]);
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

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(LuaChonNoiThatScene.getInstance().getScene());
        stage.setTitle("App Noi That");
        stage.show();
    }
}