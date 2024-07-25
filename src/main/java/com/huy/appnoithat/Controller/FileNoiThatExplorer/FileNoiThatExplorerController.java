package com.huy.appnoithat.Controller.FileNoiThatExplorer;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.NewTab.TabState;
import com.huy.appnoithat.DataModel.RecentFile;
import com.huy.appnoithat.Scene.LuaChonNoiThat.NewTabScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Service.FileNoiThatExplorer.FileNoiThatExplorerService;
import com.huy.appnoithat.Work.OpenFileWork;
import com.huy.appnoithat.Work.WorkFactory;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileNoiThatExplorerController {
    final static Logger LOGGER = LogManager.getLogger(FileNoiThatExplorerController.class);
    @FXML
    private TableColumn<RecentFile, String> DirectoryCollum;
    @FXML
    private TableView<RecentFile> RecentTableView;
    @FXML
    private TableColumn<RecentFile, String> TimeStampCollum;
    @FXML
    private TableColumn<RecentFile, String> ActionCollum;
    private final FileNoiThatExplorerService fileNoiThatExplorerService;
    @Setter
    private Parent root;
    public FileNoiThatExplorerController () {
        fileNoiThatExplorerService = FileNoiThatExplorerService.getInstance();
    }

    /**
     * Handles the action when the new file button is clicked.
     * Opens a new Lua Chon Noi That tab with a blank state.
     * @param event The event triggered by the new file button.
     */
    @FXML
    void newFileButton(ActionEvent event) {
        openNewLuaChonNoiThatTab(TabState.BLANK_TAB, null);
    }


    /**
     * Handles the action when the open file button is clicked.
     * Opens a file dialog for the user to select a file to open.
     * Adds the selected file to the recent files list and opens it.
     * @param event The event triggered by the open file button.
     */
    @FXML
    void openFileButton(ActionEvent event) {
        File selectedFile = PopupUtils.fileOpener();
        if (selectedFile != null) {
            RecentFile recentFile = new RecentFile(selectedFile.getAbsolutePath(), System.currentTimeMillis());
            openFile(recentFile);
        }
    }


    /**
     * Initializes the Recent Files tab by setting up the table columns and populating the table with recent files.
     */
    public void init() {
        DirectoryCollum.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDirectory()));
        TimeStampCollum.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
                Utils.convertMilisToDateTimeString(cellData.getValue().getTimeStamp())));
        ActionCollum.setCellFactory(tableColumn -> new ActionCell(this::deleteFile));
        ActionCollum.setCellValueFactory(new PropertyValueFactory<>(""));
        TimeStampCollum.setComparator((o1, o2) -> {
            long o1Time = Utils.convertDateTimeStringToMilis(o1);
            long o2Time = Utils.convertDateTimeStringToMilis(o2);
            return Long.compare(o2Time, o1Time);
        });
        RecentTableView.widthProperty().addListener((observableValue, number, t1) -> {
            DoubleBinding usedWidth = ActionCollum.widthProperty()
                    .add(TimeStampCollum.widthProperty());
            double width = t1.doubleValue() - usedWidth.doubleValue() - 5;
            DoubleProperty observableDouble = new SimpleDoubleProperty(width);
            DirectoryCollum.prefWidthProperty().bind(observableDouble);
        });

        RecentTableView.setItems(fileNoiThatExplorerService.getRecentFile());

        // Double click to open a recent file
        RecentTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (RecentTableView.getSelectionModel().getSelectedItem() == null) {
                    return;
                }
                openFile(RecentTableView.getSelectionModel().getSelectedItem());
            }
        });
        getWork();
    }
    private void deleteFile(int index) {
        fileNoiThatExplorerService.removeRecentFile(RecentTableView.getItems().get(index));
    }
    private void getWork() {
        new Thread(() -> {
            while (true) {
                try {
                    OpenFileWork openFileWork = WorkFactory.getWork();
                    if (openFileWork != null) {
                        Platform.runLater(() -> {
                            LOGGER.info("Open work: " + openFileWork.getParam());
                            openWith(openFileWork.getParam());
                        });
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    /**
     * Opens the specified recent file in a new tab.
     *
     * @param recentFile The recent file to be opened.
     */
    public void openFile(RecentFile recentFile) {
        if (!isDirectoryExist(recentFile.getDirectory())) {
            PopupUtils.throwErrorNotification("File không tồn tại");
            // Remove from recent file
            fileNoiThatExplorerService.removeRecentFile(recentFile);
            return;
        }
        try {
            openNewLuaChonNoiThatTab(TabState.IMPORT_TAB, recentFile.getDirectory());
        } catch (Exception e) {
            PopupUtils.throwErrorNotification("File không hợp lệ");
            LOGGER.error(e.getMessage());
            return;
        }
        fileNoiThatExplorerService.addRecentFile(recentFile);
    }

    /**
     * Opens a new Lua Chon Noi That tab with the specified tab state and directory.
     *
     * @param tabState   The state of the tab (e.g., BLANK_TAB, IMPORT_TAB).
     * @param directory  The directory associated with the tab.
     */
    private void openNewLuaChonNoiThatTab(TabState tabState, String directory) {
        NewTabScene newTabScene = new NewTabScene();
        Stage currentStage = StageFactory.CreateNewMaximizedStage(newTabScene.getScene(), true);
        newTabScene.getNewTabController().init(tabState, directory, currentStage);
    }

    /**
     * Checks if the specified directory exists.
     *
     * @param directory The directory to be checked.
     * @return True if the directory exists, false otherwise.
     */
    private boolean isDirectoryExist(String directory) {
        return new File(directory).exists();
    }
    public void openWith(String path) {
        RecentFile recentFile = new RecentFile(path, System.currentTimeMillis());
        openFile(recentFile);
    }
}
