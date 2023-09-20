package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import com.huy.appnoithat.Scene.LuaChonNoiThatScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewTabController implements Initializable {
    @FXML
    private TabPane tabPane;

    @FXML
    void newTabButtonHandler(ActionEvent event) {
        if (tabPane.getSelectionModel().getSelectedItem() != null) {
            Node nodeFromCurrentTab = tabPane.getSelectionModel().getSelectedItem().getContent();
            Tab newtab = createNewTab();
            duplicateTruongThongTin(nodeFromCurrentTab, newtab.getContent());
        } else {
            createNewTab();
        }
    }

    private Tab createNewTab() {
        Tab newTab = setUpTab();
        try {
            Node root = LuaChonNoiThatScene.getNewRoot();
            newTab.setContent(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        contextMenu.getItems().add(nhanBanMenuItem);

        newTab.contextMenuProperty().set(contextMenu);
        return newTab;
    }

    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
    }

    private void duplicateTab(ActionEvent action, Tab currentTab) {
        Node nodeFromCurrentTab = currentTab.getContent();
        Tab newTab = setUpTab();
        try {
            Node root = LuaChonNoiThatScene.getNewRoot();
            newTab.setContent(duplicateContent(nodeFromCurrentTab, root));
        } catch (IOException e) {
            throw new RuntimeException();
        }
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
        TextField TenKhachHang = (TextField) nodeFromCurrentTab.lookup("#TenKhachHang");
        TextField DienThoaiKhachHang = (TextField) nodeFromCurrentTab.lookup("#DienThoaiKhachHang");
        TextField DiaChiKhachHang = (TextField) nodeFromCurrentTab.lookup("#DiaChiKhachHang");
        TextField SanPham = (TextField) nodeFromCurrentTab.lookup("#SanPham");
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
        TextField DuplicateTenKhachHang = (TextField) nodeFromNewTab.lookup("#TenKhachHang");
        DuplicateTenKhachHang.setText(TenKhachHang.getText());
        TextField DuplicateDienThoaiKhachHang = (TextField) nodeFromNewTab.lookup("#DienThoaiKhachHang");
        DuplicateDienThoaiKhachHang.setText(DienThoaiKhachHang.getText());
        TextField DuplicateDiaChiKhachHang = (TextField) nodeFromNewTab.lookup("#DiaChiKhachHang");
        DuplicateDiaChiKhachHang.setText(DiaChiKhachHang.getText());
        TextField DuplicateSanPham = (TextField) nodeFromNewTab.lookup("#SanPham");
        DuplicateSanPham.setText(SanPham.getText());
        ImageView DuplicateImageView = (ImageView) nodeFromNewTab.lookup("#ImageView");
        DuplicateImageView.setImage(imageView.getImage());

        TextArea DuplicateNoteTextArea = (TextArea) nodeFromNewTab.lookup("#noteTextArea");
        DuplicateNoteTextArea.setText(noteTextArea.getText());
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

    public void init() {
        tabPane.getTabs().clear();
        createNewTab();
    }
}
