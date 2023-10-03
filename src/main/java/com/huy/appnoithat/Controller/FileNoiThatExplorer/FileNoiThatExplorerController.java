package com.huy.appnoithat.Controller.FileNoiThatExplorer;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.NewTab.TabState;
import com.huy.appnoithat.HelloApplication;
import com.huy.appnoithat.Scene.LuaChonNoiThat.NewTabScene;
import com.huy.appnoithat.Service.FileNoiThatExplorer.FileNoiThatExplorerService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.Date;

public class FileNoiThatExplorerController {
    @FXML
    private TableColumn<RecentFile, String> DirectoryCollum;

    @FXML
    private TableView<RecentFile> RecentTableView;

    @FXML
    private TableColumn<RecentFile, String> TimeStampCollum;
    private final FileNoiThatExplorerService fileNoiThatExplorerService;
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
            return;
        }
        fileNoiThatExplorerService.addRecentFile(recentFile);
    }
    private void openNewLuaChonNoiThatTab(TabState tabState, String directory) {
        Stage newStage = new Stage();
        NewTabScene newTabScene = new NewTabScene();
        newTabScene.getNewTabController().init(tabState, directory);
        newStage.setMaximized(true);
        newStage.setScene(newTabScene.getScene());
        newStage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("/com/huy/appnoithat/Scene/icons/logoapp.jpg")));
        newStage.show();
    }
    private boolean isDirectoryExist(String directory) {
        return new File(directory).exists();
    }
}
