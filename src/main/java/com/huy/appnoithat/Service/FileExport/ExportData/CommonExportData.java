package com.huy.appnoithat.Service.FileExport.ExportData;

import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import com.huy.appnoithat.DataModel.ThongTinThanhToan;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class CommonExportData {
    private ThongTinCongTy thongTinCongTy;
    private ThongTinKhachHang thongTinKhachHang;
    private String noteArea;
    private List<ThongTinNoiThat> thongTinNoiThatList;
    private ThongTinThanhToan thongTinThanhToan;
}
