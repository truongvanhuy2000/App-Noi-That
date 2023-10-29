package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Controller.DatabaseModify.Cell.CustomEditingListCell;
import com.huy.appnoithat.Controller.DatabaseModify.Common.DBModifyUtils;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Enums.Action;
import com.huy.appnoithat.Scene.DatabaseModify.ChangeProductSpecificationScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyHangMucScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyThongSoService;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyVatlieuService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DatabaseModifyVatLieuController implements Initializable {
    @FXML
    private Label Title;
    @FXML
    private Button addButton, backButton, deleteButton, nextButton;
    @FXML
    private Button getSampleDataButton;
    @FXML
    private ListView<VatLieu> listView;
    @FXML
    private TableView<ThongSo> tableView;
    @FXML
    private TableColumn<ThongSo, Double> Cao, Dai, Rong;
    @FXML
    private TableColumn<ThongSo, Long> DonGia;
    @FXML
    private TableColumn<ThongSo, String> DonVi;
    @FXML
    private Button swapButton;
    private int parentID;
    private final DatabaseModifyVatlieuService databaseModifyVatlieuService;
    private final DatabaseModifyThongSoService databaseModifyThongSoService;
    private final ObservableList<ThongSo> thongSoObservableList;
    private final ObservableList<VatLieu> vatLieuObservableList;
    @Setter
    private Parent root;

    /**
     * Constructs a new DatabaseModifyVatLieuController instance.
     * Initializes the necessary services and observable lists.
     */
    public DatabaseModifyVatLieuController() {
        databaseModifyThongSoService = new DatabaseModifyThongSoService();
        databaseModifyVatlieuService = new DatabaseModifyVatlieuService();
        vatLieuObservableList = FXCollections.observableArrayList();
        thongSoObservableList = FXCollections.observableArrayList();
    }

    @FXML
    void swap(ActionEvent event) {
        VatLieu vatLieu = listView.getSelectionModel().getSelectedItem();
        if (vatLieu == null) {
            return;
        }
        String swapIndex = PopupUtils.openDialog("Đổi vị trí", "Đổi vị trí", "Vị trí");
        if (swapIndex == null || swapIndex.isEmpty()) {
            return;
        }
        int index;
        try {
            index = Integer.parseInt(swapIndex);
            if (index == 0 || index > vatLieuObservableList.size()) {
                return;
            }
        } catch (NumberFormatException e) {
            return;
        }
        databaseModifyVatlieuService.swap(vatLieu.getId(), vatLieuObservableList.get(index - 1).getId());
        refreshList();
    }

    /**
     * Handles the action when the user adds a new item.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void addAction(ActionEvent event) {
        int currentPos = vatLieuObservableList.size();
        databaseModifyVatlieuService.addNewVatLieu(new VatLieu(0, DBModifyUtils.getNewName(currentPos),
                new ThongSo(0, 0.0, 0.0, 0.0, " ", 0L)), this.parentID);
        refreshList();
    }
    @FXML
    void FetchSampleData(ActionEvent event) {
        databaseModifyVatlieuService.fetchSampleVatLieuData(this.parentID);
        refresh();
        for (VatLieu item : vatLieuObservableList) {
            databaseModifyThongSoService.fetchSampleThongSoData(item.getId());
        }
    }
    /**
     * Handles the action when the user deletes an item.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void deleteAction(ActionEvent event) {
        VatLieu vatLieu = listView.getSelectionModel().getSelectedItem();
        if (vatLieu == null) {
            return;
        }
        if (vatLieu.getId() == 0) {
            vatLieuObservableList.remove(vatLieu);
            return;
        }
        databaseModifyVatlieuService.deleteVatLieu(vatLieu.getId());
        refreshList();
    }


    /**
     * Handles the action when the user selects the next item.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void nextAction(ActionEvent event) {
        if (listView.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectID = listView.getSelectionModel().getSelectedItem().getId();
        ChangeProductSpecificationScene changeProductSpecificationScene = new ChangeProductSpecificationScene();
        VBox vBox = (VBox) ((AnchorPane)changeProductSpecificationScene.getRoot()).getChildren().get(0);
        ((AnchorPane)this.root).getChildren().clear();
        ((AnchorPane)this.root).getChildren().add(vBox);
        ChangeProductSpecificationScene.getController().initializeThongSo(selectID);
        ChangeProductSpecificationScene.getController().setRoot(this.root);
    }


    /**
     * Handles the action for switching scenes.
     *
     * @param event The action event triggered by the user.
     */
    @FXML
    void sceneSwitcher(ActionEvent event) {
        Scene scene = null;
        Stage stage = null;
        Object source = event.getSource();
        stage = (Stage) ((Node) source).getScene().getWindow();
        if (source == backButton) {
            DatabaseModifyHangMucScene databaseModifyHangMucScene = new DatabaseModifyHangMucScene();
            HBox hBox = (HBox) ((AnchorPane)databaseModifyHangMucScene.getRoot()).getChildren().get(0);
            ((AnchorPane)this.root).getChildren().clear();
            ((AnchorPane)this.root).getChildren().add(hBox);
            DatabaseModifyHangMucScene.getController().refresh();
            DatabaseModifyHangMucScene.getController().setRoot(this.root);
        }
    }

    @FXML
    void tableClickToSelectItem(MouseEvent event) {

    }


    /**
     * Initializes the controller with the given parent ID.
     *
     * @param parentID The ID of the parent element.
     */
    public void init(int parentID) {
        this.parentID = parentID;
        refreshList();
        refreshChildrenList(0);
    }

    /**
     * Refreshes the view by updating the lists and clearing the selection.
     */
    public void refresh() {
        refreshList();
        refreshChildrenList(0);
        listView.getSelectionModel().clearSelection();
    }

    /**
     * Refreshes the list of VatLieu items.
     */
    private void refreshList() {
        List<VatLieu> vatLieuList = databaseModifyVatlieuService.findVatLieuByParentId(parentID);
        if (vatLieuList == null) {
            vatLieuList = new ArrayList<>();
        }
        vatLieuObservableList.clear();
        vatLieuObservableList.addAll(vatLieuList);
    }


    /**
     * Refreshes the list of ThongSo items for the specified parentID.
     * If the parentID is 0, clears the ThongSo list.
     *
     * @param parentID the parent ID for which to refresh the ThongSo list
     */
    private void refreshChildrenList(int parentID) {
        if (parentID == 0) {
            thongSoObservableList.clear();
            return;
        }
        List<ThongSo> thongSoList = databaseModifyThongSoService.findThongSoByParentId(parentID);
        if (thongSoList == null) {
            thongSoList = new ArrayList<>();
        }
        thongSoObservableList.clear();
        thongSoObservableList.addAll(thongSoList);
    }


    /**
     * Initializes the controller after FXML file loaded.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getSampleDataButton.disableProperty().bind(Bindings.size(vatLieuObservableList).greaterThan(0));
        Title.setText("Danh sách vật liệu");
        setUpVatLieuListView();
        setUpThongSoTableView();
    }

    /**
     * Sets up the VatLieu list view with appropriate cell factories and event listeners.
     * Binds VatLieuObservableList to the list view, sets up cell factories for editing, and adds selection change listeners.
     */
    private void setUpVatLieuListView() {
        // Binds VatLieuObservableList to the list view
        listView.setItems(vatLieuObservableList);

        // Makes the list view cells editable and customizes the editing behavior
        listView.setEditable(true);
        listView.setCellFactory(param -> new CustomEditingListCell<>());

        // Handles edit commit events to update or add new VatLieu items
        listView.setOnEditCommit(event -> {
            VatLieu item = event.getNewValue();
            item.setName(DBModifyUtils.getNotDuplicateName(item.getName(), vatLieuObservableList));
            if (item.getId() == 0) {
                databaseModifyVatlieuService.addNewVatLieu(item, this.parentID);
            } else {
                databaseModifyVatlieuService.EditVatLieu(item);
            }
            refreshList();
        });

        // Adds a selection change listener to update child items when a VatLieu item is selected
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                VatLieu noiThat = listView.getSelectionModel().getSelectedItem();
                if (noiThat == null) {
                    return;
                }
                refreshChildrenList(noiThat.getId());
            }
        });
    }

    /**
     * Sets up the ThongSo table view with appropriate cell value factories.
     * Binds ThongSoObservableList to the table view and sets up cell value factories for each column.
     */
    private void setUpThongSoTableView() {
        // Binds ThongSoObservableList to the table view
        tableView.setItems(thongSoObservableList);

        // Set cell value factories for each column
        Dai.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return new SimpleObjectProperty<>(param.getValue().getDai());
        });
        Rong.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return new SimpleObjectProperty<>(param.getValue().getRong());
        });
        Cao.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return new SimpleObjectProperty<>(param.getValue().getCao());
        });
        DonGia.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return new SimpleObjectProperty<>(param.getValue().getDon_gia());
        });
        DonVi.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return new SimpleObjectProperty<>(param.getValue().getDon_vi());
        });
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
