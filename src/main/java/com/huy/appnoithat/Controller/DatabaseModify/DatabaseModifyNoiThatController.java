package com.huy.appnoithat.Controller.DatabaseModify;


import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Controller.DatabaseModify.Cell.CustomEditingListCell;
import com.huy.appnoithat.Controller.DatabaseModify.Common.DBModifyUtils;
import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Enums.Action;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyHangMucScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyPhongCachScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyHangMucService;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyNoiThatService;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DatabaseModifyNoiThatController implements Initializable {
    @FXML
    private Label Title;
    @FXML
    private Button addButton, backButton, deleteButton, nextButton, getSampleDataButton;
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

    /**
     * Constructor for DatabaseModifyNoiThatController class.
     * Initializes the necessary services and observable lists.
     */
    public DatabaseModifyNoiThatController() {
        databaseModifyNoiThatService = new DatabaseModifyNoiThatService();
        databaseModifyHangMucService = new DatabaseModifyHangMucService();
        noiThatObservableList = FXCollections.observableArrayList();
        hangMucObservableList = FXCollections.observableArrayList();
    }


    /**
     * Handles the action event for adding a new NoiThat item.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void addAction(ActionEvent event) {
        int currentPos = noiThatObservableList.size();
        databaseModifyNoiThatService.addNewNoiThat(new NoiThat(0, DBModifyUtils.getNewName(currentPos), new ArrayList<>()), this.parentID);
        refreshList();
    }

    @FXML
    void FetchSampleData(ActionEvent event) {
        databaseModifyNoiThatService.fetchSampleNoiThatData(this.parentID);
        refresh();
    }

    /**
     * Handles the action event for deleting a selected NoiThat item.
     *
     * @param event The action event triggered by the user.
     */
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


    /**
     * Handles the action event for navigating to the next view based on the selected item in the list.
     * If no item is selected, the function does nothing.
     *
     * @param event The action event triggered by the user.
     */
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


    /**
     * Handles the action event for switching scenes based on the source of the event.
     * If the source is the backButton, navigates to the DatabaseModifyPhongCachScene.
     *
     * @param event The action event triggered by the user.
     */
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

    /**
     * Initializes the controller with the provided parent ID, refreshes the list, and clears the selection.
     *
     * @param parentID The ID of the parent element.
     */
    public void init(int parentID) {
        System.out.println(parentID);
        this.parentID = parentID;
        refreshList();
        refreshChildrenList(0);
    }

    /**
     * Refreshes the list and clears the selection.
     */
    public void refresh() {
        refreshList();
        refreshChildrenList(0);
        listView.getSelectionModel().clearSelection();
    }

    /**
     * Refreshes the list of NoiThat elements based on the current parent ID.
     */
    private void refreshList() {
        List<NoiThat> noiThatList = databaseModifyNoiThatService.findNoiThatListByParentID(parentID);
        if (noiThatList == null) {
            noiThatList = new ArrayList<>();
        }
        noiThatObservableList.clear();
        noiThatObservableList.addAll(noiThatList);
    }


    /**
     * Refreshes the list of HangMuc elements based on the given parent ID.
     * If the parent ID is 0, clears the list.
     *
     * @param parentID The ID of the parent element.
     */
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

    /**
     * Initializes the controller with the specified URL and ResourceBundle.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getSampleDataButton.disableProperty().bind(Bindings.size(noiThatObservableList).greaterThan(0));
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

    /**
     * Handles key events and triggers corresponding actions.
     *
     * @param event The KeyEvent that occurred.
     */
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
