package com.huy.appnoithat.Controller.NewTab.Operation;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.State;
import com.huy.appnoithat.Controller.NewTab.NewTabController;
import com.huy.appnoithat.Controller.NewTab.TabContent;
import com.huy.appnoithat.DataModel.Enums.FileType;
import com.huy.appnoithat.DataModel.SaveFile.TabData;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import com.huy.appnoithat.Service.LuaChonNoiThat.NoiThatFileService;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SaveOperation {
    private final Logger LOGGER = LogManager.getLogger(this);
    private final NewTabController newTabController;
    private final NoiThatFileService noiThatFileService;

    public SaveOperation(NewTabController newTabController) {
        this.newTabController = newTabController;
        this.noiThatFileService = newTabController.getNoiThatFileService();
    }

    public void saveNoteArea() {
        TabContent selectedTabContent = newTabController.getSelectedTabContent();
        if (selectedTabContent == null) {
            LOGGER.error("No tab was selected");
            return;
        }
        selectedTabContent.getLuaChonNoiThatController().saveNoteArea();
    }

    public void saveThongTinCongTy() {
        TabContent selectedTabContent = newTabController.getSelectedTabContent();
        if (selectedTabContent == null) {
            LOGGER.error("No tab was selected");
            return;
        }
        selectedTabContent.getLuaChonNoiThatController().saveThongTinCongTy();
    }

    public void save(List<TabData> contents) {
        // Create an instance of ExportOperation, passing the current object (this) to it
        if (newTabController.getCurrentState() == State.NEW_FILE) {
            saveAs(contents).ifPresent((savedDirectory -> {
                newTabController.setCurrentState(State.OPEN_FROM_EXISTING_FILE);
                newTabController.setCurrentDirectory(savedDirectory);
            }));
        } else if (newTabController.getCurrentState() == State.OPEN_FROM_EXISTING_FILE) {
            doSaveAction(contents, newTabController.getCurrentDirectory());
        }
    }

    public void backup(List<TabData> contents) {
        String filename = "backup-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date()) + ".nt";
        String tempDirectory = Paths.get(Config.FILE_EXPORT.TEMP_NT_FILE_DIRECTORY, filename).toString();
        noiThatFileService.export(contents, new File(tempDirectory), false);
    }

    public Optional<String> saveAs(List<TabData> contents) {
        File selectedFile;
        selectedFile = PopupUtils.fileSaver(FileType.NT);
        if (selectedFile == null) {
            backup(contents);
            return Optional.empty();
        }
        doSaveAction(contents, selectedFile.getAbsolutePath());
        return Optional.of(selectedFile.getAbsolutePath());
    }

    private void doSaveAction(List<TabData> contents, String fileDirectory) {
        if (fileDirectory == null) {
            return;
        }
        PopupUtils.throwSuccessNotification("Lưu thành công");
        noiThatFileService.export(contents, new File(fileDirectory), true);
    }
}
