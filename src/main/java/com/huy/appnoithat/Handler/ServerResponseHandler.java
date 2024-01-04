package com.huy.appnoithat.Handler;

import com.huy.appnoithat.Common.FXUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Scene.Login.LoginScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import javafx.application.Platform;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ServerResponseHandler {
    final static Logger LOGGER = LogManager.getLogger(ServerResponseHandler.class);
    private final UserSessionService userSessionService;
    public ServerResponseHandler() {
        userSessionService = new UserSessionService();
    }
    public void handleTokenExpired() {
        LOGGER.error("Token expired");
        PopupUtils.throwErrorNotification("Tài khoản đã hết hạn, nhấn đây để đăng nhập lại! \n" +
                        "Hãy lưu lại những công việc quan trọng", this::handleLogout, 5);
    }
    private void handleLogout() {
        userSessionService.cleanUserSession();
        Platform.runLater(() -> {
            if (!Window.getWindows().isEmpty()) {
                FXUtils.closeAll();
            }
            LoginScene loginScene = new LoginScene();
            loginScene.getLoginController().init();
            StageFactory.CreateNewUnresizeableStage(loginScene.getScene(), true);
        });
    }

}
