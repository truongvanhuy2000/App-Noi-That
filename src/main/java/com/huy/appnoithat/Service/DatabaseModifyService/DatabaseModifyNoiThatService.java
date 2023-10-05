package com.huy.appnoithat.Service.DatabaseModifyService;

import com.huy.appnoithat.Entity.NoiThat;
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
}
