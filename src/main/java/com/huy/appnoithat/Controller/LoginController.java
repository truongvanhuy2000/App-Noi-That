package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
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

import java.io.File;

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


    /**
     * Initializes the login form, enabling keyboard shortcuts for the login action.
     * Fires the login button event when the specified key combination is pressed.
     * Call this method everytime you switch scene
     */
    public void init() {
        // Enable keyboard shortcut for login when ENTER key is pressed in username or password field
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

    /**
     * Handles the login action when the login button is clicked.
     * Retrieves username and password from input fields, validates the credentials,
     * displays an error popup if the credentials are incorrect, and switches scenes upon successful login.
     *
     * @param actionEvent The ActionEvent triggering the login button click.
     */
    @FXML
    public void login(ActionEvent actionEvent) {
        String userName = usernameTextField.getText();
        String password = passwordField.getText();

        // Validate the credentials using the login service
        if (!loginService.Authorization(userName, password)) {
            // Display error popup for incorrect credentials
            PopupUtils.throwCriticalError("Không thể đăng nhập (Sai tên đăng nhập, mật khẩu, tài khoản hết hạn,...)");
            passwordField.setText("");
            return;
        }

        // Switch to the next scene upon successful login
        sceneSwitcher(actionEvent);
        usernameTextField.setText("");
        passwordField.setText("");
    }

    /**
     * Handles the event when the user wants to register a new account.
     * Initializes the registration scene, creates a new unresizable stage, and displays the registration form.
     *
     * @param event The MouseEvent triggering the registration action.
     */
    @FXML
    void registerAccount(MouseEvent event) {
        RegisterScene registerScene = new RegisterScene();
        Scene scene = registerScene.getScene();
        RegisterScene.getRegisterController().init();
        StageFactory.CreateNewUnresizeableStage(registerScene.getScene());
    }

    @FXML
    void loginWithToken(MouseEvent event) {
        File tokenFile = PopupUtils.fileOpener();
        if (tokenFile == null) {
            return;
        }
        String token = Utils.readFromFile(tokenFile);
        if (token == null) {
            PopupUtils.throwCriticalError("Chữ ký điện tử không hợp lệ");
            return;
        }
        if (!loginService.authorizeWithToken(token)) {
            // Display error popup for incorrect credentials
            PopupUtils.throwCriticalError("Chữ ký điện tử không hợp lệ");
            return;
        }
        // Switch to the next scene upon successful login
        Object source = event.getSource();
        Stage stage = (Stage) ((Node) source).getScene().getWindow();

        Scene scene = HomeScene.getInstance().getScene();
        HomeScene.getInstance().getHomeController().init();
        StageFactory.closeAndCreateNewMaximizedStage(stage, scene);
    }

    /**
     * Handles scene switching based on the action event source.
     *
     * @param actionEvent The ActionEvent triggering the scene switch.
     */
    private void sceneSwitcher(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        Stage stage = (Stage) ((Node) source).getScene().getWindow();
        if (source == LoginButton) {
            // Switch to the home scene if the Login button is clicked
            Scene scene = HomeScene.getInstance().getScene();
            HomeScene.getInstance().getHomeController().init();
            StageFactory.closeAndCreateNewMaximizedStage(stage, scene);
        }
    }

}
