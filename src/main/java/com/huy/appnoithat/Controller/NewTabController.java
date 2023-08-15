package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.LuaChonNoiThatScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewTabController implements Initializable {
    private final static Logger logger = LogManager.getLogger(NewTabController.class);
    @FXML
    private TabPane tabPane;
    @FXML
    private Button BackButton;

    @FXML
    void newTabButtonHandler(ActionEvent event) {
        createNewTab();
    }
    void createNewTab(){
        Tab newTab = new Tab("Tab mới");
        try {
            Node root = LuaChonNoiThatScene.getNewRoot();
            newTab.setContent(root);
        } catch (IOException e) {
            logger.error("Critical Error:" + e.getMessage());
        }
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == BackButton){
            scene = HomeScene.getInstance().getScene();
        }
        else return;
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createNewTab();
    }
}
