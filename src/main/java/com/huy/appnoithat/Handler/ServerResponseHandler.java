package com.huy.appnoithat.Handler;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Scene.LoginScene;
import com.huy.appnoithat.Scene.StageFactory;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ServerResponseHandler {
    final static Logger LOGGER = LogManager.getLogger(ServerResponseHandler.class);
    public void handleTokenExpired(Runnable aditionalAction) {
        LOGGER.error("Token expired");
        aditionalAction.run();
        PopupUtils.throwErrorSignal("Tài khoản đã hết hạn, vui lòng đăng nhập lại!");
        Platform.runLater(() -> {
            closeAll();
            LoginScene loginScene = new LoginScene();
            loginScene.getLoginController().init();
            StageFactory.CreateNewUnresizeableStage(loginScene.getScene());
        });
    }
    private void closeAll() {
        List<Window> windows = Window.getWindows();
        for (int index = 0; index < windows.size(); index ++) {
            Window window = windows.get(index);
            if (window != null) {
                Stage stage = (Stage) window;
                stage.close();
            }
        }
    }
}
