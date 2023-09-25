package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Controller.DatabaseModify.Cell.CustomEditingListCell;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DatabaseModifyVatLieuController implements Initializable {
    @FXML
    private Button addButton, backButton, deleteButton, nextButton;
    @FXML
    private TableView<ThongSo> tableViewThongSo;
    @FXML
    private TableColumn<ThongSo, Float> Cao, Dai, Rong;
    @FXML
    private TableColumn<ThongSo, Long> DonGia;
    @FXML
    private TableColumn<ThongSo, String> DonVi;
    @FXML
    private ListView<VatLieu> listViewVatLieu;
    private int parentID;
    private final DatabaseModifyVatlieuService databaseModifyVatlieuService;
    private final DatabaseModifyThongSoService databaseModifyThongSoService;
    private final ObservableList<ThongSo> thongSoObservableList;
    private final ObservableList<VatLieu> vatLieuObservableList;

    public DatabaseModifyVatLieuController() {
        databaseModifyThongSoService = new DatabaseModifyThongSoService();
        databaseModifyVatlieuService = new DatabaseModifyVatlieuService();
        vatLieuObservableList = FXCollections.observableArrayList();
        thongSoObservableList = FXCollections.observableArrayList();
    }

    @FXML
    void addAction(ActionEvent event) {
        databaseModifyVatlieuService.addNewVatLieu(new VatLieu(0, "<Thêm mới>",
                new ThongSo(0, 0f, 0f, 0f, " ", 0L)), this.parentID);
        refreshList();
    }

    @FXML
    void deleteAction(ActionEvent event) {
        VatLieu vatLieu = listViewVatLieu.getSelectionModel().getSelectedItem();
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
        if (listViewVatLieu.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectID = listViewVatLieu.getSelectionModel().getSelectedItem().getId();
        Scene scene = null;
        Stage stage = null;
        Object source = event.getSource();
        stage = (Stage) ((Node) source).getScene().getWindow();
        if (source == nextButton) {
            scene = ChangeProductSpecificationScene.getInstance().getScene();
            ChangeProductSpecificationScene.getInstance().getController().initializeThongSo(selectID);
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
            scene = DatabaseModifyHangMucScene.getInstance().getScene();
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

    private void refreshList() {
        List<VatLieu> vatLieuList = databaseModifyVatlieuService.findVatLieuByID(parentID);
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
        List<ThongSo> thongSoList = databaseModifyThongSoService.findThongSoByID(parentID);
        if (thongSoList == null) {
            thongSoList = new ArrayList<>();
        }
        thongSoObservableList.clear();
        thongSoObservableList.addAll(thongSoList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpVatLieuListView();
        setUpThongSoTableView();
    }

    private void setUpVatLieuListView() {
        listViewVatLieu.setItems(vatLieuObservableList);
        listViewVatLieu.setEditable(true);
        listViewVatLieu.setCellFactory(param -> new CustomEditingListCell<>());
        listViewVatLieu.setOnEditCommit(event -> {
            VatLieu item = event.getNewValue();
            item.setName(event.getNewValue().getName());
            if (item.getId() == 0) {
                databaseModifyVatlieuService.addNewVatLieu(item, this.parentID);
            } else {
                databaseModifyVatlieuService.EditVatLieu(item);
            }
            refreshList();
        });
        listViewVatLieu.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                VatLieu noiThat = listViewVatLieu.getSelectionModel().getSelectedItem();
                if (noiThat == null) {
                    return;
                }
                refreshChildrenList(noiThat.getId());
            }
        });
    }

    private void setUpThongSoTableView() {
        tableViewThongSo.setItems(thongSoObservableList);
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
