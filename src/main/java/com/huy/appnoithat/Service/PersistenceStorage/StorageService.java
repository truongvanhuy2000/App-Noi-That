package com.huy.appnoithat.Service.PersistenceStorage;

import com.huy.appnoithat.DataModel.RecentFile;
import com.huy.appnoithat.DataModel.Session.PersistenceUserSession;
import com.huy.appnoithat.DataModel.ThongTinCongTy;

import java.util.List;

public interface StorageService {
    ThongTinCongTy getThongTinCongTy();
    void saveThongTinCongTy(ThongTinCongTy thongTinCongTy);
    List<RecentFile> getRecentFileList();

    void saveRecentFile(List<RecentFile> recentFileList);

    PersistenceUserSession getUserSession();
    void saveUserSession(PersistenceUserSession persistenceUserSession);

    void saveNoteArea(String noteArea);
    String getNoteArea();
}
