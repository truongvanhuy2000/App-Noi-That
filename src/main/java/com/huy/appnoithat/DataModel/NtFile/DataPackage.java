package com.huy.appnoithat.DataModel.NtFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.appnoithat.DataModel.ThongTinCongTy;
import com.huy.appnoithat.DataModel.ThongTinKhachHang;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import com.huy.appnoithat.DataModel.ThongTinThanhToan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataPackage {
    @JsonProperty("thongTinCongTy")
    private ThongTinCongTy thongTinCongTy;
    @JsonProperty("thongTinKhachHang")
    private ThongTinKhachHang thongTinKhachHang;
    @JsonProperty("noteArea")
    private String noteArea;
    @JsonProperty("thongTinNoiThatList")
    private List<ThongTinNoiThat> thongTinNoiThatList;
    @JsonProperty("thongTinThanhToan")
    private ThongTinThanhToan thongTinThanhToan;
}
