package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Scene.*;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        LuaChonNoiThatButton.setVisible(false);
        QuanLyNguoiDungButton.setVisible(false);

        // Set username using current session
        String username = sessionService.getSession().getUsername();
        UserName.setText(username);

        // Show button based on role
        switch (sessionService.getSession().getRoles().toLowerCase()) {
            case "admin" -> {
                QuanLyNguoiDungButton.setVisible(true);
                LuaChonNoiThatButton.setVisible(true);
            }
            case "user" -> LuaChonNoiThatButton.setVisible(true);
            default -> {
            }
        }
    }
    // Central unit to switch scene based on context
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == LogoutButton){
            scene = LoginScene.getInstance().getScene();
        }
        else if (source == LuaChonNoiThatButton) {
            scene = NewTabScene.getInstance().getScene();
            stage.setMaximized(true);
        }
        else if (source == QuanLyNguoiDungButton) {
            scene = UserManagementScene.getInstance().getScene();
        }else if (source == suadoidatabaseButton) {
            scene = DatabaseModifyPhongCachScene.getInstance().getScene();
        }
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }

}
