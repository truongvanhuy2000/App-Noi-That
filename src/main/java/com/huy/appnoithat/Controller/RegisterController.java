package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.Scene.QRScene;
import com.huy.appnoithat.Service.Register.RegisterService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegisterController {
    @FXML
    private ComboBox<String> ComboBoxGender;
    @FXML
    private ComboBox<String> ComboBoxTime;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnCheckUserName;
    @FXML
    private Button btnSave;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtUsername;
    private final RegisterService registerService;
    public RegisterController() {
        registerService = new RegisterService();
    }
    public void init() {
        List<String> roleList = new ArrayList<>();
        roleList.add("ROLE_USER");

        ObservableList<String> listTime = FXCollections.observableArrayList("1 tháng", "6 + 1 tháng", "12 + 3 tháng");
        ObservableList<String> listGender = FXCollections.observableArrayList("Nam", "Nữ");

        ComboBoxTime.setItems(listTime);
        ComboBoxGender.setItems(listGender);

        btnSave.setDisable(true);
        btnSave.setOnAction(actionEvent -> {
            Scene QRpopup = QRScene.getInstance().getScene();
            Label giaTienQR = (Label) QRpopup.lookup("#giaTienQR");

            String Gender = ComboBoxGender.getSelectionModel().getSelectedItem().equals("Nam") ? "Male" :"Female";
            String time = ComboBoxTime.getSelectionModel().getSelectedItem();
            LocalDate localDate;
            if (time.equals("1 tháng")) {
                localDate = LocalDate.now().plusMonths(1);
                giaTienQR.setText("Đăng ký tài khoản 1 tháng giá 300,000 vnđ/ tháng");
            } else if (time.equals("6 + 1 tháng")) {
                localDate = LocalDate.now().plusMonths(7);
                giaTienQR.setText("Đăng ký tài khoản 6 tháng giá 1,500,000 vnđ/ 6 tháng.\n" +
                        " Tặng thêm 1 tháng ( 1,500,000 vnđ/7 tháng)\n");
            } else {
                localDate = LocalDate.now().plusMonths(15);
                giaTienQR.setText("Đăng ký tài khoản 12 tháng giá 2.400,000 vnđ/ 12 tháng.\n" +
                        " Tặng thêm 3 tháng (2.400,000 vnđ/15 tháng)\n");
            }
            AccountInformation accountInformation = new AccountInformation(0, txtName.getText(), Gender, txtEmail.getText(), txtAddress.getText(), txtPhone.getText());
            Account account = new Account(0, txtUsername.getText(), txtPassword.getText(), false, accountInformation, roleList, false, localDate);
            System.out.println(account);

            registerService.registerNewAccount(account);

            Stage thisStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            thisStage.close();

            Stage QRstage = new Stage();
            QRstage.setScene(QRpopup);
            QRstage.setTitle("Quét QR để thanh toán");
            QRstage.show();
            // You might need additional logic to handle saving or updating data
        });
        btnCheckUserName.setOnAction(actionEvent -> {
            if (!registerService.isUsernameValid(txtUsername.getText())) {
                btnSave.setDisable(true);
                PopupUtils.throwCriticalError("Tên đăng nhập đã tồn tại");
            } else {
                btnSave.setDisable(false);
                PopupUtils.throwSuccessSignal("Tên đăng nhập có thể sử dụng");
            }
        });
        btnCheckUserName.setOnInputMethodTextChanged(inputMethodEvent -> {
            btnSave.setDisable(true);
        });
        btnCancel.setOnAction(actionEvent -> {
            Stage thisStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            thisStage.close();
        });
    }

}
