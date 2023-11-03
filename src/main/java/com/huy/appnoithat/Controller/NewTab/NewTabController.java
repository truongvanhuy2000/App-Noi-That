package com.huy.appnoithat.Controller.NewTab;

import com.huy.appnoithat.DataModel.NtFile.DataPackage;
import com.huy.appnoithat.Scene.LuaChonNoiThat.LuaChonNoiThatScene;
import com.huy.appnoithat.Service.PersistenceStorage.PersistenceStorageService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewTabController implements Initializable {
    final static Logger LOGGER = LogManager.getLogger(NewTabController.class);
    @FXML
    private TabPane tabPane;
    private final PersistenceStorageService persistenceStorageService;
    private Stage currentStage;
    private List<TabContent> currentlyOpenTab;
    public NewTabController() {
        persistenceStorageService = PersistenceStorageService.getInstance();
        currentlyOpenTab = new ArrayList<>();
    }
    @FXML
    void newTabButtonHandler(ActionEvent event) {
        createNewTab();
    }
    private void createNewTab() {
        if (tabPane.getSelectionModel().getSelectedItem() != null) {
            createNewTab(TabState.BLANK_TAB, null);
        }
    }
    private LuaChonNoiThatScene createNoiThatScene() {
        LuaChonNoiThatScene luaChonNoiThatScene = new LuaChonNoiThatScene();
        luaChonNoiThatScene.getLuaChonNoiThatController().init(currentStage);
        return luaChonNoiThatScene;
    }
    private TabContent createNewTab(TabState tabState, String importDirectory) {
        LuaChonNoiThatScene luaChonNoiThatScene = createNoiThatScene();
        String tabName;
        if (TabState.IMPORT_TAB == tabState) {
            tabName = new File(importDirectory).getAbsoluteFile().getName();
        }
        else {
            tabName = "Tab mới";
        }
        Tab newTab = setUpTab(tabName);
        newTab.setContent(luaChonNoiThatScene.getRoot());
        if (tabState == TabState.IMPORT_TAB) {
            luaChonNoiThatScene.getLuaChonNoiThatController().importFile(importDirectory);
        }
        else if (tabState == TabState.BLANK_TAB) {
            luaChonNoiThatScene.getLuaChonNoiThatController().importData(
                    new DataPackage(persistenceStorageService.getThongTinCongTy(), null,
                            persistenceStorageService.getNoteArea(), null, null
                    )
            );
        }
        addNewTabToPane(newTab);
        TabContent tabContent = new TabContent(newTab, luaChonNoiThatScene, luaChonNoiThatScene.getLuaChonNoiThatController());
        currentlyOpenTab.add(tabContent);
        return tabContent;
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
            currentlyOpenTab.removeIf(tabContent -> tabContent.getTab().equals(newTab));
        });
        return newTab;
    }

    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
    }

    private void duplicateTab(ActionEvent action, Tab currentTab) {
        TabContent currentTabContent = currentlyOpenTab.stream().filter(tabContent -> tabContent.getTab().equals(currentTab)).findFirst().orElse(null);
        if (currentTabContent == null) {
            throw new RuntimeException("Tab not found");
        }
        TabContent newTab = createNewTab(TabState.DUPLICATE_TAB, null);
        duplicateContent(currentTabContent, newTab);
    }

    private void duplicateContent(TabContent CurrentTab, TabContent dupTab) {
        DataPackage sourceDataPackage = CurrentTab.getLuaChonNoiThatController().exportData();
        dupTab.getLuaChonNoiThatController().importData(sourceDataPackage);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getTabs().clear();
        tabPane.getTabs().add(newTabButton());
    }
    private Tab newTabButton() {
        Tab addTab = new Tab();
        Button newTabButton = new Button("+");
        newTabButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        newTabButton.setOnAction(event -> createNewTab());
        addTab.setClosable(false);
        addTab.setGraphic(newTabButton);
        return addTab;
    }
    public void init(TabState tabState, String importDirectory, Stage currentStage) {
        this.currentStage = currentStage;
        createNewTab(tabState, importDirectory);
    }
}
