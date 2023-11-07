package com.huy.appnoithat.Service.LuaChonNoiThat;

import com.huy.appnoithat.Controller.FileNoiThatExplorer.RecentFile;
import com.huy.appnoithat.DataModel.NtFile.DataPackage;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

public class LuaChonNoiThatService {
    final static Logger LOGGER = LogManager.getLogger(LuaChonNoiThatService.class);
    private final FileExportService fileExportService;
    private final PhongCachRestService phongCachRestService;
    private final NoiThatRestService noiThatRestService;
    private final HangMucRestService hangMucRestService;
    private final VatLieuRestService vatLieuRestService;
    private final FileNoiThatExplorerService fileNoiThatExplorerService;
    private final CacheNoiThatRequestService cacheNoiThatRequestService;

    /**
     * Initializes the LuaChonNoiThatService by initializing required services and components.
     */
    public LuaChonNoiThatService() {
        fileExportService = new FileExportService();
        phongCachRestService = PhongCachRestService.getInstance();
        noiThatRestService = NoiThatRestService.getInstance();
        hangMucRestService = HangMucRestService.getInstance();
        vatLieuRestService = VatLieuRestService.getInstance();
        fileNoiThatExplorerService = FileNoiThatExplorerService.getInstance();
        cacheNoiThatRequestService = CacheNoiThatRequestService.getInstance();
    }

    /**
     * Retrieves a list of all PhongCachNoiThat from the service.
     *
     * @return List of PhongCachNoiThat objects.
     */

    public List<PhongCachNoiThat> findAllPhongCachNoiThat() {
        return phongCachRestService.findAll();
    }

    /**
     * Retrieves a list of NoiThat objects based on the provided PhongCach.
     *
     * @param phongCach The PhongCach for which to find NoiThat objects.
     * @return List of NoiThat objects matching the provided PhongCach.
     */
    public List<NoiThat> findNoiThatListBy(String phongCach) {
        // Encoding the PhongCach to ensure URL safety.
        phongCach = URLEncoder.encode(phongCach, StandardCharsets.UTF_8);

//        String requestId = cacheNoiThatRequestService.createUniqueId("findNoiThatListBy", phongCach);
//        if (cacheNoiThatRequestService.isContain(requestId)) {
//            return cacheNoiThatRequestService.readCache(requestId, NoiThat.class);
//        }

        List<NoiThat> noiThatList = noiThatRestService.searchBy(phongCach);
//        cacheNoiThatRequestService.writeCache(noiThatList, requestId);
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
        phongCach = URLEncoder.encode(phongCach, StandardCharsets.UTF_8);
        noiThat = URLEncoder.encode(noiThat, StandardCharsets.UTF_8);

//        String requestId = cacheNoiThatRequestService.createUniqueId("findHangMucListBy", phongCach, noiThat);
//        if (cacheNoiThatRequestService.isContain(requestId)) {
//            return cacheNoiThatRequestService.readCache(requestId, HangMuc.class);
//        }

        List<HangMuc> hangMucList = hangMucRestService.searchBy(phongCach, noiThat);
//        cacheNoiThatRequestService.writeCache(hangMucList, requestId);
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
        phongCach = URLEncoder.encode(phongCach, StandardCharsets.UTF_8);
        noiThat = URLEncoder.encode(noiThat, StandardCharsets.UTF_8);
        hangMuc = URLEncoder.encode(hangMuc, StandardCharsets.UTF_8);

//        String requestId = cacheNoiThatRequestService.createUniqueId("findVatLieuListBy", phongCach, noiThat, hangMuc);
//        if (cacheNoiThatRequestService.isContain(requestId)) {
//            return cacheNoiThatRequestService.readCache(requestId, VatLieu.class);
//        }

        List<VatLieu> vatLieuList = vatLieuRestService.searchBy(phongCach, noiThat, hangMuc);
//        cacheNoiThatRequestService.writeCache(vatLieuList, requestId);
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
    public boolean exportFile(File selectedFile, FileType fileType, DataPackage dataPackage, Consumer<Boolean> target) {
        ExportFile exportFile = fileExportService.getExportService(selectedFile, fileType);
        exportFile.setUpDataForExport(dataPackage);
        new Thread(() -> {
            try {
                exportFile.export(selectedFile);
                target.accept(true);
            } catch (Exception e) {
                LOGGER.error("Some thing is wrong with the export operation", e);
                target.accept(false);
            }
            if (fileType == FileType.NT) {
                fileNoiThatExplorerService.addRecentFile(new RecentFile(selectedFile.getAbsolutePath(), System.currentTimeMillis()));
            }
        }).start();
        return true;
    }

    /**
     * Imports a data package from the specified file.
     *
     * @param selectedFile The file to import the data from.
     * @return The imported data package.
     */
    public DataPackage importFile(File selectedFile) {
        ExportFile exportFile = fileExportService.getExportService(selectedFile, FileType.NT);
        return exportFile.importData(selectedFile);
    }
}
