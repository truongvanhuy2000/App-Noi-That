package com.huy.appnoithat.Service.DatabaseModify;

import com.huy.appnoithat.DataModel.Entity.NoiThat;
import com.huy.appnoithat.Service.RestService.NoiThatRestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DatabaseModifyNoiThatService {
    final static Logger LOGGER = LogManager.getLogger(DatabaseModifyNoiThatService.class);
    private final NoiThatRestService noiThatRestService;

    public DatabaseModifyNoiThatService() {
        noiThatRestService = NoiThatRestService.getInstance();
    }

    public List<NoiThat> findNoiThatListByParentID(int parentID) {
        return noiThatRestService.searchByPhongCach(parentID);
    }

    public void addNewNoiThat(NoiThat noiThat, int parentID) {
        noiThatRestService.save(noiThat, parentID);
    }

    public void EditNoiThat(NoiThat noiThat) {
        noiThatRestService.update(noiThat);
    }

    public void deleteNoiThat(int id) {
        noiThatRestService.deleteById(id);
    }
    public void fetchSampleNoiThatData(int parentId) {
        noiThatRestService.copySampleDataFromAdmin(parentId);
    }
    public void swap(int id1, int id2) {
        noiThatRestService.swap(id1, id2);
    }
}
