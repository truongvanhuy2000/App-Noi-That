package com.huy.appnoithat.Controller.NewTab.Operation;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.State;
import com.huy.appnoithat.Controller.NewTab.NewTabController;
import com.huy.appnoithat.Controller.NewTab.TabContent;
import com.huy.appnoithat.Controller.NewTab.TabState;
import com.huy.appnoithat.DataModel.DataPackage;
import com.huy.appnoithat.DataModel.SaveFile.TabData;
import com.huy.appnoithat.Scene.LuaChonNoiThat.LuaChonNoiThatScene;
import com.huy.appnoithat.Service.LuaChonNoiThat.NoiThatFileService;
import com.huy.appnoithat.Service.PersistenceStorage.StorageService;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class TabOperation {
    private final NewTabController newTabController;
    private final NoiThatFileService noiThatFileService;
    private final StorageService persistenceStorageService;
    private final Stage currentStage;
    private final TabPane tabPane;

    public TabOperation(NewTabController newTabController) {
        this.newTabController = newTabController;
        this.noiThatFileService = newTabController.getNoiThatFileService();
        this.persistenceStorageService = newTabController.getPersistenceStorageService();
        currentStage = newTabController.getCurrentStage();
        tabPane = newTabController.getTabPane();
    }

    private void duplicateTab(ActionEvent action, Tab currentTab) {
        TabContent currentTabContent = newTabController.getCurrentlyOpenTab().stream().filter(
                tabContent -> tabContent.getTab().equals(currentTab))
                .findFirst().orElse(null);
        if (currentTabContent == null) {
            throw new RuntimeException("Tab not found");
        }
        createTab(currentTab.getText(), currentTabContent.getLuaChonNoiThatController().exportData());
    }
    private LuaChonNoiThatScene createNoiThatScene() {
        LuaChonNoiThatScene luaChonNoiThatScene = new LuaChonNoiThatScene();
        luaChonNoiThatScene.getLuaChonNoiThatController().init(currentStage);
        return luaChonNoiThatScene;
    }
    public void createNewTab(TabState tabState, String importDirectory) {
        if (tabState == TabState.IMPORT_TAB) {
            newTabController.setCurrentState(State.OPEN_FROM_EXISTING_FILE);
            newTabController.setCurrentDirectory(importDirectory);
            importFromFile(importDirectory);
        } else if (tabState == TabState.BLANK_TAB) {
            newTabController.setCurrentState(State.NEW_FILE);
            createBlankTab();
        }
    }
    private void importFromFile(String directory) {
        List<TabData> tabDataList = noiThatFileService.importData(directory);
        for(TabData tabData : tabDataList) {
            createTab(tabData.getTabName(), tabData.getDataPackage());
        }
    }
    private void createTab(String TabName, DataPackage content) {
        LuaChonNoiThatScene luaChonNoiThatScene = createNoiThatScene();
        Tab newTab = setUpTab(TabName);
        addNewTabToPane(newTab);
        newTab.setContent(luaChonNoiThatScene.getRoot());
        luaChonNoiThatScene.getLuaChonNoiThatController().importData(content);
        TabContent tabContent = new TabContent(newTab, luaChonNoiThatScene, luaChonNoiThatScene.getLuaChonNoiThatController());
        newTabController.getCurrentlyOpenTab().add(tabContent);
    }
    private void createBlankTab() {
        createTab("Tab mới", DataPackage.builder()
                .thongTinCongTy(persistenceStorageService.getThongTinCongTy())
                .noteArea(persistenceStorageService.getNoteArea())
                .build());
    }
    private void addNewTabToPane(Tab newTab) {
        tabPane.getTabs().add(newTab);
        tabPane.getSelectionModel().select(newTab);
    }

    private Tab setUpTab(String name) {
        if (name == null) {
            name = "Tab mới";
        }
        Tab newTab = new Tab(name);
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
        newTab.setOnClosed(event -> {
            newTabController.getCurrentlyOpenTab().removeIf(tabContent -> tabContent.getTab().equals(newTab));
        });
        return newTab;
    }
}
