package com.huy.appnoithat.Controller.NewTab.Operation;

import com.huy.appnoithat.Common.FXUtils;
import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Configuration.Config;
import com.huy.appnoithat.Controller.NewTab.NewTabController;
import com.huy.appnoithat.Controller.NewTab.TabContent;
import com.huy.appnoithat.Controller.NewTab.TabUtils;
import com.huy.appnoithat.DataModel.DataPackage;
import com.huy.appnoithat.DataModel.Enums.FileType;
import com.huy.appnoithat.DataModel.SaveFile.TabData;
import com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.Operation.Excel.ExportMultipleXLS;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import com.huy.appnoithat.Service.LuaChonNoiThat.NoiThatFileService;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class ExportOperation {
    private final Logger LOGGER = LogManager.getLogger(this);
    private final NewTabController newTabController;
    private final NoiThatFileService noiThatFileService;
    private final LuaChonNoiThatService luaChonNoiThatService;
    private final StackPane loadingPane;

    public ExportOperation(NewTabController newTabController) {
        this.newTabController = newTabController;
        this.noiThatFileService = newTabController.getNoiThatFileService();
        this.loadingPane = newTabController.getLoadingPane();
        this.luaChonNoiThatService = newTabController.getLuaChonNoiThatService();
    }

    private List<TabData> exportData() {
        List<TabData> tabDataList = new ArrayList<>();
        List<TabContent> tabContentList = TabUtils.resortTab(newTabController.getCurrentlyOpenTab(), newTabController.getTabPane());
        for (TabContent tabContent : tabContentList) {
            DataPackage dataPackage = tabContent.getLuaChonNoiThatController().exportData();
            if (dataPackage == null) {
                continue;
            }
            String tabName = tabContent.getTab().getText();
            tabDataList.add(new TabData(dataPackage, tabName));
        }
        return tabDataList;
    }

    public String backup() {
        List<TabData> contents = exportData();
        String filename = "backup-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(new Date()) + ".nt";
        String tempDirectory = Paths.get(Config.FILE_EXPORT.TEMP_NT_FILE_DIRECTORY, filename).toString();
        noiThatFileService.export(contents, new File(tempDirectory), false);
        return filename;
    }

    public void exportFile(FileType fileType) {
        TabContent selectedTabContent = newTabController.getSelectedTabContent();
        if (selectedTabContent == null) {
            return;
        }
        File selectedFile = PopupUtils.fileSaver(fileType);
        if (selectedFile == null) {
            return;
        }
        DataPackage dataPackage = selectedTabContent.getLuaChonNoiThatController().exportData();
        showLoading();
        new Thread(() -> {
            boolean result = luaChonNoiThatService.exportFile(selectedFile, fileType, dataPackage);
            hideLoading(result, selectedFile);
        }).start();
    }

    public void exportMultipleExcel() {
        List<TabData> exportDataList = exportData();
        File selectedFile = PopupUtils.fileSaver(FileType.EXCEL);
        if (selectedFile == null) {
            return;
        }
        showLoading();
        new Thread(() -> {
            ExportMultipleXLS exportMultipleXLS = new ExportMultipleXLS();
            exportMultipleXLS.setUpDataForExport(exportDataList);
            boolean result = exportMultipleXLS.export(selectedFile);
            hideLoading(result, selectedFile);
        }).start();
    }

    private void showLoading() {
        FXUtils.showLoading(loadingPane, "Đang xuất file...");
    }

    private void hideLoading(Boolean result, File outputFile) {
        Platform.runLater(() -> {
            FXUtils.hideLoading(loadingPane);
            showResult(result, outputFile);
        });
    }

    private void showResult(Boolean result, File outputFile) {
        if (!result) {
            PopupUtils.throwErrorNotification("Xuất file thất bại");
        } else {
            PopupUtils.throwSuccessNotification("Xuất file thành công. Nhấn để mở", () -> {
                if (!outputFile.exists()) {
                    return;
                }
                new Thread(() -> {
                    LOGGER.info("Opening file: " + outputFile.getAbsolutePath());
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.open(outputFile);
                    } catch (IOException e) {
                        PopupUtils.throwErrorNotification("Không có phần mềm hỗ trợ mở loại tệp này");
                        throw new RuntimeException(e);
                    }
                }).start();
            });
        }
    }
}
