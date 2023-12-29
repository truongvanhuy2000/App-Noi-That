package com.huy.appnoithat.Controller.Register;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.Scene.Login.QRScene;
import com.huy.appnoithat.Service.Register.RegisterService;
import javafx.application.Platform;
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
    private static final String TIME_OPTION1 = "1 tháng";
    private static final String TIME_OPTION2 = "6 + 1 tháng";
    private static final String TIME_OPTION3 = "12 + 3 tháng";
    private static final String GENDER_OPTION1 = "Nam";
    private static final String GENDER_OPTION2 = "Nữ";
    @FXML
    private ComboBox<String> ComboBoxGender, ComboBoxTime;
    @FXML
    private Button btnCheckUserName, btnSave, btnCancel;
    @FXML
    private TextField txtAddress, txtEmail, txtName, txtPassword, txtPhone, txtUsername;
    private final RegisterService registerService;
    public RegisterController() {
        registerService = new RegisterService();
    }
    /**
     * Initializes the registration process.
     * Sets up initial values for ComboBoxes and disables the save button by default.
     * Handles the registration of a new account when the save button is clicked.
     * Displays a QR code for payment after successful registration.
     */
    public void init() {
        // Define a list of user roles
        List<String> roleList = new ArrayList<>();
        roleList.add("ROLE_USER");

        // Set up options for ComboBoxes
        ObservableList<String> listTime = FXCollections.observableArrayList(TIME_OPTION1, TIME_OPTION2, TIME_OPTION3);
        ObservableList<String> listGender = FXCollections.observableArrayList(GENDER_OPTION1, GENDER_OPTION2);

        // Populate ComboBoxes with options
        ComboBoxTime.setItems(listTime);
        ComboBoxGender.setItems(listGender);

        btnSave.setDisable(true);
        btnSave.setOnAction(actionEvent -> {
            // Get the QR scene and elements from the QR popup
            Scene QRpopup = QRScene.getInstance().getScene();
            Label giaTienQR = (Label) QRpopup.lookup("#giaTienQR");
            String Gender = "Male";
            if(ComboBoxGender.getSelectionModel().getSelectedItem()!=null){
                 Gender = ComboBoxGender.getSelectionModel().getSelectedItem();
            }
            // Retrieve selected gender, time option, and current date
            String time = TIME_OPTION1;
            if(ComboBoxTime.getSelectionModel().getSelectedItem()!=null){
                time = ComboBoxTime.getSelectionModel().getSelectedItem();
            }
            LocalDate localDate = LocalDate.now();

            // Set expiration date based on the selected time option
            switch (time) {
                case TIME_OPTION1 -> {
                    localDate = LocalDate.now().plusMonths(1);
                    giaTienQR.setText("Đăng ký tài khoản 1 tháng giá 300,000 vnđ/ tháng");
                }
                case TIME_OPTION2 -> {
                    localDate = LocalDate.now().plusMonths(7);
                    giaTienQR.setText("Đăng ký tài khoản 6 tháng giá 1,500,000 vnđ/ 6 tháng.\n" +
                            " Tặng thêm 1 tháng ( 1,500,000 vnđ/7 tháng)\n");
                }
                case TIME_OPTION3 -> {
                    localDate = LocalDate.now().plusMonths(15);
                    giaTienQR.setText("Đăng ký tài khoản 12 tháng giá 2.400,000 vnđ/ 12 tháng.\n" +
                            " Tặng thêm 3 tháng (2.400,000 vnđ/15 tháng)\n");
                }
            }
            AccountInformation accountInformation = new AccountInformation(0, txtName.getText(), Gender, txtEmail.getText(), txtAddress.getText(), txtPhone.getText());
            Account account = new Account(0, txtUsername.getText(), txtPassword.getText(), false, accountInformation, roleList, false, localDate);

            registerService.registerNewAccount(account);

            // Close the current registration window
            Stage thisStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            thisStage.close();

            // Display a new window with the QR code for payment
            Stage QRstage = new Stage();
            QRstage.setScene(QRpopup);
            QRstage.setTitle("Quét QR để thanh toán");
            QRstage.show();
            // You might need additional logic to handle saving or updating data
        });

        btnCheckUserName.setOnAction(actionEvent -> {
            if (!isValidCharacter(txtUsername.getText())) {
                PopupUtils.throwErrorNotification("Tên đăng nhập không hợp lệ\n" +
                        "Tên đăng nhập phải có độ dài từ 8 đến 20 ký tự\n" +
                        "Tên đăng nhập không được chứa ký tự đặc biệt");
                btnSave.setDisable(true);
                return;
            }
            if (!registerService.isUsernameValid(txtUsername.getText())) {
                btnSave.setDisable(true);
                PopupUtils.throwErrorNotification("Tên đăng nhập đã tồn tại");
            }
            else {
                btnSave.setDisable(false);
            }
        });
        txtUsername.setOnKeyPressed(keyEvent -> {
            btnSave.setDisable(true);
        });
        btnCancel.setOnAction(actionEvent -> {
            Stage thisStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            thisStage.close();
        });
        txtUsername.focusedProperty().addListener(((observableValue, aBoolean, t1) -> {
            if (t1) {
                txtUsername.selectAll();
            } else {
                Platform.runLater(() -> {
                    btnCheckUserName.fire();
                });
            }
        }));
    }

    private boolean isValidCharacter(String username) {
        String regex = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
        return username.matches(regex);
    }
}
