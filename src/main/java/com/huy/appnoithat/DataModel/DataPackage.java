package com.huy.appnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @JsonProperty("percentage")
    private List<String> percentage;
}
