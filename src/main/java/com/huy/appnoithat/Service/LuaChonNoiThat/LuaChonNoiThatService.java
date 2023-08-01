package com.huy.appnoithat.Service.LuaChonNoiThat;

import com.huy.appnoithat.Entity.*;

import java.util.ArrayList;
import java.util.List;

public class LuaChonNoiThatService {
    ThongSo ts1 = new ThongSo(1, 10, 5, 3, "cm", 50);
    ThongSo ts2 = new ThongSo(2, 8, 6, 4, "cm", 45);
    ThongSo ts3 = new ThongSo(3, 12, 7, 2, "cm", 55);
    ThongSo ts4 = new ThongSo(4, 15, 5, 3, "cm", 60);
    ThongSo ts5 = new ThongSo(5, 10, 5, 3, "cm", 50);
    ThongSo ts6 = new ThongSo(6, 10, 5, 3, "cm", 50);
    ThongSo ts7 = new ThongSo(7, 10, 5, 3, "cm", 50);
    ThongSo ts8 = new ThongSo(8, 10, 5, 3, "cm", 50);
    ThongSo ts9 = new ThongSo(9, 10, 5, 3, "cm", 50);

    VatLieu vl1 = new VatLieu(1, "Wood", new ThongSo());
    VatLieu vl2 = new VatLieu(2, "Metal", new ThongSo());
    VatLieu vl3 = new VatLieu(3, "Plastic", new ThongSo());
    VatLieu vl4 = new VatLieu(4, "Wood", new ThongSo());
    VatLieu vl5 = new VatLieu(5, "Metal", new ThongSo());
    VatLieu vl6 = new VatLieu(6, "Plastic", new ThongSo());
    VatLieu vl7 = new VatLieu(7, "Wood", new ThongSo());
    VatLieu vl8 = new VatLieu(8, "Metal", new ThongSo());
    VatLieu vl9 = new VatLieu(9, "Plastic", new ThongSo());

    HangMuc hm1 = new HangMuc(1, "IndoorTable", new ArrayList<>());
    HangMuc hm2 = new HangMuc(2, "IndoorChair", new ArrayList<>());
    HangMuc hm3 = new HangMuc(3, "IndoorBed", new ArrayList<>());
    HangMuc hm4 = new HangMuc(4, "OutdoorTable", new ArrayList<>());

    NoiThat nt1 = new NoiThat(1, "Table", new ArrayList<>());
    NoiThat nt2 = new NoiThat(2, "Chair", new ArrayList<>());
    NoiThat nt3 = new NoiThat(3, "Bed", new ArrayList<>());

    PhongCachNoiThat pc1 = new PhongCachNoiThat(1, "Modern", new ArrayList<>());
    PhongCachNoiThat pc2 = new PhongCachNoiThat(2, "Classic", new ArrayList<>());
    // Fake the data
    public LuaChonNoiThatService() {
        vl1.setThongSo(ts1);
        vl2.setThongSo(ts2);
        vl3.setThongSo(ts3);
        vl4.setThongSo(ts4);
        vl5.setThongSo(ts5);
        vl6.setThongSo(ts6);
        vl7.setThongSo(ts7);
        vl8.setThongSo(ts8);
        vl9.setThongSo(ts9);

        hm1.add(vl1);
        hm1.add(vl2);
        hm1.add(vl3);
        hm2.add(vl4);
        hm2.add(vl5);
        hm2.add(vl6);
        hm3.add(vl7);
        hm3.add(vl8);
        hm3.add(vl9);

        nt1.add(hm1);
        nt1.add(hm2);
        nt2.add(hm3);
        nt3.add(hm4);

        pc1.add(nt1);
        pc1.add(nt2);
        pc2.add(nt3);
    }

    public List<PhongCachNoiThat> findAllPhongCachNoiThat() {
        List<PhongCachNoiThat> list = new ArrayList<>();
        list.add(pc1);
        list.add(pc2);
        return list;
    }

    public PhongCachNoiThat findPhongCachNoiThatById(int id) {
        List<PhongCachNoiThat> list = findAllPhongCachNoiThat();
        return list.stream().filter(pc -> pc.getId() == id).findFirst().orElse(null);
    }
}
