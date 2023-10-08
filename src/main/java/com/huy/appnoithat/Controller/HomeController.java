package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Scene.LoginScene;
import com.huy.appnoithat.Scene.LuaChonNoiThat.FileNoiThatExplorerScene;
import com.huy.appnoithat.Scene.UseManagement.UserManagementScene;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private Text UserName;
    @FXML
    private AnchorPane PCPane;
    private final UserSessionService sessionService;

    public HomeController() {
        this.sessionService = new UserSessionService();
    }

    @FXML
    void logout(ActionEvent event) {
        sessionService.cleanUserSession();
        LogoutButton.getScene().getWindow().hide();
        sceneSwitcher(event);
    }
    private void initController() {
    }

    // Initialize scene
    public void initialize() {
        PCPane.setVisible(true);
        PCPane.getChildren().clear();
        toggleButton(false, false, false);
        QuanLyNguoiDungButton.setOnAction(this::OnClickQuanLyNguoiDung);
        suadoidatabaseButton.setOnAction(this::OnClickSuaDoiDatabase);
        LuaChonNoiThatButton.setOnAction(this::OnClickLuaChonNoiThat);
        String username = sessionService.getLoginAccount().getUsername();
        UserName.setText("Welcome " + username);
        // Show button based on role
        String role = sessionService.getLoginAccount().getRoleList().contains("ROLE_ADMIN") ? "Admin" : "User";
        switch (role) {
            case "Admin" -> {
                toggleButton(false, true, false);
                QuanLyNguoiDungButton.fire();
            }
            case "User" -> {
                toggleButton(true, false, true);
                LuaChonNoiThatButton.fire();
            }
            default -> {
            }
        }
        LOGGER.info("Login as " + username + " with role " + role);
    }

    private void OnClickLuaChonNoiThat(ActionEvent actionEvent) {
        PCPane.getChildren().clear();
        FileNoiThatExplorerScene fileNoiThatExplorerScene = new FileNoiThatExplorerScene();
        PCPane.getChildren().addAll(((AnchorPane)fileNoiThatExplorerScene.getRoot()).getChildren());
        FileNoiThatExplorerScene.getController().init();
        FileNoiThatExplorerScene.getController().setRoot(PCPane);
    }

    private void toggleButton(boolean luaChonNoiThatBtn, boolean quanLyNguoiDungBtn, boolean suadoidatabaseBtn) {
        LuaChonNoiThatButton.setDisable(!luaChonNoiThatBtn);
        QuanLyNguoiDungButton.setDisable(!quanLyNguoiDungBtn);
        suadoidatabaseButton.setDisable(!suadoidatabaseBtn);
    }

    // Central unit to switch scene based on context
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Object source = actionEvent.getSource();
        Stage stage = (Stage) ((Node) source).getScene().getWindow();
        stage.setResizable(false);
        if (source == LogoutButton) {
            scene = LoginScene.getInstance().getScene();
        }
        stage.setScene(scene);
        stage.show();
    }

    private void OnClickSuaDoiDatabase(ActionEvent actionEvent) {
        PCPane.getChildren().clear();
        DatabaseModifyPhongCachScene databaseModifyPhongCachScene = new DatabaseModifyPhongCachScene();
        HBox hBox = (HBox) ((AnchorPane)databaseModifyPhongCachScene.getRoot()).getChildren().get(0);
        PCPane.getChildren().addAll(hBox);
        DatabaseModifyPhongCachScene.getController().init();
        DatabaseModifyPhongCachScene.getController().setRoot(PCPane);
    }

    private void OnClickQuanLyNguoiDung(ActionEvent actionEvent) {
        PCPane.getChildren().clear();
        UserManagementScene userManagementScene = new UserManagementScene();
        VBox vBox = (VBox) ((AnchorPane)userManagementScene.getRoot()).getChildren().get(0);
        PCPane.getChildren().addAll(vBox);
        UserManagementScene.getController().init();
    }
}
