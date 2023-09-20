package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Service.FileExport.ExportFileService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FileExportController {
    private ExportFileService exportFileService;

    // Call this method everytime you switch scene
    public void initialize() {
    }

    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node) source).getScene().getWindow();
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
