package com.huy.appnoithat.Controller.FileNoiThatExplorer;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.NewTab.TabState;
import com.huy.appnoithat.HelloApplication;
import com.huy.appnoithat.Scene.LuaChonNoiThat.NewTabScene;
import com.huy.appnoithat.Scene.StageFactory;
import com.huy.appnoithat.Service.FileNoiThatExplorer.FileNoiThatExplorerService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
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
    private final FileNoiThatExplorerService fileNoiThatExplorerService;
    @Setter
    private Parent root;
    public FileNoiThatExplorerController () {
        fileNoiThatExplorerService = FileNoiThatExplorerService.getInstance();
    }
    @FXML
    void newFileButton(ActionEvent event) {
        openNewLuaChonNoiThatTab(TabState.BLANK_TAB, null);
    }
    @FXML
    void openFileButton(ActionEvent event) {
        File selectedFile = PopupUtils.fileOpener();
        if (selectedFile == null) {
            return;
        }
        RecentFile recentFile = new RecentFile(selectedFile.getAbsolutePath(), System.currentTimeMillis());
        // Add to recent file
        openFile(recentFile);
    }
    public void init() {
        DirectoryCollum.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDirectory()));
        TimeStampCollum.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
                Utils.convertMilisToDateTimeString(cellData.getValue().getTimeStamp())));
        RecentTableView.setItems(fileNoiThatExplorerService.getRecentFile());
        RecentTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (RecentTableView.getSelectionModel().getSelectedItem() == null) {
                    return;
                }
                openFile(RecentTableView.getSelectionModel().getSelectedItem());
            }
        });
    }
    public void openFile(RecentFile recentFile) {
        if (!isDirectoryExist(recentFile.getDirectory())) {
            PopupUtils.throwErrorSignal("File không tồn tại");
            // Remove from recent file
            fileNoiThatExplorerService.removeRecentFile(recentFile);
            return;
        }
        try {
            openNewLuaChonNoiThatTab(TabState.IMPORT_TAB, recentFile.getDirectory());
        } catch (Exception e) {
            PopupUtils.throwErrorSignal("File không hợp lệ");
            LOGGER.error(e.getMessage());
            return;
        }
        fileNoiThatExplorerService.addRecentFile(recentFile);
    }
    private void openNewLuaChonNoiThatTab(TabState tabState, String directory) {
        NewTabScene newTabScene = new NewTabScene();
        Stage currentStage = StageFactory.CreateNewMaximizedStage(newTabScene.getScene());
        newTabScene.getNewTabController().init(tabState, directory, currentStage);
    }
    private boolean isDirectoryExist(String directory) {
        return new File(directory).exists();
    }
}
