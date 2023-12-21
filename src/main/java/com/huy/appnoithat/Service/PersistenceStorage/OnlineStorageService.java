package com.huy.appnoithat.Service.PersistenceStorage;

import com.huy.appnoithat.DataModel.LapBaoGiaInfo;
import com.huy.appnoithat.DataModel.RecentFile;
import com.huy.appnoithat.DataModel.Session.PersistenceUserSession;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.Service.RestService.LapBaoGiaRestService;

import java.util.List;

public class OnlineStorageService implements StorageService {
    LapBaoGiaRestService lapBaoGiaRestService;
    public OnlineStorageService() {
        lapBaoGiaRestService = LapBaoGiaRestService.getInstance();
    }
    @Override
    public ThongTinCongTy getThongTinCongTy() {
        LapBaoGiaInfo lapBaoGiaInfo = lapBaoGiaRestService.getLapBaoGiaInfo();
        if (lapBaoGiaInfo == null) {
            return null;
        }
        return ThongTinCongTy.builder()
                .tenCongTy(lapBaoGiaInfo.getTenCongTy())
                .diaChiVanPhong(lapBaoGiaInfo.getDiaChiVanPhong())
                .soDienThoai(lapBaoGiaInfo.getSoDienThoai())
                .email(lapBaoGiaInfo.getEmail())
                .diaChiXuong(lapBaoGiaInfo.getDiaChiXuong())
                .logo(lapBaoGiaInfo.getLogo())
                .build();
    }

    @Override
    public void saveThongTinCongTy(ThongTinCongTy thongTinCongTy) {
        lapBaoGiaRestService.saveThongTinCongTy(thongTinCongTy);
    }

    @Override
    public List<RecentFile> getRecentFileList() {
        return null;
    }

    @Override
    public void saveRecentFile(List<RecentFile> recentFileList) {

    }

    @Override
    public PersistenceUserSession getUserSession() {
        return null;
    }

    @Override
    public void saveUserSession(PersistenceUserSession persistenceUserSession) {

    }

    @Override
    public void saveNoteArea(String noteArea) {
        lapBaoGiaRestService.saveNote(noteArea);
    }

    @Override
    public String getNoteArea() {
        LapBaoGiaInfo lapBaoGiaInfo = lapBaoGiaRestService.getLapBaoGiaInfo();
        if (lapBaoGiaInfo == null) {
            return null;
        }
        return lapBaoGiaInfo.getNote();
    }
}
