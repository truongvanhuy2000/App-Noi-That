package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.HelloApplication;
import com.huy.appnoithat.Service.FileExport.ExportFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FileExportController {
    private ExportFile exportFileService;

    // Call this method everytime you switch scene
    public void initialize() {
    }


    /**
     * Handles switching scenes in a JavaFX application.
     *
     * @param actionEvent The ActionEvent triggered by the user interaction.
     */
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node) source).getScene().getWindow();
        stage.setScene(scene);
        stage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("/com/huy/appnoithat/Scene/icons/logoapp.jpg")));
        stage.show();
    }
}
