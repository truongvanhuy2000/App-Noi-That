package com.huy.appnoithat.Handler;

import com.huy.appnoithat.Common.FXUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Scene.Login.LoginScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Session.UserSessionService;
import javafx.application.Platform;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionExpiredHandler {
    final static Logger LOGGER = LogManager.getLogger(SessionExpiredHandler.class);

    public void handleTokenExpired() {
        LOGGER.error("Token expired");
        PopupUtils.throwErrorNotification("Tài khoản đã hết hạn!", "Nhấn đây để đăng nhập lại! \n" +
                        "Hãy lưu lại những công việc quan trọng", SessionExpiredHandler::handleLogout, 5);
    }
    private static void handleLogout() {
        UserSessionService userSessionService = new UserSessionService();
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
