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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController{

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
            if (keyEvent.getCode() == KeyCode.ENTER) {
                LoginButton.fire();
            }
        });
        passwordField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
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
        try {
            List<String> roleList = new ArrayList<>();
            roleList.add("ROLE_USER");

            ObservableList<String> listTime = FXCollections.observableArrayList("1 tháng", "6 tháng", "12 tháng");
            ObservableList<String> listGender = FXCollections.observableArrayList("Nam", "Nữ");
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

            Label giaTienQR = (Label) QRpopup.lookup("#giaTienQR");
            loginStage.setScene(register);
            loginStage.setTitle("REGISTER ACCOUNT");
            loginStage.show();

            btnSave.setOnAction(actionEvent -> {
                String Gender = comboBoxGender.getSelectionModel().getSelectedItem().toString().equals("Nam") ? "Male" :"Female";
                String time = comboBoxTime.getSelectionModel().getSelectedItem().toString();
                LocalDate localDate;
                if (time.equals("1 tháng")) {
                    localDate = LocalDate.now().plusMonths(1);
                    giaTienQR.setText("Đăng ký tài khoản 1 tháng giá 300,000 vnđ/ tháng");
                } else if (time.equals("6 tháng")) {
                    localDate = LocalDate.now().plusMonths(7);
                    giaTienQR.setText("Đăng ký tài khoản 6 tháng giá 1,500,000 vnđ/ 6 tháng.\n" +
                            " tặng thêm 1 tháng ( 1,500,000 vnđ/7 tháng)\n");
                } else {
                    localDate = LocalDate.now().plusMonths(15);
                    giaTienQR.setText("Đăng ký tài khoản 12 tháng giá 2.400,000 vnđ/ 12 tháng.\n" +
                            " tặng thêm 3 tháng (2.400,000 vnđ/15 tháng)\n");
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
