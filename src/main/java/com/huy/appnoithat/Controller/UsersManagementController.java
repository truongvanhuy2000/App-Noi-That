package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Service.SessionService.UserSessionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UsersManagementController {
    UserSessionService userSessionService;
    public UsersManagementController() {
        userSessionService = new UserSessionService();
    }
    public void initialize() {
    }
    // Used to switch between scence
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
//        if (source == LogoutButton){
//            scene = LoginScene.getInstance().getScene();
//        }
//        else {
//            return;
//        }
        stage.setScene(scene);
        stage.show();
    }
}
