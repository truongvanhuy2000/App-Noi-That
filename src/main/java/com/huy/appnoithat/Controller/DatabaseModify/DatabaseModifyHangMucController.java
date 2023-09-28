package com.huy.appnoithat.Controller.DatabaseModify;

import com.huy.appnoithat.Controller.DatabaseModify.Cell.CustomEditingListCell;
import com.huy.appnoithat.Controller.DatabaseModify.Common.DBModifyUtils;
import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyNoiThatScene;
import com.huy.appnoithat.Scene.DatabaseModify.DatabaseModifyVatLieuScene;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyHangMucService;
import com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyVatlieuService;
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

public class DatabaseModifyHangMucController implements Initializable {
    @FXML
    private Button addButton, backButton, deleteButton, nextButton;
    @FXML
    private ListView<VatLieu> childrenList;
    @FXML
    private ListView<HangMuc> listViewHangMuc;
    private int parentID;
    private final DatabaseModifyHangMucService databaseModifyHangMucService;
    private final DatabaseModifyVatlieuService databaseModifyVatlieuService;
    private final ObservableList<HangMuc> hangMucObservableList;
    private final ObservableList<VatLieu> vatLieuObservableList;

    public DatabaseModifyHangMucController() {
        databaseModifyHangMucService = new com.huy.appnoithat.Service.DatabaseModifyService.DatabaseModifyHangMucService();
        databaseModifyVatlieuService = new DatabaseModifyVatlieuService();
        vatLieuObservableList = FXCollections.observableArrayList();
        hangMucObservableList = FXCollections.observableArrayList();
    }

    @FXML
    void addAction(ActionEvent event) {
        int currentPos = hangMucObservableList.size();
        databaseModifyHangMucService.addNewHangMuc(new HangMuc(0, DBModifyUtils.getNewName(currentPos), new ArrayList<>()), this.parentID);
        refreshList();
    }

    @FXML
    void deleteAction(ActionEvent event) {
        HangMuc hangMuc = listViewHangMuc.getSelectionModel().getSelectedItem();
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

    @FXML
    void nextAction(ActionEvent event) {
        if (listViewHangMuc.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        int selectID = listViewHangMuc.getSelectionModel().getSelectedItem().getId();
        Scene scene = null;
        Stage stage = null;
        Object source = event.getSource();
        stage = (Stage) ((Node) source).getScene().getWindow();
        if (source == nextButton) {
            scene = DatabaseModifyVatLieuScene.getInstance().getScene();
            DatabaseModifyVatLieuScene.getInstance().getController().init(selectID);
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
            scene = DatabaseModifyNoiThatScene.getInstance().getScene();
            DatabaseModifyNoiThatScene.getInstance().getController().refresh();
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
        List<HangMuc> hangMucList = databaseModifyHangMucService.findHangMucByParentId(parentID);
        if (hangMucList == null) {
            hangMucList = new ArrayList<>();
        }
        hangMucObservableList.clear();
        hangMucObservableList.addAll(hangMucList);
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewHangMuc.setItems(hangMucObservableList);
        listViewHangMuc.setEditable(true);
        listViewHangMuc.setCellFactory(param -> new CustomEditingListCell<>());
        listViewHangMuc.setOnEditCommit(event -> {
            HangMuc item = event.getNewValue();
            item.setName(DBModifyUtils.getNotDuplicateName(item.getName(), hangMucObservableList));
            if (item.getId() == 0) {
                databaseModifyHangMucService.addNewHangMuc(item, this.parentID);
            } else {
                databaseModifyHangMucService.EditHangMuc(item);
            }
            refreshList();
        });
        listViewHangMuc.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                HangMuc noiThat = listViewHangMuc.getSelectionModel().getSelectedItem();
                if (noiThat == null) {
                    return;
                }
                refreshChildrenList(noiThat.getId());
            }
        });
        childrenList.setEditable(false);
        childrenList.setCellFactory(param -> new CustomEditingListCell<>());
        childrenList.setItems(vatLieuObservableList);
    }
}

