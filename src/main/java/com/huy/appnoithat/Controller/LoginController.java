package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Enums.Action;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.RegisterScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Service.Login.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    private Button LoginButton;
    private final LoginService loginService;

    public LoginController() {
        loginService = new LoginService();
    }

    // Call this method everytime you switch scene
    public void init() {
        usernameTextField.setOnKeyPressed(keyEvent -> {
            if (KeyboardUtils.isRightKeyCombo(Action.LOGIN, keyEvent)) {
                LoginButton.fire();
            }
        });
        passwordField.setOnKeyPressed(keyEvent -> {
            if (KeyboardUtils.isRightKeyCombo(Action.LOGIN, keyEvent)) {
                LoginButton.fire();
            }
        });
    }

    @FXML
    public void login(ActionEvent actionEvent) {
        String userName = usernameTextField.getText();
        String password = passwordField.getText();
        if (!loginService.Authorization(userName, password)) {
            PopupUtils.throwCriticalError("Sai tên đăng nhập hoặc mật khẩu");
            passwordField.setText("");
            return;
        }
        sceneSwitcher(actionEvent);
    }


    @FXML
    void registerAccount(MouseEvent event) {
        Scene scene = RegisterScene.getInstance().getScene();
        RegisterScene.getRegisterController().init();
        StageFactory.CreateNewUnresizeableStage(RegisterScene.getInstance().getScene());
    }

    private void sceneSwitcher(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        Stage stage = (Stage) ((Node) source).getScene().getWindow();
        if (source == LoginButton) {
            Scene scene = HomeScene.getInstance().getScene();
            HomeScene.getInstance().getHomeController().init();
            StageFactory.closeAndCreateNewMaximizedStage(stage, scene);
        }
    }

}
