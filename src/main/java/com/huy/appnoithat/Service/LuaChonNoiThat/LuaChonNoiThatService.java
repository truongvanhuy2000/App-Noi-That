package com.huy.appnoithat.Service.LuaChonNoiThat;

import com.huy.appnoithat.DataModel.DataPackage;
import com.huy.appnoithat.DataModel.Entity.HangMuc;
import com.huy.appnoithat.DataModel.Entity.NoiThat;
import com.huy.appnoithat.DataModel.Entity.PhongCachNoiThat;
import com.huy.appnoithat.DataModel.Entity.VatLieu;
import com.huy.appnoithat.DataModel.Enums.FileType;
import com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.ExportFile;
import com.huy.appnoithat.Service.LuaChonNoiThat.FileExport.FileExportService;
import com.huy.appnoithat.Service.RestService.HangMucRestService;
import com.huy.appnoithat.Service.RestService.NoiThatRestService;
import com.huy.appnoithat.Service.RestService.PhongCachRestService;
import com.huy.appnoithat.Service.RestService.VatLieuRestService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class LuaChonNoiThatService {
    final static Logger LOGGER = LogManager.getLogger(LuaChonNoiThatService.class);
    private final FileExportService fileExportService;
    private final PhongCachRestService phongCachRestService;
    private final NoiThatRestService noiThatRestService;
    private final HangMucRestService hangMucRestService;
    private final VatLieuRestService vatLieuRestService;
    private final CacheService cacheService;

    /**
     * Retrieves a list of all PhongCachNoiThat from the service.
     *
     * @return List of PhongCachNoiThat objects.
     */

    public List<PhongCachNoiThat> findAllPhongCachNoiThat() {
        String requestId = cacheService.createUniqueId("findAllPhongCachNoiThat");
        if (cacheService.isContain(requestId)) {
            return cacheService.readCache(requestId, PhongCachNoiThat.class).orElse(new ArrayList<>());
        }
        List<PhongCachNoiThat> phongCachNoiThatList = phongCachRestService.findAll();
        cacheService.writeCache(phongCachNoiThatList, requestId);
        return phongCachNoiThatList;
    }

    /**
     * Retrieves a list of NoiThat objects based on the provided PhongCach.
     *
     * @param phongCach The PhongCach for which to find NoiThat objects.
     * @return List of NoiThat objects matching the provided PhongCach.
     */
    public List<NoiThat> findNoiThatListBy(String phongCach) {
        // Encoding the PhongCach to ensure URL safety.
        String requestId = cacheService.createUniqueId("findNoiThatListBy", phongCach);
        if (cacheService.isContain(requestId)) {
            return cacheService.readCache(requestId, NoiThat.class).orElse(new ArrayList<>());
        }

        List<NoiThat> noiThatList = noiThatRestService.searchBy(phongCach);
        cacheService.writeCache(noiThatList, requestId);
        return noiThatList;
    }

    public List<NoiThat> findNoiThatListByPhongCachID(int phongCachID) {
        // Encoding the PhongCach to ensure URL safety.
        String requestId = cacheService.createUniqueId("findNoiThatListByPhongCachID", String.valueOf(phongCachID));
        if (cacheService.isContain(requestId)) {
            return cacheService.readCache(requestId, NoiThat.class).orElse(new ArrayList<>());
        }

        List<NoiThat> noiThatList = noiThatRestService.searchByPhongCach(phongCachID);
        cacheService.writeCache(noiThatList, requestId);
        return noiThatList;
    }


    /**
     * Retrieves a list of HangMuc objects based on the provided PhongCach and NoiThat.
     *
     * @param phongCach The PhongCach for which to find HangMuc objects.
     * @param noiThat   The NoiThat for which to find HangMuc objects.
     * @return List of HangMuc objects matching the provided PhongCach and NoiThat.
     */
    public List<HangMuc> findHangMucListBy(String phongCach, String noiThat) {
        // Encoding PhongCach and NoiThat to ensure URL safety.
        String requestId = cacheService.createUniqueId("findHangMucListBy", phongCach, noiThat);
        if (cacheService.isContain(requestId)) {
            return cacheService.readCache(requestId, HangMuc.class).orElse(new ArrayList<>());
        }

        List<HangMuc> hangMucList = hangMucRestService.searchBy(phongCach, noiThat);
        cacheService.writeCache(hangMucList, requestId);
        return hangMucList;
    }

    public List<HangMuc> findHangMucListByNoiThatID(Integer noiThatID) {
        // Encoding PhongCach and NoiThat to ensure URL safety.
        String requestId = cacheService.createUniqueId("findHangMucListByNoiThatID", noiThatID.toString());
        if (cacheService.isContain(requestId)) {
            return cacheService.readCache(requestId, HangMuc.class).orElse(new ArrayList<>());
        }

        List<HangMuc> hangMucList = hangMucRestService.searchByNoiThat(noiThatID);
        cacheService.writeCache(hangMucList, requestId);
        return hangMucList;
    }

    /**
     * Retrieves a list of VatLieu objects based on the provided PhongCach, NoiThat, and HangMuc.
     *
     * @param phongCach The PhongCach for which to find VatLieu objects.
     * @param noiThat   The NoiThat for which to find VatLieu objects.
     * @param hangMuc   The HangMuc for which to find VatLieu objects.
     * @return List of VatLieu objects matching the provided PhongCach, NoiThat, and HangMuc.
     */
    public List<VatLieu> findVatLieuListBy(String phongCach, String noiThat, String hangMuc) {
        // Encoding PhongCach, NoiThat, and HangMuc to ensure URL safety.
        String requestId = cacheService.createUniqueId("findVatLieuListBy", phongCach, noiThat, hangMuc);
        if (cacheService.isContain(requestId)) {
            return cacheService.readCache(requestId, VatLieu.class).orElse(new ArrayList<>());
        }

        List<VatLieu> vatLieuList = vatLieuRestService.searchBy(phongCach, noiThat, hangMuc);
        cacheService.writeCache(vatLieuList, requestId);
        return vatLieuList;
    }


    public List<VatLieu> findVatLieuListByHangMucID(Integer hangMucID) {
        // Encoding PhongCach, NoiThat, and HangMuc to ensure URL safety.
        String requestId = cacheService.createUniqueId("findVatLieuListByHangMucID", hangMucID.toString());
        if (cacheService.isContain(requestId)) {
            return cacheService.readCache(requestId, VatLieu.class).orElse(new ArrayList<>());
        }

        List<VatLieu> vatLieuList = vatLieuRestService.searchByHangMuc(hangMucID);
        cacheService.writeCache(vatLieuList, requestId);
        return vatLieuList;
    }
    /**
     * Exports a data package to the specified file of the given file type.
     *
     * @param selectedFile The file to export the data to.
     * @param fileType     The type of the file to export (e.g., NT for NoiThat).
     * @param dataPackage  The data package to export.
     * @return True if the export operation is successful, false otherwise.
     */
    public boolean exportFile(File selectedFile, FileType fileType, DataPackage dataPackage) {
        ExportFile exportFile = fileExportService.getExportService(selectedFile, fileType);
        exportFile.setUpDataForExport(dataPackage);
        try {
            exportFile.export(selectedFile);
            return true;
        } catch (Exception e) {
            LOGGER.error("Some thing is wrong with the export operation", e);
        }
        return false;
    }
}
