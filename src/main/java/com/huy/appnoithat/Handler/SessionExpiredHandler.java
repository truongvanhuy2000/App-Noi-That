package com.huy.appnoithat.Handler;

import com.google.inject.Inject;
import com.huy.appnoithat.Common.FXUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.IOC.DIContainer;
import com.huy.appnoithat.Scene.Login.LoginScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Session.UserSessionManager;
import javafx.application.Platform;
import javafx.stage.Window;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SessionExpiredHandler {
    final static Logger LOGGER = LogManager.getLogger(SessionExpiredHandler.class);
    private final UserSessionManager userSessionManager;

    public void handleTokenExpired() {
        LOGGER.error("Token expired");
        PopupUtils.throwErrorNotification("Tài khoản đã hết hạn!", "Nhấn đây để đăng nhập lại! \n" +
                "Hãy lưu lại những công việc quan trọng", this::handleLogout, 5);
    }

    private void handleLogout() {
        userSessionManager.cleanUserSession();
        Platform.runLater(() -> {
            if (!Window.getWindows().isEmpty()) {
                FXUtils.closeAll();
            }
            LoginScene loginScene = DIContainer.get();
            loginScene.getLoginController().init();
            StageFactory.CreateNewUnresizeableStage(loginScene, true);
        });
    }

}
