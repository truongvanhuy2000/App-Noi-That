package com.huy.appnoithat.Controller.DatabaseModify;


import com.huy.appnoithat.Controller.DatabaseModify.Cell.CustomEditingListCell;
import com.huy.appnoithat.Controller.DatabaseModify.Common.DBModifyUtils;
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

public class DatabaseModifyPhongCachController implements Initializable {
    @FXML
    private Label Title;
    @FXML
    private Button addButton, deleteButton, backButton, nextButton;
    @FXML
    private ListView<NoiThat> childrenList;
    @FXML
    private ListView<PhongCachNoiThat> listView;
    private final DatabaseModifyPhongCachService databaseModifyPhongCachService;
    private final DatabaseModifyNoiThatService databaseModifyNoiThatService;
    private final ObservableList<PhongCachNoiThat> phongCachNoiThatObservableList;
    private final ObservableList<NoiThat> noiThatObservableList;
    @Setter
    private Parent root;
    public DatabaseModifyPhongCachController() {
        databaseModifyPhongCachService = new DatabaseModifyPhongCachService();
        databaseModifyNoiThatService = new DatabaseModifyNoiThatService();
        phongCachNoiThatObservableList = FXCollections.observableArrayList();
        noiThatObservableList = FXCollections.observableArrayList();
    }

    @FXML
    void addAction(ActionEvent event) {
        int currentPos = phongCachNoiThatObservableList.size();
        databaseModifyPhongCachService.addNewPhongCach(new PhongCachNoiThat(0, DBModifyUtils.getNewName(currentPos), new ArrayList<>()));
        refreshList();
    }

    @FXML
    void deleteAction(ActionEvent event) {
        PhongCachNoiThat phongCachNoiThat = listView.getSelectionModel().getSelectedItem();
        if (phongCachNoiThat == null) {
            return;
        }
        if (phongCachNoiThat.getId() == 0) {
            phongCachNoiThatObservableList.remove(phongCachNoiThat);
            return;
        }
        databaseModifyPhongCachService.deletePhongCach(phongCachNoiThat.getId());
        refreshList();
        refreshChildrenList(0);
    }
    @FXML
    void nextAction(ActionEvent event) {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectID = listView.getSelectionModel().getSelectedItem().getId();
        DatabaseModifyNoiThatScene databaseModifyNoiThatScene = new DatabaseModifyNoiThatScene();
        HBox hBox = (HBox) ((AnchorPane)databaseModifyNoiThatScene.getRoot()).getChildren().get(0);

        ((AnchorPane)this.root).getChildren().clear();
        ((AnchorPane)this.root).getChildren().add(hBox);
        DatabaseModifyNoiThatScene.getController().init(selectID);
        DatabaseModifyNoiThatScene.getController().setRoot(this.root);
    }

    public void init() {
        refreshList();
        refreshChildrenList(0);
    }
    public void refresh() {
        refreshList();
        refreshChildrenList(0);
        listView.getSelectionModel().clearSelection();
    }
    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
        Scene scene = null;
        Stage stage = null;
        Object source = actionEvent.getSource();
        stage = (Stage) ((Node) source).getScene().getWindow();
        if (source == backButton) {
            scene = HomeScene.getInstance().getScene();
            listView.getItems().clear();
        } else {
            return;
        }
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void onKeyPressed(KeyEvent event) {
//        if (KeyboardUtils.isRightKeyCombo(Action.ADD_NEW_ROW, event)) {
//            addButton.fire();
//        }
//        else if (KeyboardUtils.isRightKeyCombo(Action.DELETE, event)) {
//            deleteButton.fire();
//        }
//        else if (KeyboardUtils.isRightKeyCombo(Action.NEXT_SCREEN, event)) {
//            nextButton.fire();
//        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setVisible(false);
        Title.setText("Danh sách phong cách");
        listView.setItems(phongCachNoiThatObservableList);
        listView.setEditable(true);
        listView.setCellFactory(param -> new CustomEditingListCell<>());
        listView.setOnEditCommit(event -> {
            PhongCachNoiThat item = event.getNewValue();
            item.setName(DBModifyUtils.getNotDuplicateName(item.getName(), phongCachNoiThatObservableList));
            if (item.getId() == 0) {
                databaseModifyPhongCachService.addNewPhongCach(item);
            } else {
                databaseModifyPhongCachService.EditPhongCach(item);
            }
            refreshList();
        });
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                PhongCachNoiThat phongCachNoiThat = listView.getSelectionModel().getSelectedItem();
                if (phongCachNoiThat == null) {
                    return;
                }
                refreshChildrenList(phongCachNoiThat.getId());
            }
        });
        childrenList.setEditable(false);
        childrenList.setCellFactory(param -> new CustomEditingListCell<>());
        childrenList.setItems(noiThatObservableList);
        init();
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
        List<NoiThat> noiThatList = databaseModifyNoiThatService.findNoiThatListByParentID(parentID);
        if (noiThatList == null) {
            noiThatList = new ArrayList<>();
        }
        noiThatObservableList.clear();
        noiThatObservableList.addAll(noiThatList);
    }
}
