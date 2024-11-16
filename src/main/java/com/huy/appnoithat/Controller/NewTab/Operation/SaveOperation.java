package com.huy.appnoithat.Controller.NewTab.Operation;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.State;
import com.huy.appnoithat.Controller.NewTab.NewTabController;
import com.huy.appnoithat.Controller.NewTab.TabContent;
import com.huy.appnoithat.Controller.NewTab.TabUtils;
import com.huy.appnoithat.DataModel.DataPackage;
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
import java.util.ArrayList;
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

    private List<TabData> saveData() {
        List<TabData> tabDataList = new ArrayList<>();
        List<TabContent> tabContentList = TabUtils.resortTab(newTabController.getCurrentlyOpenTab(), newTabController.getTabPane());
        for (TabContent tabContent : tabContentList) {
            DataPackage dataPackage = tabContent.getLuaChonNoiThatController().saveData();
            if (dataPackage == null) {
                continue;
            }
            String tabName = tabContent.getTab().getText();
            tabDataList.add(new TabData(dataPackage, tabName));
        }
        return tabDataList;
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

    public boolean save() {
        // Create an instance of ExportOperation, passing the current object (this) to it
        if (newTabController.getCurrentState() == State.NEW_FILE) {
            Optional<String> result = saveAs();
            if (result.isPresent()) {
                newTabController.setCurrentState(State.OPEN_FROM_EXISTING_FILE);
                newTabController.setCurrentDirectory(result.get());
                return true;
            }
            return false;
        } else if (newTabController.getCurrentState() == State.OPEN_FROM_EXISTING_FILE) {
            List<TabData> contents = saveData();
            doSaveAction(contents, newTabController.getCurrentDirectory());
            return true;
        }
        return false;
    }

    public Optional<String> saveAs() {
        File selectedFile = PopupUtils.fileSaver(FileType.NT);
        if (selectedFile == null) {
            return Optional.empty();
        }
        List<TabData> contents = saveData();
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
