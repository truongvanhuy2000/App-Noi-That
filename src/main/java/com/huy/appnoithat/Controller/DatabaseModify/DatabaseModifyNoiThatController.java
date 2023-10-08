package com.huy.appnoithat.Controller.DatabaseModify;


import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Controller.DatabaseModify.Cell.CustomEditingListCell;
import com.huy.appnoithat.Controller.DatabaseModify.Common.DBModifyUtils;
import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Enums.Action;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DatabaseModifyNoiThatController implements Initializable {
    @FXML
    private Label Title;
    @FXML
    private Button addButton, backButton, deleteButton, nextButton;
    @FXML
    private ListView<HangMuc> childrenList;
    @FXML
    private ListView<NoiThat> listView;
    int parentID;
    private final DatabaseModifyHangMucService databaseModifyHangMucService;
    private final DatabaseModifyNoiThatService databaseModifyNoiThatService;
    private final ObservableList<HangMuc> hangMucObservableList;
    private final ObservableList<NoiThat> noiThatObservableList;
    @Setter
    private Parent root;

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
        NoiThat noiThat = listView.getSelectionModel().getSelectedItem();
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
        if (listView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectID = listView.getSelectionModel().getSelectedItem().getId();
        DatabaseModifyHangMucScene databaseModifyHangMucScene = new DatabaseModifyHangMucScene();
        HBox hBox = (HBox) ((AnchorPane)databaseModifyHangMucScene.getRoot()).getChildren().get(0);

        ((AnchorPane)this.root).getChildren().clear();
        ((AnchorPane)this.root).getChildren().add(hBox);
        databaseModifyHangMucScene.getController().init(selectID);
        databaseModifyHangMucScene.getController().setRoot(this.root);
    }

    @FXML
    void sceneSwitcher(ActionEvent event) {
        Object source = event.getSource();
        if (source == backButton) {
            DatabaseModifyPhongCachScene databaseModifyPhongCachScene = new DatabaseModifyPhongCachScene();
            HBox hBox = (HBox) ((AnchorPane)databaseModifyPhongCachScene.getRoot()).getChildren().get(0);
            ((AnchorPane)this.root).getChildren().clear();
            ((AnchorPane)this.root).getChildren().add(hBox);
            DatabaseModifyPhongCachScene.getController().init();
            DatabaseModifyPhongCachScene.getController().setRoot(this.root);
        }
    }
    public void init(int parentID) {
        System.out.println(parentID);
        this.parentID = parentID;
        refreshList();
        refreshChildrenList(0);
    }
    public void refresh() {
        refreshList();
        refreshChildrenList(0);
        listView.getSelectionModel().clearSelection();
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
        Title.setText("Danh sách nội thất");
        listView.setItems(noiThatObservableList);
        listView.setEditable(true);
        listView.setCellFactory(param -> new CustomEditingListCell<>());
        listView.setOnEditCommit(event -> {
            NoiThat item = event.getNewValue();
            item.setName(DBModifyUtils.getNotDuplicateName(item.getName(), noiThatObservableList));
            if (item.getId() == 0) {
                databaseModifyNoiThatService.addNewNoiThat(item, this.parentID);
            } else {
                databaseModifyNoiThatService.EditNoiThat(item);
            }
            refreshList();
        });
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                NoiThat noiThat = listView.getSelectionModel().getSelectedItem();
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
    @FXML
    void onKeyPressed(KeyEvent event) {
        if (KeyboardUtils.isRightKeyCombo(Action.ADD_NEW_ROW, event)) {
            addButton.fire();
        }
        else if (KeyboardUtils.isRightKeyCombo(Action.DELETE, event)) {
            deleteButton.fire();
        }
        else if (KeyboardUtils.isRightKeyCombo(Action.NEXT_SCREEN, event)) {
            nextButton.fire();
        }
    }
}
