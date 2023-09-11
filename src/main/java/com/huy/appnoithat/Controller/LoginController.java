package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.RegisterScene;
import com.huy.appnoithat.Service.Login.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    public void initialize() {
    }
    @FXML
    public void login(ActionEvent actionEvent) {
        String userName = usernameTextField.getText();
        String password = passwordField.getText();
        if (!loginService.Authorization(userName, password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("LOGIN FAIL!!");
            alert.setHeaderText("look, a error");
            alert.setContentText("Username or Password wrong, please input again !!");
            alert.showAndWait();
            passwordField.setText("");
            return;
//            throw new RuntimeException("Login fail");
        }
        sceneSwitcher(actionEvent);
    }


    @FXML
    void registerAccount(MouseEvent event) {
        try {

            Stage loginStage = new Stage();
            Scene register = RegisterScene.getInstance().getScene();

//element field of userManagementEditorScene
            TextField txtUsername = (TextField) register.lookup("#txtUsername");
            TextField txtRepassword = (TextField) register.lookup("#txtRepassword");
            TextField txtPassword = (TextField) register.lookup("#txtPassword");
            TextField txtEmail = (TextField) register.lookup("#txtEmail");
            TextField txtBirth = (TextField) register.lookup("#txtBirth");
            TextField txtPhone = (TextField) register.lookup("#txtPhone");
            Button btnSave = (Button) register.lookup("#btnSave");
            Button btnCancel = (Button) register.lookup("#btnCancel");

            loginStage.setScene(register);
            loginStage.setTitle("REGISTER ACCOUNT");
            loginStage.show();


            btnSave.setOnAction(actionEvent -> {

                loginStage.close();
                // You might need additional logic to handle saving or updating data
            });

            btnCancel.setOnAction(actionEvent -> {
                loginStage.close();
            });
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        stage.setResizable(false);
        if (source == LoginButton){
            scene = HomeScene.getInstance().getScene();
            HomeScene.getInstance().getHomeController().initialize();
        }
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }
}
