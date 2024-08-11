package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.DatabaseModify.Cell.CustomEditingListCell;
import com.huy.appnoithat.Controller.DatabaseModify.Common.DBModifyUtils;
import com.huy.appnoithat.DataModel.Entity.HangMuc;
import com.huy.appnoithat.DataModel.Entity.VatLieu;
import com.huy.appnoithat.DataModel.Enums.Action;
import com.huy.appnoithat.IOC.DIContainer;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyNoiThatScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyVatLieuScene;
import com.huy.appnoithat.Service.DatabaseModify.DatabaseModifyHangMucService;
import com.huy.appnoithat.Service.DatabaseModify.DatabaseModifyVatlieuService;
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

public class DatabaseModifyHangMucController implements Initializable {
    @FXML
    private Label Title;
    @FXML
    private Button addButton, backButton, deleteButton, nextButton;
    @FXML
    private Button getSampleDataButton;
    @FXML
    private ListView<VatLieu> childrenList;
    @FXML
    private ListView<HangMuc> listView;
    @FXML
    private Button swapButton;
    private int parentID;
    private final DatabaseModifyHangMucService databaseModifyHangMucService;
    private final DatabaseModifyVatlieuService databaseModifyVatlieuService;
    private final ObservableList<HangMuc> hangMucObservableList = FXCollections.observableArrayList();
    private final ObservableList<VatLieu> vatLieuObservableList = FXCollections.observableArrayList();
    @Setter
    private Parent root;

    public DatabaseModifyHangMucController(DatabaseModifyHangMucService databaseModifyHangMucService,
                                           DatabaseModifyVatlieuService databaseModifyVatlieuService) {
        this.databaseModifyHangMucService = databaseModifyHangMucService;
        this.databaseModifyVatlieuService = databaseModifyVatlieuService;
    }

    @FXML
    void swap(ActionEvent event) {
        HangMuc hangMuc = listView.getSelectionModel().getSelectedItem();
        if (hangMuc == null) {
            return;
        }
        String swapIndex = PopupUtils.openDialog("Đổi vị trí", "Đổi vị trí", "Vị trí");
        if (swapIndex == null || swapIndex.isEmpty()) {
            return;
        }
        int index;
        try {
            index = Integer.parseInt(swapIndex);
            if (index == 0 || index > hangMucObservableList.size()) {
                return;
            }
        } catch (NumberFormatException e) {
            return;
        }
        databaseModifyHangMucService.swap(hangMuc.getId(), hangMucObservableList.get(index - 1).getId());
        refreshList();
    }
    /**
     * Handles the action event for adding a new HangMuc item.
     *
     * @param event The ActionEvent triggering the action.
     */
    @FXML
    void addAction(ActionEvent event) {
        int currentPos = hangMucObservableList.size();
        databaseModifyHangMucService.addNewHangMuc(new HangMuc(0, DBModifyUtils.getNewName(currentPos), new ArrayList<>()), this.parentID);
        refreshList();
    }

    @FXML
    void FetchSampleData(ActionEvent event) {
        databaseModifyHangMucService.fetchSampleHangMucData(this.parentID);
        refresh();
    }

    /**
     * Handles the action event for deleting a HangMuc item.
     *
     * @param event The ActionEvent triggering the action.
     */
    @FXML
    void deleteAction(ActionEvent event) {
        if (!PopupUtils.confirmationDialog("Xóa", "Xóa", "Bạn có chắc chắn muốn xóa mục này?")) {
            return;
        }
        HangMuc hangMuc = listView.getSelectionModel().getSelectedItem();
        if (hangMuc == null) {
            return;
        }
        if (hangMuc.getId() == 0) {
            hangMucObservableList.remove(hangMuc);
            return;
        }
        databaseModifyHangMucService.deleteHangMuc(hangMuc.getId());
        refreshList();
        refreshChildrenList(0);
    }


    /**
     * Handles the action event for navigating to the next screen.
     *
     * @param event The ActionEvent triggering the action.
     */
    @FXML
    void nextAction(ActionEvent event) {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectID = listView.getSelectionModel().getSelectedItem().getId();
        DatabaseModifyVatLieuScene databaseModifyVatLieuScene = DIContainer.get();
        HBox hBox = (HBox) ((AnchorPane)databaseModifyVatLieuScene.getRoot()).getChildren().get(0);

        ((AnchorPane)this.root).getChildren().clear();
        ((AnchorPane)this.root).getChildren().add(hBox);
        databaseModifyVatLieuScene.getController().init(selectID);
        databaseModifyVatLieuScene.getController().setRoot(this.root);
    }

    /**
     * Handles the scene switching based on the ActionEvent source.
     * If the source is the backButton, navigates to the DatabaseModifyNoiThatScene.
     *
     * @param event The ActionEvent triggering the action.
     */
    @FXML
    void sceneSwitcher(ActionEvent event) {
        Object source = event.getSource();
        if (source == backButton) {
            DatabaseModifyNoiThatScene databaseModifyNoiThatScene = DIContainer.get();
            HBox hBox = (HBox) ((AnchorPane)databaseModifyNoiThatScene.getRoot()).getChildren().get(0);
            ((AnchorPane)this.root).getChildren().clear();
            ((AnchorPane)this.root).getChildren().add(hBox);
            databaseModifyNoiThatScene.getController().refresh();
            databaseModifyNoiThatScene.getController().setRoot(this.root);
        }
    }

    /**
     * Initializes the controller with the specified parent ID, refreshes the list views, and logs the parent ID to the console.
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
     * Refreshes both the main list view and its children list view, clearing the selection in the main list view.
     */
    public void refresh() {
        refreshList();
        refreshChildrenList(0);
        listView.getSelectionModel().clearSelection();
    }

    /**
     * Refreshes the main list view with HangMuc items associated with the current parent ID.
     */
    private void refreshList() {
        List<HangMuc> hangMucList = databaseModifyHangMucService.findHangMucByParentId(parentID);
        if (hangMucList == null) {
            hangMucList = new ArrayList<>();
        }
        hangMucObservableList.clear();
        hangMucObservableList.addAll(hangMucList);
//        listView.getSelectionModel().select(0);
    }

    /**
     * Refreshes the children list view with VatLieu items associated with the provided parent ID.
     *
     * @param parentID The ID of the parent element.
     */

    private void refreshChildrenList(int parentID) {
        if (parentID == 0) {
            vatLieuObservableList.clear();
            return;
        }
        List<VatLieu> vatLieuList = databaseModifyVatlieuService.findVatLieuByParentId(parentID);
        if (vatLieuList == null) {
            vatLieuList = new ArrayList<>();
        }
        vatLieuObservableList.clear();
        vatLieuObservableList.addAll(vatLieuList);
    }


    /**
     * Initializes the controller when the FXML file is loaded.
     *
     * @param url            The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getSampleDataButton.disableProperty().bind(Bindings.size(hangMucObservableList).greaterThan(0));
        Title.setText("Danh sách hạng mục");
        listView.setItems(hangMucObservableList);
        listView.setEditable(true);
        listView.setCellFactory(param -> new CustomEditingListCell<>());
        listView.setOnEditCommit(event -> {
            HangMuc item = event.getNewValue();
            item.setName(DBModifyUtils.getNotDuplicateName(item.getName(), hangMucObservableList));
            if (item.getId() == 0) {
                databaseModifyHangMucService.addNewHangMuc(item, this.parentID);
            } else {
                databaseModifyHangMucService.EditHangMuc(item);
            }
            refreshList();
        });
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                HangMuc noiThat = listView.getSelectionModel().getSelectedItem();
                if (noiThat == null) {
                    return;
                }
                refreshChildrenList(noiThat.getId());
            }
        });
        childrenList.setEditable(false);
        childrenList.setCellFactory(param -> new CustomEditingListCell<>());
        childrenList.setItems(vatLieuObservableList);
        setupButton();
    }
    private void setupButton() {
        deleteButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull());
        nextButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull());
        swapButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull());
    }
    /**
     * Handles key pressed events to perform corresponding actions based on the keyboard shortcuts.
     *
     * @param event The KeyEvent triggered by the user.
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
        else if (KeyboardUtils.isRightKeyCombo(Action.EXIT, event)) {
            backButton.fire();
        }
    }
}

