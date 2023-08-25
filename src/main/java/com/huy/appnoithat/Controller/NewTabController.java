package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Scene.LuaChonNoiThatScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewTabController implements Initializable {
    @FXML
    private TabPane tabPane;
    @FXML
    private Button BackButton;

    @FXML
    void newTabButtonHandler(ActionEvent event) {
        createNewTab();
    }
    private void createNewTab(){
        Tab newTab = setUpTab();
        try {
            Node root = LuaChonNoiThatScene.getNewRoot();
            newTab.setContent(root);
        } catch (IOException e) {
        }
        addNewTabToPane(newTab);
    }
    private void addNewTabToPane(Tab newTab){
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }
    private Tab setUpTab(){
        Tab newTab = new Tab("Tab mới");
        ContextMenu contextMenu = new ContextMenu();
        MenuItem nhanBanMenuItem = new MenuItem("Nhân bản");
        nhanBanMenuItem.setOnAction(event -> duplicateTab(event, newTab));
        contextMenu.getItems().add(nhanBanMenuItem);

        newTab.contextMenuProperty().set(contextMenu);
        return newTab;
    }
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == BackButton){
            scene = HomeScene.getInstance().getScene();
            HomeScene.getInstance().getHomeController().initialize();
        }
        else return;
        stage.setScene(scene);
        stage.show();
    }
    private void duplicateTab(ActionEvent action, Tab currentTab) {
        Node nodeFromCurrentTab = currentTab.getContent();
//        nodeFromCurrentTab.lookupAll();
        Tab newTab = setUpTab();
        try {
            Node root = LuaChonNoiThatScene.getNewRoot();
            newTab.setContent(root);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createNewTab();
    }
}
