package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.IOC.DIContainer;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Scene.GlobalSettingScene;
import com.huy.appnoithat.Scene.Login.LoginScene;
import com.huy.appnoithat.Scene.LuaChonNoiThat.FileNoiThatExplorerScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Scene.UseManagement.UserManagementScene;
import com.huy.appnoithat.Scene.UserDetailScene;
import com.huy.appnoithat.Service.Login.LoginService;
import com.huy.appnoithat.Session.UserSessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Optional;

public class HomeController {
    final static Logger LOGGER = LogManager.getLogger(HomeController.class);
    @FXML
    private Button LogoutButton;
    @FXML
    private Button LuaChonNoiThatButton;
    @FXML
    private Button QuanLyNguoiDungButton;
    @FXML
    private Button suadoidatabaseButton;
    @FXML
    private Button settingButton;
    @FXML
    private Text UserName;
    @FXML
    private AnchorPane PCPane;
    private final UserSessionManager sessionService;
    private final LoginService loginService;

    public HomeController(UserSessionManager sessionService, LoginService loginService) {
        this.sessionService = sessionService;
        this.loginService = loginService;
    }

    @FXML
    void ChangeUserDetail(ActionEvent event) {
        UserDetailScene scene = DIContainer.get();
        StageFactory.CreateNewUnresizeableStage(scene, false);
        scene.getController().init();
    }
    @FXML
    void xuatChuKy(ActionEvent event) {
        File savedFile = PopupUtils.fileSaver();
        if (savedFile == null) {
            return;
        }
        Utils.writeObjectToFile(savedFile, sessionService.getToken());
        PopupUtils.throwSuccessNotification("Xuất chữ ký thành công!");
    }
    /**
     * Handles the logout action by cleaning the user session, closing the current window,
     * and switching to a different scene.
     *
     * @param event The ActionEvent triggering the logout.
     * Clean the user session
     * Close the current window
     * Switch to a different scene (assuming sceneSwitcher method exists)
     */
    @FXML
    void logout(ActionEvent event) {
        sessionService.cleanUserSession();
        LogoutButton.getScene().getWindow().hide();
        sceneSwitcher(event);
    }
    /**
     * Initializes the user interface based on the logged-in user's role and displays relevant buttons and actions.
     *
     */
    public void init(Stage mainStage) {
        // Show the primary content pane and clear its children
        PCPane.setVisible(true);
        PCPane.getChildren().clear();

        // Set actions for different buttons
        QuanLyNguoiDungButton.setOnAction(this::OnClickQuanLyNguoiDung);
        suadoidatabaseButton.setOnAction(this::OnClickSuaDoiDatabase);
        LuaChonNoiThatButton.setOnAction(this::OnClickLuaChonNoiThat);
        settingButton.setOnAction(this::onClickGlobalSettingOption);
        // Get the username from the session service and display a welcome message
        String username = sessionService.getAccount().getUsername();
        UserName.setText("Welcome " + username);

        // Determine user role and show appropriate buttons and actions
        String role = sessionService.getAccount().getRoleList().contains("ROLE_ADMIN") ? "Admin" : "User";
        LOGGER.info("Login as " + username + " with role " + role);
        switch (role) {
            // Enable Admin-related buttons and trigger the QuanLyNguoiDungButton action
            case "Admin" -> {
                toggleButton(false, true, true, true);
                QuanLyNguoiDungButton.fire();
            }
            case "User" -> {
                // Enable User-related buttons and trigger the LuaChonNoiThatButton action
                toggleButton(true, false, true, false);
                LuaChonNoiThatButton.fire();
            }
        }
    }

    private void onClickGlobalSettingOption(ActionEvent actionEvent) {
        PCPane.getChildren().clear();

        // Load the user management scene
        GlobalSettingScene scene = DIContainer.get();

        // Extract VBox from the scene and add it to the primary content pane
        VBox vBox = (VBox) ((AnchorPane)scene.getRoot()).getChildren().get(0);

        // Initialize the user management scene controller
        PCPane.getChildren().addAll(vBox);
        scene.getController().init();
    }

    /**
     * Handles the action when the user clicks on the "Lua Chon Noi That" button.
     * Clears the primary content pane, loads the furniture explorer scene, initializes it, and sets it as the root.
     *
     * @param actionEvent The ActionEvent triggering the button click.
     */
    private void OnClickLuaChonNoiThat(ActionEvent actionEvent) {
        // Clear the primary content pane
        PCPane.getChildren().clear();
        // Load the furniture explorer scene
        FileNoiThatExplorerScene fileNoiThatExplorerScene = DIContainer.get();

        // Add the children of the furniture explorer scene to the primary content pane
        PCPane.getChildren().addAll(((AnchorPane)fileNoiThatExplorerScene.getRoot()).getChildren());
        fileNoiThatExplorerScene.getController().init();

        // Set the primary content pane as the root for the furniture explorer scene controller
        fileNoiThatExplorerScene.getController().setRoot(PCPane);
    }

    private void toggleButton(boolean luaChonNoiThatBtn, boolean quanLyNguoiDungBtn, boolean suadoidatabaseBtn, boolean pricingSetting) {
        LuaChonNoiThatButton.setVisible(luaChonNoiThatBtn);
        LuaChonNoiThatButton.setManaged(luaChonNoiThatBtn);

        QuanLyNguoiDungButton.setVisible(quanLyNguoiDungBtn);
        QuanLyNguoiDungButton.setManaged(quanLyNguoiDungBtn);

        suadoidatabaseButton.setVisible(suadoidatabaseBtn);
        suadoidatabaseButton.setManaged(suadoidatabaseBtn);

        settingButton.setVisible(pricingSetting);
        settingButton.setManaged(pricingSetting);
    }

    /**
     * Handles scene switching based on the action event source.
     *
     * @param actionEvent The ActionEvent triggering the scene switch.
     */
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        Stage stage = (Stage) ((Node) source).getScene().getWindow();
        if (source == LogoutButton) {
            // Switch to the login scene if the Logout button is clicked
            LoginScene loginScene = DIContainer.get();
            StageFactory.createNewUnResizeableMainStage(stage, loginScene, true);
            loginScene.getLoginController().init();
        }
    }

    /**
     * Handles the action when the user clicks on the "Sua Doi Database" button.
     * Clears the primary content pane, loads the database modification scene,
     * extracts the required UI elements, adds them to the primary content pane, and initializes the scene controller.
     *
     * @param actionEvent The ActionEvent triggering the button click.
     */
    private void OnClickSuaDoiDatabase(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog("");

        dialog.setTitle("NHẬP MẬT KHẨU");
        dialog.setHeaderText("Nhập mật khẩu để sửa dữ liệu gốc:");
        dialog.setContentText("Mật khẩu :");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(password -> {
            if(loginService.reAuthorize(password)) {
                // Clear the primary content pane
                PCPane.getChildren().clear();

                // Load the database modification scene
                DatabaseModifyPhongCachScene databaseModifyPhongCachScene = DIContainer.get();

                // Extract HBox from the scene and add it to the primary content pane
                PCPane.getChildren().addAll(((AnchorPane)databaseModifyPhongCachScene.getRoot()).getChildren());

                // Initialize the database modification scene controller
                databaseModifyPhongCachScene.getController().init();
                databaseModifyPhongCachScene.getController().setRoot(PCPane);
            }
        });

    }

    /**
     * Handles the action when the user clicks on the "Quan Ly Nguoi Dung" button.
     * Clears the primary content pane, loads the user management scene,
     * extracts the required UI elements, adds them to the primary content pane, and initializes the scene controller.
     *
     * @param actionEvent The ActionEvent triggering the button click.
     */
    private void OnClickQuanLyNguoiDung(ActionEvent actionEvent) {
        // Clear the primary content pane
        PCPane.getChildren().clear();

        // Load the user management scene
        UserManagementScene userManagementScene = DIContainer.get();

        // Extract VBox from the scene and add it to the primary content pane
        VBox vBox = (VBox) ((AnchorPane)userManagementScene.getRoot()).getChildren().get(0);

        // Initialize the user management scene controller
        PCPane.getChildren().addAll(vBox);
        userManagementScene.getController().init();
    }
}
