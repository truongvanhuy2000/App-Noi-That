package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Common.FXUtils;
import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.DataModel.Token;
import com.huy.appnoithat.Enums.Action;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.Login.RegisterScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Service.Login.LoginService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;

public class LoginController {

    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    private Button LoginButton;
    @FXML
    private StackPane loadingPane;
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
        loadingPane.setDisable(true);
        loadingPane.setVisible(false);
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
        FXUtils.showLoading(loadingPane, "Đang đăng nhập, vui lòng đợi");
        new Thread(() -> {
            boolean isAuthorized = loginService.basicAuthorization(userName, password);
            Platform.runLater(() -> {
                FXUtils.hideLoading(loadingPane);
                if (!isAuthorized) {
                    // Display error popup for incorrect credentials
                    PopupUtils.throwCriticalError("Không thể đăng nhập, vui lòng kiểm tra lại thông tin");
                    passwordField.setText("");
                } else {
                    sceneSwitcher(actionEvent);
                    usernameTextField.setText("");
                    passwordField.setText("");
                }

            });
        }).start();
        // Validate the credentials using the login service
        // Switch to the next scene upon successful login

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
        StageFactory.CreateNewUnresizeableStage(registerScene.getScene(), false);
    }

    @FXML
    void loginWithToken(MouseEvent event) {
        File tokenFile = PopupUtils.fileOpener();
        if (tokenFile == null) {
            return;
        }
        Token token = Utils.readObjectFromFile(tokenFile, Token.class);
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

        goToHome(stage);
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
            goToHome(stage);
        }
    }

    private void goToHome(Stage stage) {
        Scene scene = HomeScene.getInstance().getScene();
        Stage mainStage = StageFactory.createNewMaximizedMainStage(stage, scene, true);
        HomeScene.getInstance().getHomeController().init(mainStage);
    }

}
