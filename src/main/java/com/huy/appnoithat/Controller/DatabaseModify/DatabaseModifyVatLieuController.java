package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Controller.DatabaseModify.Cell.CustomEditingListCell;
import com.huy.appnoithat.Controller.DatabaseModify.Common.DBModifyUtils;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Scene.DatabaseModify.ChangeProductSpecificationScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyHangMucScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyThongSoService;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyVatlieuService;
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
    private ListView<VatLieu> listView;
    @FXML
    private TableView<ThongSo> tableView;
    @FXML
    private TableColumn<ThongSo, Double> Cao, Dai, Rong;
    @FXML
    private TableColumn<ThongSo, Long> DonGia;
    @FXML
    private TableColumn<ThongSo, String> DonVi;
    private int parentID;
    private final DatabaseModifyVatlieuService databaseModifyVatlieuService;
    private final DatabaseModifyThongSoService databaseModifyThongSoService;
    private final ObservableList<ThongSo> thongSoObservableList;
    private final ObservableList<VatLieu> vatLieuObservableList;
    @Setter
    private Parent root;

    public DatabaseModifyVatLieuController() {
        databaseModifyThongSoService = new DatabaseModifyThongSoService();
        databaseModifyVatlieuService = new DatabaseModifyVatlieuService();
        vatLieuObservableList = FXCollections.observableArrayList();
        thongSoObservableList = FXCollections.observableArrayList();
    }

    @FXML
    void addAction(ActionEvent event) {
        int currentPos = vatLieuObservableList.size();
        databaseModifyVatlieuService.addNewVatLieu(new VatLieu(0, DBModifyUtils.getNewName(currentPos),
                new ThongSo(0, 0.0, 0.0, 0.0, " ", 0L)), this.parentID);
        refreshList();
    }

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

    public void init(int parentID) {
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
        List<VatLieu> vatLieuList = databaseModifyVatlieuService.findVatLieuByParentId(parentID);
        if (vatLieuList == null) {
            vatLieuList = new ArrayList<>();
        }
        vatLieuObservableList.clear();
        vatLieuObservableList.addAll(vatLieuList);
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Title.setText("Danh sách vật liệu");
        setUpVatLieuListView();
        setUpThongSoTableView();
    }

    private void setUpVatLieuListView() {
        listView.setItems(vatLieuObservableList);
        listView.setEditable(true);
        listView.setCellFactory(param -> new CustomEditingListCell<>());
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

    private void setUpThongSoTableView() {
        tableView.setItems(thongSoObservableList);
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
}
