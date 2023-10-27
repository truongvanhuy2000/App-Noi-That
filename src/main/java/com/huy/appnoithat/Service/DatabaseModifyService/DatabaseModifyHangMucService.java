package com.huy.appnoithat.Service.DatabaseModifyService;

import com.huy.appnoithat.Entity.HangMuc;
import com.huy.appnoithat.Service.RestService.HangMucRestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DatabaseModifyHangMucService {
    final static Logger LOGGER = LogManager.getLogger(DatabaseModifyHangMucService.class);
    private final HangMucRestService hangMucRestService;

    public DatabaseModifyHangMucService() {
        hangMucRestService = HangMucRestService.getInstance();
    }

    public List<HangMuc> findHangMucByParentId(int id) {
        return hangMucRestService.searchByNoiThat(id);
    }

    public void addNewHangMuc(HangMuc hangMuc, int parentID) {
        hangMucRestService.save(hangMuc, parentID);
    }

    public void EditHangMuc(HangMuc hangMuc) {
        hangMucRestService.update(hangMuc);
    }

    public void deleteHangMuc(int id) {
        hangMucRestService.deleteById(id);
    }
    public void fetchSampleHangMucData(int parentId) {
        hangMucRestService.copySampleDataFromAdmin(parentId);
    }
    public void swap(int id1, int id2) {
        hangMucRestService.swap(id1, id2);
    }
}
