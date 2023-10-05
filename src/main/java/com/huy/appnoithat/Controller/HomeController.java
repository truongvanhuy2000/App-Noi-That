package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Scene.LoginScene;
import com.huy.appnoithat.Scene.NewTabScene;
import com.huy.appnoithat.Scene.UserManagementScene;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeController {
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
    private final UserSessionService sessionService;

    @FXML
    private ImageView logoutIMG;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane PCPane;
    public HomeController() {
        this.sessionService = new UserSessionService();
    }
    @FXML
    void logout(ActionEvent event) {
        sessionService.cleanUserSession();
        LogoutButton.getScene().getWindow().hide();
        sceneSwitcher(event);
    }
    // Initialize scene
    public void initialize() {

        // Hide all button
        toggleButton(false, false, false);
        // Set username using current session
        String username = sessionService.getLoginAccount().getUsername();
        UserName.setText(" " + username);
        // Show button based on role
        String role = sessionService.getLoginAccount().getRoleList().contains("ROLE_ADMIN") ? "Admin" : "User";
        switch (role) {
            case "Admin" -> {
                toggleButton(false, true, true);
            }
            case "User" -> {
                toggleButton(true, false, false);
            }
            default -> {
            }
        }
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
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        stage.setResizable(false);
        if (source == LogoutButton){
            scene = LoginScene.getInstance().getScene();
        }
        else if (source == LuaChonNoiThatButton) {
            Stage newStage = new Stage();
            scene = NewTabScene.getInstance().getScene();
            NewTabScene.getInstance().getNewTabController().init();
            newStage.setMaximized(true);
            newStage.setScene(scene);
            newStage.show();
            return;
        }
        else if (source == QuanLyNguoiDungButton) {
//            scene = UserManagementScene.getInstance().getScene();
            PCPane.setVisible(false);
            mainPane.setVisible(true);
            AnchorPane root =(AnchorPane) UserManagementScene.getInstance().getRoot();
            mainPane.getChildren().addAll(root.getChildren());
            return;
        }else if (source == suadoidatabaseButton) {
//            scene = DatabaseModifyPhongCachScene.getInstance().getScene();
//            DatabaseModifyPhongCachScene.getInstance().getController().init();
            mainPane.setVisible(false);
            PCPane.setVisible(true);
            AnchorPane root = (AnchorPane)DatabaseModifyPhongCachScene.getInstance().getRoot();
            PCPane.getChildren().addAll(root.getChildren());
            return;
        }
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }

}