package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Entity.Account;
import com.huy.appnoithat.Entity.AccountInformation;
import com.huy.appnoithat.Service.UserDetail.UserDetailService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserDetailController implements Initializable {
    private static final String GENDER_OPTION1 = "Nam";
    private static final String GENDER_OPTION2 = "Nữ";
    @FXML
    private TextField Address, PhoneNumber, Email, FullName;
    @FXML
    private ComboBox<String> Gender;
    @FXML
    private PasswordField ConfirmPassword, OldPassword, NewPassword;
    @FXML
    private Button ChangePasswordBtn;
    private final UserDetailService userDetailService;

    public UserDetailController() {
        userDetailService = new UserDetailService();
    }

    @FXML
    void ChangePassword(ActionEvent event) {
        if (!ConfirmPassword.getText().equals(NewPassword.getText())) {
            PopupUtils.throwErrorSignal("Mật khẩu xác nhận không khớp");
            return;
        }
        if (userDetailService.updatePassword(OldPassword.getText(), NewPassword.getText())) {
            PopupUtils.throwSuccessSignal("Đổi mật khẩu thành công");
            clearPassword();
        } else {
            PopupUtils.throwErrorSignal("Đổi mật khẩu thất bại");
            clearPassword();
        }
    }

    @FXML
    void UpdateInformation(ActionEvent event) {
        AccountInformation accountInformation = new AccountInformation();
        accountInformation.setName(FullName.getText());
        accountInformation.setAddress(Address.getText());
        accountInformation.setPhone(PhoneNumber.getText());
        accountInformation.setEmail(Email.getText());
        accountInformation.setGender(Gender.getValue());
        if (userDetailService.updateAccountInformation(accountInformation)) {
            PopupUtils.throwSuccessSignal("Cập nhật thông tin thành công");
            refreshInfo();
        } else {
            PopupUtils.throwErrorSignal("Cập nhật thông tin thất bại");
        }
    }
    void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    void clearPassword() {
        OldPassword.setText("");
        NewPassword.setText("");
        ConfirmPassword.setText("");
    }
    public void init() {
        refreshInfo();
    }
    public void refreshInfo() {
        Account account = userDetailService.getAccountInformation();
        FullName.setText(account.getAccountInformation().getName());
        Address.setText(account.getAccountInformation().getAddress());
        PhoneNumber.setText(account.getAccountInformation().getPhone());
        Email.setText(account.getAccountInformation().getEmail());
        Gender.setValue(account.getAccountInformation().getGender());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> listGender = FXCollections.observableArrayList(GENDER_OPTION1, GENDER_OPTION2);
        Gender.setItems(listGender);
        ChangePasswordBtn.disableProperty().bind(OldPassword.textProperty().isEmpty()
                .or(NewPassword.textProperty().isEmpty()).or(ConfirmPassword.textProperty().isEmpty()));
    }
}
