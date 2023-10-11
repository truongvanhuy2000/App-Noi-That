package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.Enums.Action;
import com.huy.appnoithat.HelloApplication;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.QRScene;
import com.huy.appnoithat.Scene.RegisterScene;
import com.huy.appnoithat.Service.Login.LoginService;
import com.huy.appnoithat.Service.Register.RegisterService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public void initialize() {
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
        Stage stage = new Stage();
        stage.setScene(RegisterScene.getInstance().getScene());
        RegisterScene.getRegisterController().init();
        stage.setTitle("REGISTER ACCOUNT");
        stage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("/com/huy/appnoithat/Scene/icons/logoapp.jpg")));
        stage.show();
    }

    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node) source).getScene().getWindow();
        if (source == LoginButton) {
            scene = HomeScene.getInstance().getScene();
            HomeScene.getInstance().getHomeController().initialize();
        } else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }
}
