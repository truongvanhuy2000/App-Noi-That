package com.huy.appnoithat.Service.FileExport.Operation.NtFile;

import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import com.huy.appnoithat.DataModel.ThongTinThanhToan;
import com.huy.appnoithat.Service.FileExport.ExportData.CommonExportData;
import com.huy.appnoithat.Service.FileExport.ExportFile;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
@Getter
@Setter
public class ExportNtFile implements ExportFile {
    private ThongTinCongTy thongTinCongTy;
    private ThongTinKhachHang thongTinKhachHang;
    private String noteArea;
    private List<ThongTinNoiThat> thongTinNoiThatList;
    private ThongTinThanhToan thongTinThanhToan;

    private OutputStream outputFile;
    public ExportNtFile() {
    }
    public ExportNtFile(File outputFile) {
        try {
            this.outputFile = new FileOutputStream(outputFile.getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void export(){

    }

    @Override
    public void setUpDataForExport(CommonExportData dataForExport) {
        setThongTinCongTy(dataForExport.getThongTinCongTy());
        setThongTinKhachHang(dataForExport.getThongTinKhachHang());
        setThongTinNoiThatList(dataForExport.getThongTinNoiThatList());
        setThongTinThanhToan(dataForExport.getThongTinThanhToan());
        setNoteArea(dataForExport.getNoteArea());
    }

    @Override
    public void importData() {

    }


}
