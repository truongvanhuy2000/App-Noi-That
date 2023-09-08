package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.LoginScene;
import com.huy.appnoithat.Scene.NewTabScene;
import com.huy.appnoithat.Scene.UserManagementScene;
import com.huy.appnoithat.Service.SessionService.UserSessionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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
    final static Logger LOGGER = LogManager.getLogger(HomeController.class);

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
        String username = sessionService.getSession().getAccount().getUsername();
        UserName.setText(username);
//         Show button based on role
        List<String> roles = sessionService.getSession().getAccount().getRoleList();
        if (roles == null) {
            LOGGER.error("User has no role");
            throw new RuntimeException("User has no role");
        }
        else {
            if (roles.contains("ROLE_ADMIN")) {
                LuaChonNoiThatButton.setVisible(true);
                QuanLyNguoiDungButton.setVisible(true);
                suadoidatabaseButton.setVisible(true);
            }
            else if (roles.contains("ROLE_USER")) {
                LuaChonNoiThatButton.setVisible(true);
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
            DatabaseModifyPhongCachScene.getInstance().getController().initializePhongCach();
        }
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }

}
