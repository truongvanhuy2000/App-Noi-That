package com.huy.appnoithat.Handler;

import com.huy.appnoithat.Common.FXUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Scene.Login.LoginScene;
import com.huy.appnoithat.Scene.StageFactory;
import javafx.application.Platform;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ServerResponseHandler {
    final static Logger LOGGER = LogManager.getLogger(ServerResponseHandler.class);
    public void handleTokenExpired(Runnable aditionalAction) {
        List<Window> windows = Window.getWindows();
        LOGGER.error("Token expired");
        aditionalAction.run();
        if (!windows.isEmpty()) {
            PopupUtils.throwErrorSignal("Tài khoản đã hết hạn, vui lòng đăng nhập lại!");
        }
        Platform.runLater(() -> {
            if (!windows.isEmpty()) {
                FXUtils.closeAll();
                LoginScene loginScene = new LoginScene();
                loginScene.getLoginController().init();
                StageFactory.CreateNewUnresizeableStage(loginScene.getScene(), true);
            }
        });
    }

}
