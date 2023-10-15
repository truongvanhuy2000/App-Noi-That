package com.huy.appnoithat.Service.LuaChonNoiThat;

import com.huy.appnoithat.Controller.FileNoiThatExplorer.RecentFile;
import com.huy.appnoithat.DataModel.DataPackage;
import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Entity.NoiThat;
import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Enums.FileType;
import com.huy.appnoithat.Service.FileExport.ExportFile;
import com.huy.appnoithat.Service.FileExport.FileExportService;
import com.huy.appnoithat.Service.FileNoiThatExplorer.FileNoiThatExplorerService;
import com.huy.appnoithat.Service.RestService.HangMucRestService;
import com.huy.appnoithat.Service.RestService.NoiThatRestService;
import com.huy.appnoithat.Service.RestService.PhongCachRestService;
import com.huy.appnoithat.Service.RestService.VatLieuRestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LuaChonNoiThatService {
    final static Logger LOGGER = LogManager.getLogger(LuaChonNoiThatService.class);
    private final FileExportService fileExportService;
    private final PhongCachRestService phongCachRestService;
    private final NoiThatRestService noiThatRestService;
    private final HangMucRestService hangMucRestService;
    private final VatLieuRestService vatLieuRestService;
    private final FileNoiThatExplorerService fileNoiThatExplorerService;
    public LuaChonNoiThatService() {
        fileExportService = new FileExportService();
        phongCachRestService = PhongCachRestService.getInstance();
        noiThatRestService = NoiThatRestService.getInstance();
        hangMucRestService = HangMucRestService.getInstance();
        vatLieuRestService = VatLieuRestService.getInstance();
        fileNoiThatExplorerService = FileNoiThatExplorerService.getInstance();
    }

    public List<PhongCachNoiThat> findAllPhongCachNoiThat() {
        return phongCachRestService.findAll();
    }

    public List<NoiThat> findNoiThatListBy(String phongCach) {
        phongCach = URLEncoder.encode(phongCach, StandardCharsets.UTF_8);
        return noiThatRestService.searchBy(phongCach);
    }

    public List<HangMuc> findHangMucListBy(String phongCach, String noiThat) {
        phongCach = URLEncoder.encode(phongCach, StandardCharsets.UTF_8);
        noiThat = URLEncoder.encode(noiThat, StandardCharsets.UTF_8);
        return hangMucRestService.searchBy(phongCach, noiThat);
    }

    public List<VatLieu> findVatLieuListBy(String phongCach, String noiThat, String hangMuc) {
        phongCach = URLEncoder.encode(phongCach, StandardCharsets.UTF_8);
        noiThat = URLEncoder.encode(noiThat, StandardCharsets.UTF_8);
        hangMuc = URLEncoder.encode(hangMuc, StandardCharsets.UTF_8);
        return vatLieuRestService.searchBy(phongCach, noiThat, hangMuc);
    }

    public boolean exportFile(File selectedFile, FileType fileType, DataPackage dataPackage) {
        ExportFile exportFile = fileExportService.getExportService(selectedFile, fileType);
        exportFile.setUpDataForExport(dataPackage);
        try {
            exportFile.export(selectedFile);
        } catch (IOException e) {
            LOGGER.error("Some thing is wrong with the export operation", e);
            return false;
        }
        if (fileType == FileType.NT) {
            fileNoiThatExplorerService.addRecentFile(new RecentFile(selectedFile.getAbsolutePath(), System.currentTimeMillis()));
        }
        return true;
    }

    public DataPackage importFile(File selectedFile) {
        ExportFile exportFile = fileExportService.getExportService(selectedFile, FileType.NT);
        return exportFile.importData(selectedFile);
    }

}
