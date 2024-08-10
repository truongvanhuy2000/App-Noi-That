package com.huy.appnoithat.Service.DatabaseModify;

import com.huy.appnoithat.DataModel.Entity.VatLieu;
import com.huy.appnoithat.Service.RestService.VatLieuRestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DatabaseModifyVatlieuService {
    final Logger LOGGER = LogManager.getLogger(DatabaseModifyVatlieuService.class);
    private final VatLieuRestService vatLieuRestService;

    public DatabaseModifyVatlieuService(VatLieuRestService vatLieuRestService) {
        this.vatLieuRestService = vatLieuRestService;
    }

    public List<VatLieu> findVatLieuByParentId(int id) {
        return vatLieuRestService.searchByHangMuc(id);
    }

    public void addNewVatLieu(VatLieu vatLieu, int parentID) {
        vatLieuRestService.save(vatLieu, parentID);
    }

    public void EditVatLieu(VatLieu vatLieu) {
        vatLieuRestService.update(vatLieu);
    }

    public void deleteVatLieu(int id) {
        vatLieuRestService.deleteById(id);
    }
    public void fetchSampleVatLieuData(int parentId) {
        vatLieuRestService.copySampleDataFromAdmin(parentId);
    }

    public void swap(int id1, int id2) {
        vatLieuRestService.swap(id1, id2);
    }
}
