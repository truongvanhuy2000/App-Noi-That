package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Service.Login.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordField;
    private LoginService loginService;
    public LoginController() {
        loginService = new LoginService();
    }
    public void login(ActionEvent actionEvent) {
        if (!loginService.Authorization(usernameTextField.getText(), passwordField.getText())) {
            return;
        }
    }
}
