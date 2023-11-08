package com.huy.appnoithat.Controller.NewTab;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.State;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Operation.ExportOperation;
import com.huy.appnoithat.DataModel.DataPackage;
import com.huy.appnoithat.DataModel.SaveFile.TabData;
import com.huy.appnoithat.Enums.FileType;
import com.huy.appnoithat.Scene.LuaChonNoiThat.LuaChonNoiThatScene;
import com.huy.appnoithat.Service.LuaChonNoiThat.NoiThatFileService;
import com.huy.appnoithat.Service.PersistenceStorage.PersistenceStorageService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class NewTabController implements Initializable {
    final static Logger LOGGER = LogManager.getLogger(NewTabController.class);
    @FXML
    private TabPane tabPane;
    private final PersistenceStorageService persistenceStorageService;
    private final NoiThatFileService noiThatFileService;
    private Stage currentStage;
    private final List<TabContent> currentlyOpenTab;
    @FXML
    private MenuItem MenuItemExportPDF, MenuItemExportXLS, MenuItemSave, MenuItemSaveAs, MenuItemSaveCompanyInfo, MenuItemSaveNoteArea;
    @FXML
    private CheckMenuItem AutoSave;
    private Timeline autoSaveTimer;
    private State currentState;
    String currentDirectory;
    public NewTabController() {
        persistenceStorageService = PersistenceStorageService.getInstance();
        noiThatFileService = new NoiThatFileService();
        currentlyOpenTab = new ArrayList<>();
    }
    @FXML
    void onClickMenuItem(ActionEvent event) {
        Object source = event.getSource();
        if (source == MenuItemExportPDF) {
            exportFile(FileType.PDF);
        }
        else if (source == MenuItemExportXLS) {
            exportFile(FileType.EXCEL);
        }
        else if (source == MenuItemSave) {
            save();
        }
        else if (source == AutoSave) {
            if (AutoSave.isSelected()) {
                LOGGER.info("Auto save is enabled");
                startAutoSaveAction();
            } else {
                LOGGER.info("Auto save is disabled");
                stopAutoSaveAction();
            }
        }
        else if (source == MenuItemSaveCompanyInfo) {
            saveThongTinCongTy();
        }
        else if (source == MenuItemSaveNoteArea) {
            saveNoteArea();
        }
    }

    private void saveNoteArea() {
        TabContent selectedTabContent = getSelectedTabContent();
        if (selectedTabContent == null) {
            return;
        }
        selectedTabContent.getLuaChonNoiThatController().saveNoteArea();
    }

    private void saveThongTinCongTy() {
        TabContent selectedTabContent = getSelectedTabContent();
        if (selectedTabContent == null) {
            return;
        }
        selectedTabContent.getLuaChonNoiThatController().saveThongTinCongTy();
    }

    /**
     * Saves the data to a file with the FileType.NT in the current directory.
     */
    public void save() {
        // Create an instance of ExportOperation, passing the current object (this) to it
        if (currentState == State.NEW_FILE) {
            String directory = saveAs();
            if (directory == null) {
                backup();
                return;
            }
            currentState = State.OPEN_FROM_EXISTING_FILE;
            currentDirectory = directory;
            startAutoSaveAction();
        } else if (currentState == State.OPEN_FROM_EXISTING_FILE) {
            saveToExistingFile(currentDirectory);
            PopupUtils.throwSuccessSignal("Lưu thành công");
        }
    }
    public void backup() {
        String filename = "backup-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date()) + ".nt";
        String tempDirectory = Paths.get(Config.FILE_EXPORT.TEMP_NT_FILE_DIRECTORY, filename).toString();
        saveToExistingFile(tempDirectory);
    }
    public void startAutoSaveAction() {
        autoSaveTimer.setCycleCount(Timeline.INDEFINITE);
        autoSaveTimer.play();
    }
    public void stopAutoSaveAction() {
        autoSaveTimer.stop();
    }
    private String saveAs() {
        File selectedFile = PopupUtils.fileSaver();
        if (selectedFile == null) {
            return null;
        }
        saveToExistingFile(selectedFile.getAbsolutePath());
        PopupUtils.throwSuccessSignal("Lưu thành công");
        return selectedFile.getAbsolutePath();
    }
    private void saveToExistingFile(String fileDirectory) {
        noiThatFileService.export(exportData(), new File(fileDirectory));
    }
    private List<TabData> exportData() {
        List<TabData> tabDataList = new ArrayList<>();
        for(TabContent tabContent : currentlyOpenTab) {
            DataPackage dataPackage = tabContent.getLuaChonNoiThatController().exportData();
            if (dataPackage == null) {
                continue;
            }
            String tabName =  tabContent.getTab().getText();
            tabDataList.add(new TabData(dataPackage, tabName));
        }
        return tabDataList;
    }

    private void exportFile(FileType fileType) {
        TabContent selectedTabContent = getSelectedTabContent();
        if (selectedTabContent == null) {
            return;
        }
        selectedTabContent.getLuaChonNoiThatController().exportFile(fileType);
    }
    private LuaChonNoiThatScene createNoiThatScene() {
        LuaChonNoiThatScene luaChonNoiThatScene = new LuaChonNoiThatScene();
        luaChonNoiThatScene.getLuaChonNoiThatController().init(currentStage);
        return luaChonNoiThatScene;
    }
    private void createNewTab(TabState tabState, String importDirectory) {
        if (tabState == TabState.IMPORT_TAB) {
            currentState = State.OPEN_FROM_EXISTING_FILE;
            currentDirectory = importDirectory;
            importFromFile(importDirectory);
        }
        else if (tabState == TabState.BLANK_TAB) {
            currentState = State.NEW_FILE;
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
        newTab.setContent(luaChonNoiThatScene.getRoot());
        luaChonNoiThatScene.getLuaChonNoiThatController().importData(content);
        addNewTabToPane(newTab);
        TabContent tabContent = new TabContent(newTab, luaChonNoiThatScene, luaChonNoiThatScene.getLuaChonNoiThatController());
        currentlyOpenTab.add(tabContent);
    }
    private void createBlankTab() {
        createTab("Tab mới", new DataPackage(persistenceStorageService.getThongTinCongTy(), null,
                persistenceStorageService.getNoteArea(), null, null));
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
        TabContent currentTabContent = currentlyOpenTab.stream().filter(
                tabContent -> tabContent.getTab().equals(currentTab)).findFirst().orElse(null);
        if (currentTabContent == null) {
            throw new RuntimeException("Tab not found");
        }
        createTab(currentTab.getText(), currentTabContent.getLuaChonNoiThatController().exportData());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getTabs().clear();
        tabPane.getTabs().add(newTabButton());
        currentState = State.NEW_FILE;
        AutoSave.setSelected(true);
        autoSaveTimer = new Timeline(new KeyFrame(Duration.minutes(10), event -> {
            if (currentState == State.OPEN_FROM_EXISTING_FILE && currentDirectory != null) {
                save();
            }
            else if (currentState == State.NEW_FILE) {
                backup();
            }
        }));
        startAutoSaveAction();
    }
    private Tab newTabButton() {
        Tab addTab = new Tab();
        Button newTabButton = new Button("+");
        newTabButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        newTabButton.setOnAction(event -> {
            if (tabPane.getSelectionModel().getSelectedItem() != null) {
                createNewTab(TabState.BLANK_TAB, null);
            }
        });
        addTab.setClosable(false);
        addTab.setGraphic(newTabButton);
        return addTab;
    }
    public void init(TabState tabState, String importDirectory, Stage currentStage) {
        this.currentStage = currentStage;
        AutoSave.setSelected(true);
        createNewTab(tabState, importDirectory);
    }
    private TabContent getSelectedTabContent() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab == null) {
            return null;
        }
        return currentlyOpenTab.stream().filter(tabContent -> tabContent.getTab().equals(selectedTab)).findFirst().orElse(null);
    }
}
