package com.huy.appnoithat.Service.DatabaseModify;

import com.huy.appnoithat.DataModel.Entity.ThongSo;
import com.huy.appnoithat.Service.RestService.ThongSoRestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DatabaseModifyThongSoService {
    final static Logger LOGGER = LogManager.getLogger(DatabaseModifyThongSoService.class);
    private final ThongSoRestService thongSoRestService;

    public DatabaseModifyThongSoService() {
        thongSoRestService = new ThongSoRestService();
    }

    public List<ThongSo> findThongSoByParentId(int id) {
        return thongSoRestService.searchByVatLieu(id);
    }

    public void EditThongSo(ThongSo thongSo) {
        thongSoRestService.update(thongSo);
    }
    public void addNewThongSo(ThongSo thongSo, int parentId) {
        thongSoRestService.save(thongSo, parentId);
    }
    public void fetchSampleThongSoData(int parentId) {
        thongSoRestService.copySampleDataFromAdmin(parentId);
    }

}
