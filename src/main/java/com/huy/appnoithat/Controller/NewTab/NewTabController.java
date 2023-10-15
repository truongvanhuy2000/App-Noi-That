package com.huy.appnoithat.Controller.NewTab;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.Scene.LuaChonNoiThat.LuaChonNoiThatScene;
import com.huy.appnoithat.Service.PersistenceStorage.PersistenceStorageService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewTabController implements Initializable {
    @FXML
    private TabPane tabPane;
    private PersistenceStorageService persistenceStorageService;
    public NewTabController() {
        persistenceStorageService = PersistenceStorageService.getInstance();
    }
    @FXML
    void newTabButtonHandler(ActionEvent event) {
        if (tabPane.getSelectionModel().getSelectedItem() != null) {
            Node nodeFromCurrentTab = tabPane.getSelectionModel().getSelectedItem().getContent();
            Tab newtab = createNewTab(TabState.BLANK_TAB, null);
            duplicateTruongThongTin(nodeFromCurrentTab, newtab.getContent());
        } else {
            Tab newtab = createNewTab(TabState.BLANK_TAB, null);
        }
    }

    private Tab createNewTab(TabState tabState, String importDirectory) {
        LuaChonNoiThatScene luaChonNoiThatScene = new LuaChonNoiThatScene();
        Tab newTab = setUpTab();
        Node root = luaChonNoiThatScene.getRoot();
        newTab.setContent(root);
        if (tabState == TabState.IMPORT_TAB) {
            luaChonNoiThatScene.getLuaChonNoiThatController().importFile(importDirectory);
        }
        else {
            initSavedThongTinCongTy(newTab.getContent(), persistenceStorageService.getThongTinCongTy());
        }
        addNewTabToPane(newTab);
        return newTab;
    }

    private void addNewTabToPane(Tab newTab) {
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }

    private Tab setUpTab() {
        Tab newTab = new Tab("Tab mới");
        ContextMenu contextMenu = new ContextMenu();
        MenuItem nhanBanMenuItem = new MenuItem("Nhân bản");
        nhanBanMenuItem.setOnAction(event -> duplicateTab(event, newTab));
        MenuItem renameTab = new MenuItem("Đổi tên");
        renameTab.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog(newTab.getText());
            dialog.setTitle("Đổi tên tab");
            dialog.setHeaderText("Đổi tên tab");
            dialog.setContentText("Nhập tên mới:");
            dialog.showAndWait().ifPresent(newTab::setText);
        });

        contextMenu.getItems().add(nhanBanMenuItem);
        contextMenu.getItems().add(renameTab);
        newTab.contextMenuProperty().set(contextMenu);
        return newTab;
    }

    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
    }

    private void duplicateTab(ActionEvent action, Tab currentTab) {
        Node nodeFromCurrentTab = currentTab.getContent();
        Tab newTab = setUpTab();
        Node root = new LuaChonNoiThatScene().getRoot();
        newTab.setContent(duplicateContent(nodeFromCurrentTab, root));
        addNewTabToPane(newTab);
    }

    private Node duplicateContent(Node nodeFromCurrentTab, Node nodeFromNewTab) {
        duplicateTruongThongTin(nodeFromCurrentTab, nodeFromNewTab);
        duplicateBangNoiThat(nodeFromCurrentTab, nodeFromNewTab);
        duplicateBangThanhToan(nodeFromCurrentTab, nodeFromNewTab);
        return nodeFromNewTab;
    }

    private void duplicateTruongThongTin(Node nodeFromCurrentTab, Node nodeFromNewTab) {
        TextField TenCongTy = (TextField) nodeFromCurrentTab.lookup("#TenCongTy");
        TextField VanPhong = (TextField) nodeFromCurrentTab.lookup("#VanPhong");
        TextField DiaChiXuong = (TextField) nodeFromCurrentTab.lookup("#DiaChiXuong");
        TextField DienThoaiCongTy = (TextField) nodeFromCurrentTab.lookup("#DienThoaiCongTy");
        TextField Email = (TextField) nodeFromCurrentTab.lookup("#Email");

        ImageView imageView = (ImageView) nodeFromCurrentTab.lookup("#ImageView");

        TextArea noteTextArea = (TextArea) nodeFromCurrentTab.lookup("#noteTextArea");

        TextField DuplicateTenCongTy = (TextField) nodeFromNewTab.lookup("#TenCongTy");
        DuplicateTenCongTy.setText(TenCongTy.getText());
        TextField DuplicateVanPhong = (TextField) nodeFromNewTab.lookup("#VanPhong");
        DuplicateVanPhong.setText(VanPhong.getText());
        TextField DuplicateDiaChiXuong = (TextField) nodeFromNewTab.lookup("#DiaChiXuong");
        DuplicateDiaChiXuong.setText(DiaChiXuong.getText());
        TextField DuplicateDienThoaiCongTy = (TextField) nodeFromNewTab.lookup("#DienThoaiCongTy");
        DuplicateDienThoaiCongTy.setText(DienThoaiCongTy.getText());
        TextField DuplicateEmail = (TextField) nodeFromNewTab.lookup("#Email");
        DuplicateEmail.setText(Email.getText());
        ImageView DuplicateImageView = (ImageView) nodeFromNewTab.lookup("#ImageView");
        DuplicateImageView.setImage(imageView.getImage());

        TextArea DuplicateNoteTextArea = (TextArea) nodeFromNewTab.lookup("#noteTextArea");
        DuplicateNoteTextArea.setText(noteTextArea.getText());
    }

    private void initSavedThongTinCongTy(Node nodeFromCurrentTab, ThongTinCongTy thongTinCongTy) {
        TextField TenCongTy = (TextField) nodeFromCurrentTab.lookup("#TenCongTy");
        TextField VanPhong = (TextField) nodeFromCurrentTab.lookup("#VanPhong");
        TextField DiaChiXuong = (TextField) nodeFromCurrentTab.lookup("#DiaChiXuong");
        TextField DienThoaiCongTy = (TextField) nodeFromCurrentTab.lookup("#DienThoaiCongTy");
        TextField Email = (TextField) nodeFromCurrentTab.lookup("#Email");

        TenCongTy.setText(thongTinCongTy.getTenCongTy());
        VanPhong.setText(thongTinCongTy.getDiaChiVanPhong());
        DiaChiXuong.setText(thongTinCongTy.getDiaChiXuong());
        DienThoaiCongTy.setText(thongTinCongTy.getSoDienThoai());
        Email.setText(thongTinCongTy.getEmail());
    }
    private void duplicateBangNoiThat(Node nodeFromCurrentTab, Node nodeFromNewTab) {
        TreeTableView<BangNoiThat> bangNoiThat = (TreeTableView<BangNoiThat>) nodeFromCurrentTab.lookup("#TableNoiThat");
        TreeTableView<BangNoiThat> DuplicateBangNoiThat = (TreeTableView<BangNoiThat>) nodeFromNewTab.lookup("#TableNoiThat");
        DuplicateBangNoiThat.setRoot(deepcopy(bangNoiThat.getRoot()));
    }
    private void duplicateBangThanhToan(Node nodeFromCurrentTab, Node nodeFromNewTab) {
        TableView<BangThanhToan> bangThanhToan = (TableView<BangThanhToan>) nodeFromCurrentTab.lookup("#bangThanhToan");
        TableView<BangThanhToan> DuplicateBangThanhToan = (TableView<BangThanhToan>) nodeFromNewTab.lookup("#bangThanhToan");
        DuplicateBangThanhToan.getItems().clear();
        DuplicateBangThanhToan.getItems().addAll(bangThanhToan.getItems());
    }

    private TreeItem<BangNoiThat> deepcopy(TreeItem<BangNoiThat> item) {
        TreeItem<BangNoiThat> copy = createNewItem(item.getValue());
        for (TreeItem<BangNoiThat> child : item.getChildren()) {
            copy.getChildren().add(deepcopy(child));
        }
        return copy;
    }

    private TreeItem<BangNoiThat> createNewItem(BangNoiThat item) {
        TreeItem<BangNoiThat> newItem = new TreeItem<>(item);
        newItem.setExpanded(true);
        newItem.addEventHandler(TreeItem.branchCollapsedEvent(),
                (EventHandler<TreeItem.TreeModificationEvent<String>>) event -> event.getTreeItem().setExpanded(true));
        return newItem;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void init(TabState tabState, String importDirectory) {
        tabPane.getTabs().clear();
        Tab newTab = createNewTab(tabState, importDirectory);
    }

}
