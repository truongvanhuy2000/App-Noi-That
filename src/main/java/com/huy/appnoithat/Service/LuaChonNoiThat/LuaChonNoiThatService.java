package com.huy.appnoithat.Service.LuaChonNoiThat;

import com.huy.appnoithat.Entity.*;

import java.util.ArrayList;
import java.util.List;

public class LuaChonNoiThatService {
    ThongSo ts1 = new ThongSo(1, 1000F, 50F, 300F, "mét dài", 50L);
    ThongSo ts2 = new ThongSo(2, 800F, 600F, 400F, "mét dài", 45L);
    ThongSo ts3 = new ThongSo(3, 1200F, 700F, 200F, "mét dài", 55L);
    ThongSo ts4 = new ThongSo(4, 1500F, 50F, 300F, "mét dài", 60L);
    ThongSo ts5 = new ThongSo(5, 1000F, 50F, 3000F, "mét dài", 50L);
    ThongSo ts6 = new ThongSo(6, 1000F, 5000F, 3000F, "mét vuông", 50L);
    ThongSo ts7 = new ThongSo(7, 1000F, 5000F, 300F, "mét vuông", 50L);
    ThongSo ts8 = new ThongSo(8, 100F, 50F, 3F, "mét vuông", 50L);
    ThongSo ts9 = new ThongSo(9, 10F, 5F, 3F, "mét vuông", 50L);

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

    public List<UserSelection> getFakeUserSelection(){
        List<UserSelection> listSelection= new ArrayList<UserSelection>();
        listSelection.add(new UserSelection(pc1, nt1, hm1, vl1, ts1));
        listSelection.add(new UserSelection(pc1, nt2, hm2, vl2, ts2));
        return listSelection;
    }
    public PhongCachNoiThat findPhongCachNoiThatByName(String name) {
        List<PhongCachNoiThat> list = findAllPhongCachNoiThat();
        return list.stream().filter(pc -> pc.getName().equals(name)).findFirst().orElse(null);
    }
    public List<NoiThat> findNoiThatByPhongCachName(String name) {
        PhongCachNoiThat foundPhongCachNoiThat = findPhongCachNoiThatByName(name);
        if (foundPhongCachNoiThat == null) throw new NullPointerException("Phong cach not found");
        return foundPhongCachNoiThat.getNoiThatList();
    }

    public List<HangMuc> findHangMucListByPhongCachAndNoiThat(String phongCach, String noiThat){
        PhongCachNoiThat foundPhongCach = findPhongCachNoiThatByName(phongCach);
        if (foundPhongCach == null) throw new NullPointerException("Phong cach not found");
        NoiThat foundNoiThat = foundPhongCach.getNoiThatList().stream().filter(nt -> nt.getName().equals(noiThat)).findFirst().orElse(null);
        if (foundNoiThat == null) throw new NullPointerException("Noi that not found");
        return foundNoiThat.getHangMucList();
    }

    public List<VatLieu> findVatLieuListByParentsName(String phongCach, String noiThat, String hangMuc){
        PhongCachNoiThat foundPhongCach = findPhongCachNoiThatByName(phongCach);
        if (foundPhongCach == null) throw new NullPointerException("Phong cach not found");
        NoiThat foundNoiThat = foundPhongCach.getNoiThatList().stream().filter(nt -> nt.getName().equals(noiThat)).findFirst().orElse(null);
        if (foundNoiThat == null) throw new NullPointerException("Noi that not found");
        HangMuc foundHangMuc = foundNoiThat.getHangMucList().stream().filter(hm -> hm.getName().equals(hangMuc)).findFirst().orElse(null);
        if (foundHangMuc == null) throw new NullPointerException("Hang muc not found");
        return foundHangMuc.getVatLieuList();
    }
}
