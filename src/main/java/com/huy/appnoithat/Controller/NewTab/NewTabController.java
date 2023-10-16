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


    /**
     * Handles the action when the "New Tab" button is clicked.
     *
     * @param event The ActionEvent triggering the new tab creation.
     */
    @FXML
    void newTabButtonHandler(ActionEvent event) {
        // Check if there is a currently selected tab
        if (tabPane.getSelectionModel().getSelectedItem() != null) {
            // Get the content from the current tab
            Node nodeFromCurrentTab = tabPane.getSelectionModel().getSelectedItem().getContent();

            // Create a new tab with a blank state and duplicate the content from the current tab
            Tab newtab = createNewTab(TabState.BLANK_TAB, null);
            duplicateTruongThongTin(nodeFromCurrentTab, newtab.getContent());
        } else {

            // If no tab is selected, simply create a new blank tab
            Tab newtab = createNewTab(TabState.BLANK_TAB, null);
        }
    }

    /**
     * Creates a new tab with the specified tab state and import directory.
     *
     * @param tabState        The state of the tab (import or blank).
     * @param importDirectory The directory to import data from (null if creating a blank tab).
     * @return The newly created tab.
     */
    private Tab createNewTab(TabState tabState, String importDirectory) {
        // Create a new instance of LuaChonNoiThatScene
        LuaChonNoiThatScene luaChonNoiThatScene = new LuaChonNoiThatScene();

        // Determine the tab name based on the tab state
        String tabName = "";
        if (TabState.IMPORT_TAB == tabState) {
            tabName = new File(importDirectory).getAbsoluteFile().getName();
        }
        else {
            tabName = "Tab mới";
        }

        // Set up the new tab with the determined tab name
        Tab newTab = setUpTab(tabName);

        // Get the root node from LuaChonNoiThatScene
        Node root = luaChonNoiThatScene.getRoot();

        // Set the content of the new tab to the root node
        newTab.setContent(root);

        // Perform specific actions based on the tab state
        if (tabState == TabState.IMPORT_TAB) {
            // If it's an import tab, import data from the specified directory
            luaChonNoiThatScene.getLuaChonNoiThatController().importFile(importDirectory);
        }
        else {
            // If it's a blank tab, initialize saved information
            initSavedThongTinCongTy(newTab.getContent(), persistenceStorageService.getThongTinCongTy());
        }

        // Add the new tab to the tab pane
        addNewTabToPane(newTab);

        // Return the newly created tab
        return newTab;
    }

    /**
     * Adds a new tab to the TabPane and selects it.
     *
     * @param newTab The tab to be added.
     */
    private void addNewTabToPane(Tab newTab) {
        // Add the new tab to the TabPane
        tabPane.getTabs().add(newTab);
        // Select the newly added tab to make it active
        tabPane.getSelectionModel().select(newTab);
    }

    /**
     * Sets up a new tab with the given name and configures context menu options.
     *
     * @param name The name of the tab.
     * @return The configured tab.
     */
    private Tab setUpTab(String name) {
        // If name is null, set a default name
        if (name == null) {
            name = "Tab mới";
        }
        // Create a new tab with the specified name
        Tab newTab = new Tab(name);

        // Create context menu items
        ContextMenu contextMenu = new ContextMenu();
        MenuItem nhanBanMenuItem = new MenuItem("Nhân bản");
        nhanBanMenuItem.setOnAction(event -> duplicateTab(event, newTab));
        MenuItem renameTab = new MenuItem("Đổi tên");
        renameTab.setOnAction(event -> {
            // Show a dialog to get a new name for the tab
            TextInputDialog dialog = new TextInputDialog(newTab.getText());
            dialog.setTitle("Đổi tên tab");
            dialog.setHeaderText("Đổi tên tab");
            dialog.setContentText("Nhập tên mới:");

            // Update tab name if user provides a new name
            dialog.showAndWait().ifPresent(newTab::setText);
        });

        // Create a context menu and add menu items
        contextMenu.getItems().add(nhanBanMenuItem);
        contextMenu.getItems().add(renameTab);

        // Set the context menu for the tab
        newTab.contextMenuProperty().set(contextMenu);
        return newTab;
    }

    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
    }

    /**
     * Duplicates the content of the current tab and creates a new tab with the duplicated content.
     *
     * @param action     The ActionEvent triggering the duplication.
     * @param currentTab The current tab whose content needs to be duplicated.
     */
    private void duplicateTab(ActionEvent action, Tab currentTab) {
        // Get the content from the current tab
        Node nodeFromCurrentTab = currentTab.getContent();

        // Create a new tab with a null name (it will be set in setUpTab method)
        Tab newTab = setUpTab(null);

        // Get the root node from a new instance of LuaChonNoiThatScene
        Node root = new LuaChonNoiThatScene().getRoot();

        // Duplicate the content from the current tab and set it to the new tab
        newTab.setContent(duplicateContent(nodeFromCurrentTab, root));

        // Add the new tab to the tab pane
        addNewTabToPane(newTab);
    }

    /**
     * Duplicates content from nodes of the current tab to nodes of the new tab.
     *
     * @param nodeFromCurrentTab The node from the current tab to duplicate content from.
     * @param nodeFromNewTab     The node from the new tab to duplicate content to.
     * @return The node from the new tab after duplicating content.
     */
    private Node duplicateContent(Node nodeFromCurrentTab, Node nodeFromNewTab) {
        // Duplicate content from the current tab to the new tab's nodes
        duplicateTruongThongTin(nodeFromCurrentTab, nodeFromNewTab);
        duplicateBangNoiThat(nodeFromCurrentTab, nodeFromNewTab);
        duplicateBangThanhToan(nodeFromCurrentTab, nodeFromNewTab);

        // Return the node from the new tab after duplicating content
        return nodeFromNewTab;
    }


    /**
     * Duplicates specific content from nodes of the current tab to nodes of the new tab.
     *
     * @param nodeFromCurrentTab The node from the current tab to duplicate content from.
     * @param nodeFromNewTab     The node from the new tab to duplicate content to.
     */
    private void duplicateTruongThongTin(Node nodeFromCurrentTab, Node nodeFromNewTab) {
        // Retrieve specific nodes from the current tab
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

    /**
     * Initializes specific fields in the node with data from a ThongTinCongTy object.
     *
     * @param nodeFromCurrentTab The node from which to initialize fields.
     * @param thongTinCongTy     The ThongTinCongTy object containing data.
     */
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

    /**
     * Duplicates the content of a TreeTableView from the current tab to a TreeTableView in a new tab.
     *
     * @param nodeFromCurrentTab The node from the current tab containing the original TreeTableView.
     * @param nodeFromNewTab     The node from the new tab containing the duplicate TreeTableView.
     */
    private void duplicateBangNoiThat(Node nodeFromCurrentTab, Node nodeFromNewTab) {
        // Retrieve the original TreeTableView from the current tab
        TreeTableView<BangNoiThat> bangNoiThat = (TreeTableView<BangNoiThat>) nodeFromCurrentTab.lookup("#TableNoiThat");
        // Retrieve the duplicate TreeTableView from the new tab
        TreeTableView<BangNoiThat> DuplicateBangNoiThat = (TreeTableView<BangNoiThat>) nodeFromNewTab.lookup("#TableNoiThat");
        // Duplicate the content by creating a deep copy of the root item
        DuplicateBangNoiThat.setRoot(deepcopy(bangNoiThat.getRoot()));
    }

    /**
     * Duplicates the content of a TableView from the current tab to a TableView in a new tab.
     *
     * @param nodeFromCurrentTab The node from the current tab containing the original TableView.
     * @param nodeFromNewTab     The node from the new tab containing the duplicate TableView.
     */
    private void duplicateBangThanhToan(Node nodeFromCurrentTab, Node nodeFromNewTab) {
        // Retrieve the original TableView from the current tab

        TableView<BangThanhToan> bangThanhToan = (TableView<BangThanhToan>) nodeFromCurrentTab.lookup("#bangThanhToan");
        // Retrieve the duplicate TableView from the new tab
        TableView<BangThanhToan> DuplicateBangThanhToan = (TableView<BangThanhToan>) nodeFromNewTab.lookup("#bangThanhToan");

        // Clear the duplicate TableView and add items from the original TableView
        DuplicateBangThanhToan.getItems().clear();
        DuplicateBangThanhToan.getItems().addAll(bangThanhToan.getItems());
    }


    /**
     * Creates a deep copy of a TreeItem and its children.
     *
     * @param item The original TreeItem to be copied.
     * @return A deep copy of the original TreeItem.
     */
    private TreeItem<BangNoiThat> deepcopy(TreeItem<BangNoiThat> item) {
        TreeItem<BangNoiThat> copy = createNewItem(item.getValue());
        for (TreeItem<BangNoiThat> child : item.getChildren()) {
            copy.getChildren().add(deepcopy(child));
        }
        return copy;
    }

    /**
     * Creates a new TreeItem with the provided item and ensures it's initially expanded.
     *
     * @param item The data item to be wrapped in the TreeItem.
     * @return A new TreeItem with the provided item, initially expanded.
     */
    private TreeItem<BangNoiThat> createNewItem(BangNoiThat item) {
        // Create a new TreeItem with the provided data item
        TreeItem<BangNoiThat> newItem = new TreeItem<>(item);
        newItem.setExpanded(true);

        // Add an event handler to handle branch collapsed events and keep the item expanded
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
