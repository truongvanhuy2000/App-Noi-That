package com.huy.appnoithat.Service.DatabaseModify;

import com.huy.appnoithat.Entity.PhongCachNoiThat;
import com.huy.appnoithat.Service.RestService.PhongCachRestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DatabaseModifyPhongCachService {
    final static Logger LOGGER = LogManager.getLogger(DatabaseModifyPhongCachService.class);
    private final PhongCachRestService phongCachRestService;

    public DatabaseModifyPhongCachService() {
        phongCachRestService = PhongCachRestService.getInstance();
    }

    public List<PhongCachNoiThat> findAllPhongCach() {
        return phongCachRestService.findAll();
    }

    public void addNewPhongCach(PhongCachNoiThat phongCachNoiThat) {
        phongCachRestService.save(phongCachNoiThat);
    }

    public void EditPhongCach(PhongCachNoiThat phongCachNoiThat) {
        phongCachRestService.update(phongCachNoiThat);
    }

    public void deletePhongCach(int id) {
        phongCachRestService.deleteById(id);
    }
    public void fetchSamplePhongCachData() {
        phongCachRestService.copySampleDataFromAdmin();
    }
    public void swap(int id1, int id2) {
        phongCachRestService.swap(id1, id2);
    }
}
