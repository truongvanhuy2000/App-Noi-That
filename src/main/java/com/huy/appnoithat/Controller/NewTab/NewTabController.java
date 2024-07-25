package com.huy.appnoithat.Controller.NewTab;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.State;
import com.huy.appnoithat.Controller.NewTab.Operation.ContentOperation;
import com.huy.appnoithat.Controller.NewTab.Operation.TabOperation;
import com.huy.appnoithat.DataModel.Enums.Action;
import com.huy.appnoithat.DataModel.Enums.FileType;
import com.huy.appnoithat.Service.LuaChonNoiThat.NoiThatFileService;
import com.huy.appnoithat.Service.PersistenceStorage.PersistenceStorageService;
import com.huy.appnoithat.Service.PersistenceStorage.StorageService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
@Data
public class NewTabController implements Initializable {
    final static Logger LOGGER = LogManager.getLogger(NewTabController.class);
    private final StorageService persistenceStorageService;
    private final NoiThatFileService noiThatFileService;
    private Stage currentStage;
    private final List<TabContent> currentlyOpenTab;
    @FXML
    private MenuItem MenuItemExportPDF, MenuItemExportXLS, MenuItemSave, MenuItemSaveAs,
            MenuItemSaveCompanyInfo, MenuItemSaveNoteArea, MenuItemExportMultipleXLS;
    @FXML
    private CheckMenuItem AutoSave;
    @FXML
    private TabPane tabPane;
    @FXML
    private StackPane loadingPane;

    private Timeline autoSaveTimer;
    private State currentState;
    private String currentDirectory;
    private TabOperation tabOperation;
    private ContentOperation contentOperation;
    public NewTabController() {
        persistenceStorageService = new PersistenceStorageService();
        noiThatFileService = new NoiThatFileService();
        currentlyOpenTab = new ArrayList<>();
    }
    @FXML
    void onClickMenuItem(ActionEvent event) {
        Object source = event.getSource();
        if (source == MenuItemExportPDF) {
            contentOperation.exportFile(FileType.PDF);
        }
        else if (source == MenuItemExportXLS) {
            contentOperation.exportFile(FileType.EXCEL);
        }
        else if (source == MenuItemExportMultipleXLS) {
            contentOperation.exportMultipleExcel();
        }
        else if (source == MenuItemSave) {
            contentOperation.save();
        }
        else if (source == MenuItemSaveAs) {
            contentOperation.saveAs();
        }
        else if (source == AutoSave) {
            if (AutoSave.isSelected()) {
                LOGGER.info("Auto save is enabled");
                contentOperation.startAutoSaveAction();
            } else {
                LOGGER.info("Auto save is disabled");
                contentOperation.stopAutoSaveAction();
            }
        }
        else if (source == MenuItemSaveCompanyInfo) {
            contentOperation.saveThongTinCongTy();
        }
        else if (source == MenuItemSaveNoteArea) {
            contentOperation.saveNoteArea();
        }
    }

    @FXML
    private void sceneSwitcher(ActionEvent actionEvent) {
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        if (KeyboardUtils.isRightKeyCombo(Action.SAVE, event)) {
            LOGGER.info("Save key combo pressed");
            contentOperation.save();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadingPane.setDisable(true);
        loadingPane.setVisible(false);

        tabOperation = new TabOperation(this);
        contentOperation = new ContentOperation(this);
        tabPane.getTabs().clear();
        Tab newTabButton = newTabButton();
        tabPane.getTabs().add(newTabButton);
        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        tabPane.getTabs().addListener((ListChangeListener.Change<? extends Tab> change) ->
                handleMovingNewTabButton(change, newTabButton));
        currentState = State.NEW_FILE;
        AutoSave.setSelected(true);
        autoSaveTimer = new Timeline(new KeyFrame(Duration.minutes(10), event -> {
            if (currentState == State.OPEN_FROM_EXISTING_FILE && currentDirectory != null) {
                contentOperation.save();
            }
            else if (currentState == State.NEW_FILE) {
                contentOperation.backup();
            }
        }));
        contentOperation.startAutoSaveAction();
    }

    /**
     * @param change
     * @param newTabButton
     * This method will handle the situation when user trying to move the add new tab button because it a tab :))
     */
    private void handleMovingNewTabButton(ListChangeListener.Change<? extends Tab> change, Tab newTabButton) {
        {
            int indexOfNewTab = tabPane.getTabs().indexOf(newTabButton);
            if (indexOfNewTab > 0 && tabPane.getTabs().size() >= 2) {
                ObservableList<Tab> observableList = FXCollections.observableArrayList(tabPane.getTabs());
                Collections.swap(observableList, indexOfNewTab, 0);
                tabPane.getTabs().clear();
                tabPane.getTabs().addAll(observableList);
                tabPane.getSelectionModel().select(1);
            }
        }
    }

    public void init(TabState tabState, String importDirectory, Stage currentStage) {
        this.currentStage = currentStage;
        AutoSave.setSelected(true);
        tabOperation.createNewTab(tabState, importDirectory);
    }
    private Tab newTabButton() {
        Tab addTab = new Tab();
        Button newTabButton = new Button("+");
        newTabButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        newTabButton.setOnAction(event -> {
            if (tabPane.getSelectionModel().getSelectedItem() != null) {
                tabOperation.createNewTab(TabState.BLANK_TAB, null);
            }
        });
        addTab.setClosable(false);
        addTab.setGraphic(newTabButton);

        return addTab;
    }
    public TabContent getSelectedTabContent() {
        Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
        if (selectedTab == null) {
            return null;
        }
        return currentlyOpenTab.stream().filter(tabContent -> tabContent.getTab().equals(selectedTab)).findFirst().orElse(null);
    }
}
