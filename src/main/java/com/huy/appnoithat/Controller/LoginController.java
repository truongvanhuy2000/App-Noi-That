package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.QRScene;
import com.huy.appnoithat.Scene.RegisterScene;
import com.huy.appnoithat.Service.Login.LoginService;
import com.huy.appnoithat.Service.Register.RegisterService;
import com.huy.appnoithat.Shared.PopupUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        try {
            List<String> roleList = new ArrayList<>();
            roleList.add("ROLE_USER");

            ObservableList<String> listTime = FXCollections.observableArrayList("1 tháng", "3 tháng", "6 tháng");
            ObservableList<String> listGender = FXCollections.observableArrayList("Male", "FaMale");
            Stage loginStage = new Stage();
            Scene register = RegisterScene.getInstance().getScene();
            Scene QRpopup = QRScene.getInstance().getScene();

//element field of userManagementEditorScene
            TextField txtUsername = (TextField) register.lookup("#txtUsername");
            TextField txtName = (TextField) register.lookup("#txtName");
            TextField txtPassword = (TextField) register.lookup("#txtPassword");
            TextField txtEmail = (TextField) register.lookup("#txtEmail");
            TextField txtPhone = (TextField) register.lookup("#txtPhone");
            TextField txtAddress = (TextField) register.lookup("#txtAddress");
            ComboBox comboBoxGender = (ComboBox) register.lookup("#txtGender");
            ComboBox comboBoxTime = (ComboBox) register.lookup("#txtTime");
            comboBoxTime.setItems(listTime);
            comboBoxGender.setItems(listGender);
            Button btnSave = (Button) register.lookup("#btnSave");
            Button btnCancel = (Button) register.lookup("#btnCancel");

            loginStage.setScene(register);
            loginStage.setTitle("REGISTER ACCOUNT");
            loginStage.show();

            btnSave.setOnAction(actionEvent -> {
                String Gender = comboBoxGender.getSelectionModel().getSelectedItem().toString();
                String time = comboBoxTime.getSelectionModel().getSelectedItem().toString();
                LocalDate localDate;

                if (time.equals("1 tháng")) {
                    localDate = LocalDate.now().plusDays(30);
                } else if (time.equals("3 tháng")) {
                    localDate = LocalDate.now().plusDays(90);
                } else {
                    localDate = LocalDate.now().plusDays(180);
                }
                AccountInformation accountInformation = new AccountInformation(0, txtName.getText(), Gender, txtEmail.getText(), txtAddress.getText(), txtPhone.getText());
                Account account = new Account(0, txtUsername.getText(), txtPassword.getText(), false, accountInformation, roleList, false, localDate);
                System.out.println(account);
                RegisterService registerService = new RegisterService();
                registerService.registerNewAccount(account);
                loginStage.close();

                loginStage.setScene(QRpopup);
                loginStage.setTitle("QR Layout");
                loginStage.show();
                // You might need additional logic to handle saving or updating data
            });

            btnCancel.setOnAction(actionEvent -> {
                loginStage.close();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
