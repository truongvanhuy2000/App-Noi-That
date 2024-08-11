package com.huy.appnoithat.Controller.Register;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.DataModel.Entity.Account;
import com.huy.appnoithat.DataModel.Entity.AccountInformation;
import com.huy.appnoithat.DataModel.PricingModelDTO;
import com.huy.appnoithat.IOC.DIContainer;
import com.huy.appnoithat.Scene.Login.QRScene;
import com.huy.appnoithat.Service.Register.RegisterService;
import com.huy.appnoithat.Service.RestService.PricingModelRestService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.List;

public class RegisterController {
    private static final String GENDER_OPTION1 = "Nam";
    private static final String GENDER_OPTION2 = "Nữ";
    @FXML
    private ComboBox<String> ComboBoxGender;

    @FXML
    private ComboBox<PricingModelDTO.PricingModel> ComboBoxTime;

    @FXML
    private Button btnCheckUserName, btnSave, btnCancel;
    @FXML
    private TextField txtAddress, txtEmail, txtName, txtPassword, txtPhone, txtUsername;
    private final RegisterService registerService;
    private final PricingModelRestService pricingModelRestService;
    private boolean isPricingEnable = false;

    public RegisterController(RegisterService registerService, PricingModelRestService pricingModelRestService) {
        this.registerService = registerService;
        this.pricingModelRestService = pricingModelRestService;
    }

    /**
     * Initializes the registration process.
     * Sets up initial values for ComboBoxes and disables the save button by default.
     * Handles the registration of a new account when the save button is clicked.
     * Displays a QR code for payment after successful registration.
     */
    public void init() {
        PricingModelDTO pricingModelDTO = pricingModelRestService.getPricingModel();
        isPricingEnable = pricingModelDTO.isActive();
        // Set up options for ComboBoxes
        ObservableList<PricingModelDTO.PricingModel> listTime = FXCollections.observableArrayList();
        listTime.addAll(pricingModelDTO.getPricingModelList());

        // Populate ComboBoxes with options
        ComboBoxTime.setItems(listTime);
        ComboBoxTime.setConverter(new StringConverter<>() {
            @Override
            public String toString(PricingModelDTO.PricingModel pricingModel) {
                if (pricingModel == null) {
                    return "";
                }
                String timeOption = pricingModel.getMonthOption().toString();
                if (pricingModel.getBonusMonth() != 0) {
                    timeOption += String.format(" khuyến mãi thêm %s", pricingModel.getBonusMonth());
                }
                timeOption += " tháng";
                return timeOption;
            }

            @Override
            public PricingModelDTO.PricingModel fromString(String s) {
                return ComboBoxTime.getValue();
            }
        });
        ComboBoxTime.setVisibleRowCount(3);

        ComboBoxGender.setItems(FXCollections.observableArrayList(GENDER_OPTION1, GENDER_OPTION2));

        btnSave.setDisable(true);
        btnSave.setOnAction(this::onLickSaveBtn);

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
                checkUsername();
            }
        }));
    }

    private String getDisplayText(PricingModelDTO.PricingModel pricingModel) {
        String BASE_TEXT = "Đăng ký tài khoản %s tháng giá %s VNĐ.";
        String price = Utils.convertLongToDecimal(pricingModel.getPrice());
        String baseText = String.format(BASE_TEXT, pricingModel.getMonthOption(), price);
        if (pricingModel.getBonusMonth() != null && pricingModel.getBonusMonth() != 0) {
            baseText += String.format(" Tặng thêm %s tháng", pricingModel.getBonusMonth());
        }
        String totalMoneyText = String.format("\nSố tiền chuyển khoản: %s VNĐ", price);
        return baseText + totalMoneyText;
    }

    private void onLickSaveBtn(ActionEvent actionEvent) {
        QRScene qrScene = DIContainer.get();
        Scene QRpopup = qrScene.getScene();
        Label giaTienQR = (Label) QRpopup.lookup("#giaTienQR");
        String Gender = "Male";
        if (ComboBoxGender.getSelectionModel().getSelectedItem() != null) {
            Gender = ComboBoxGender.getSelectionModel().getSelectedItem();
        }
        // Retrieve selected gender, time option, and current date
        PricingModelDTO.PricingModel selectedPricingModel = ComboBoxTime.getSelectionModel().getSelectedItem();
        LocalDate localDate = LocalDate.now().plusMonths(
                selectedPricingModel.getMonthOption() + selectedPricingModel.getBonusMonth());
        String QRText = getDisplayText(selectedPricingModel);
        AccountInformation accountInformation = AccountInformation.builder()
                .id(0)
                .name(txtName.getText())
                .gender(Gender)
                .email(txtEmail.getText())
                .address(txtAddress.getText())
                .phone(txtPhone.getText())
                .build();

        Account account = Account.builder()
                .id(0)
                .username(txtUsername.getText())
                .password(txtPassword.getText())
                .active(false)
                .accountInformation(accountInformation)
                .roleList(List.of("ROLE_USER"))
                .enabled(false)
                .expiredDate(localDate)
                .build();

        registerService.registerNewAccount(account);
        // Close the current registration window
        Stage thisStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        thisStage.close();
        if (isPricingEnable) {
            // Display a new window with the QR code for payment
            Stage QRstage = new Stage();
            giaTienQR.setText(QRText);
            QRstage.setScene(QRpopup);
            QRstage.setTitle("Quét QR để thanh toán");
            QRstage.show();
            // You might need additional logic to handle saving or updating data
        }
    }

    private boolean isValidCharacter(String username) {
        String regex = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
        return username.matches(regex);
    }

    private void checkUsername() {
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
        } else {
            btnSave.setDisable(false);
        }
    }
}
