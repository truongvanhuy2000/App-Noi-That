package com.huy.appnoithat.Controller.DatabaseModify;


import com.huy.appnoithat.Controller.DatabaseModify.Cell.CustomEditingListCell;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyNoiThatScene;
import com.huy.appnoithat.Scene.HomeScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyNoiThatService;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyPhongCachService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DatabaseModifyPhongCachController implements Initializable {
    @FXML
    private ListView<PhongCachNoiThat> listViewPhongCach;
    @FXML
    private ListView<NoiThat> childrenList;
    @FXML
    private Button EditPhongCachButton;

    @FXML
    private Button addPhongCachButton;

    @FXML
    private Button deletePhongCachButton;
    @FXML
    private Button backButton;
    @FXML
    private Button nextScreenButton;
    private final DatabaseModifyPhongCachService databaseModifyPhongCachService;
    private final DatabaseModifyNoiThatService databaseModifyNoiThatService;
    private final ObservableList<PhongCachNoiThat> phongCachNoiThatObservableList;
    private final ObservableList<NoiThat> noiThatObservableList;

    public DatabaseModifyPhongCachController() {
        databaseModifyPhongCachService = new DatabaseModifyPhongCachService();
        databaseModifyNoiThatService = new DatabaseModifyNoiThatService();
        phongCachNoiThatObservableList = FXCollections.observableArrayList();
        noiThatObservableList = FXCollections.observableArrayList();
    }

    @FXML
    void AddNewPhongCach(ActionEvent event) {
        phongCachNoiThatObservableList.add(new PhongCachNoiThat(0, "<Thêm mới>", new ArrayList<>()));
    }

    @FXML
    void DeletePhongCach(ActionEvent event) {
        PhongCachNoiThat phongCachNoiThat = listViewPhongCach.getSelectionModel().getSelectedItem();
        if (phongCachNoiThat == null) {
            return;
        }
        if (phongCachNoiThat.getId() == 0) {
            phongCachNoiThatObservableList.remove(phongCachNoiThat);
            return;
        }
        databaseModifyPhongCachService.deletePhongCach(phongCachNoiThat.getId());
        refreshList();
    }
    @FXML
    void tableClickToSelectItem(MouseEvent event) {
    }
    @FXML
    void NextScreen(ActionEvent event) {
        if(listViewPhongCach.getSelectionModel().getSelectedItem() ==null){
            return;
        }
        int selectID = listViewPhongCach.getSelectionModel().getSelectedItem().getId();
        Scene scene = null;
        Stage stage = null;
        Object source = event.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == nextScreenButton){
            scene = DatabaseModifyNoiThatScene.getInstance().getScene();
            DatabaseModifyNoiThatScene.getInstance().getController().init(selectID);
        }else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }
    public void init() {
        refreshList();
    }
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node)source).getScene().getWindow();
        if (source == backButton){
            scene = HomeScene.getInstance().getScene();
            listViewPhongCach.getItems().clear();
        }
        else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewPhongCach.setItems(phongCachNoiThatObservableList);
        listViewPhongCach.setEditable(true);
        listViewPhongCach.setCellFactory(param -> new CustomEditingListCell<>());
        listViewPhongCach.setOnEditCommit(event -> {
            PhongCachNoiThat item = event.getNewValue();
            item.setName(event.getNewValue().getName());
            if (item.getId() == 0) {
                databaseModifyPhongCachService.addNewPhongCach(item);
            }
            else {
                databaseModifyPhongCachService.EditPhongCach(item);
            }
            refreshList();
        });
        listViewPhongCach.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                PhongCachNoiThat phongCachNoiThat = listViewPhongCach.getSelectionModel().getSelectedItem();
                if (phongCachNoiThat == null) {
                    return;
                }
                refreshChildrenList(phongCachNoiThat.getId());
            }
        });
        childrenList.setEditable(false);
        childrenList.setCellFactory(param -> new CustomEditingListCell<>());
        childrenList.setItems(noiThatObservableList);
    }
    private void refreshList() {
        List<PhongCachNoiThat> phongCachNoiThatList = databaseModifyPhongCachService.findAllPhongCach();
        if (phongCachNoiThatList == null) {
            phongCachNoiThatList = new ArrayList<>();
        }
        phongCachNoiThatObservableList.clear();
        phongCachNoiThatObservableList.addAll(phongCachNoiThatList);
    }
    private void refreshChildrenList(int parentID) {
        List<NoiThat> noiThatList = databaseModifyNoiThatService.findNoiThatByID(parentID);
        if (noiThatList == null) {
            noiThatList = new ArrayList<>();
        }
        noiThatObservableList.clear();
        noiThatObservableList.addAll(noiThatList);
    }
}
