package com.huy.appnoithat.Controller.DatabaseModify;


import com.huy.appnoithat.Controller.DatabaseModify.Cell.CustomEditingListCell;
import com.huy.appnoithat.Controller.DatabaseModify.Common.DBModifyUtils;
import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyHangMucScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyNoiThatScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyHangMucService;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyNoiThatService;
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

public class DatabaseModifyNoiThatController implements Initializable {

    @FXML
    private Button addButton, backButton, deleteButton, nextButton;
    @FXML
    private ListView<HangMuc> childrenList;
    @FXML
    private ListView<NoiThat> listViewNoiThat;
    int parentID;
    private final DatabaseModifyHangMucService databaseModifyHangMucService;
    private final DatabaseModifyNoiThatService databaseModifyNoiThatService;
    private final ObservableList<HangMuc> hangMucObservableList;
    private final ObservableList<NoiThat> noiThatObservableList;

    public DatabaseModifyNoiThatController() {
        databaseModifyNoiThatService = new DatabaseModifyNoiThatService();
        databaseModifyHangMucService = new DatabaseModifyHangMucService();
        noiThatObservableList = FXCollections.observableArrayList();
        hangMucObservableList = FXCollections.observableArrayList();
    }

    @FXML
    void addAction(ActionEvent event) {
        int currentPos = noiThatObservableList.size();
        databaseModifyNoiThatService.addNewNoiThat(new NoiThat(0, DBModifyUtils.getNewName(currentPos), new ArrayList<>()), this.parentID);
        refreshList();
    }

    @FXML
    void deleteAction(ActionEvent event) {
        NoiThat noiThat = listViewNoiThat.getSelectionModel().getSelectedItem();
        if (noiThat == null) {
            return;
        }
        if (noiThat.getId() == 0) {
            noiThatObservableList.remove(noiThat);
            return;
        }
        databaseModifyNoiThatService.deleteNoiThat(noiThat.getId());
        refreshList();
        refreshChildrenList(0);
    }

    @FXML
    void nextAction(ActionEvent event) {
        if (listViewNoiThat.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectID = listViewNoiThat.getSelectionModel().getSelectedItem().getId();
        Scene scene = null;
        Stage stage = null;
        Object source = event.getSource();
        stage = (Stage) ((Node) source).getScene().getWindow();
        if (source == nextButton) {
            scene = DatabaseModifyHangMucScene.getInstance().getScene();
            DatabaseModifyHangMucScene.getInstance().getController().init(selectID);
        } else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void sceneSwitcher(ActionEvent event) {
        Scene scene = null;
        Stage stage = null;
        Object source = event.getSource();
        stage = (Stage) ((Node) source).getScene().getWindow();
        if (source == backButton) {
            scene = DatabaseModifyPhongCachScene.getInstance().getScene();
            DatabaseModifyPhongCachScene.getInstance().getController().refresh();
        } else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void tableClickToSelectItem(MouseEvent event) {

    }

    public void init(int parentID) {
        this.parentID = parentID;
        refreshList();
        refreshChildrenList(0);
    }
    public void refresh() {
        refreshList();
        refreshChildrenList(0);
    }
    private void refreshList() {
        List<NoiThat> noiThatList = databaseModifyNoiThatService.findNoiThatListByParentID(parentID);
        if (noiThatList == null) {
            noiThatList = new ArrayList<>();
        }
        noiThatObservableList.clear();
        noiThatObservableList.addAll(noiThatList);
    }

    private void refreshChildrenList(int parentID) {
        if (parentID == 0) {
            hangMucObservableList.clear();
            return;
        }
        List<HangMuc> hangMucList = databaseModifyHangMucService.findHangMucByParentId(parentID);
        if (hangMucList == null) {
            hangMucList = new ArrayList<>();
        }
        hangMucObservableList.clear();
        hangMucObservableList.addAll(hangMucList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewNoiThat.setItems(noiThatObservableList);
        listViewNoiThat.setEditable(true);
        listViewNoiThat.setCellFactory(param -> new CustomEditingListCell<>());
        listViewNoiThat.setOnEditCommit(event -> {
            NoiThat item = event.getNewValue();
            item.setName(DBModifyUtils.getNotDuplicateName(item.getName(), noiThatObservableList));
            if (item.getId() == 0) {
                databaseModifyNoiThatService.addNewNoiThat(item, this.parentID);
            } else {
                databaseModifyNoiThatService.EditNoiThat(item);
            }
            refreshList();
        });
        listViewNoiThat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                NoiThat noiThat = listViewNoiThat.getSelectionModel().getSelectedItem();
                if (noiThat == null) {
                    return;
                }
                refreshChildrenList(noiThat.getId());
            }
        });
        childrenList.setEditable(false);
        childrenList.setCellFactory(param -> new CustomEditingListCell<>());
        childrenList.setItems(hangMucObservableList);
    }
}
