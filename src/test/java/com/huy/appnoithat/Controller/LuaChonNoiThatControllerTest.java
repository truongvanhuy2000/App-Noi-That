package com.huy.appnoithat.Controller;

import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import com.huy.appnoithat.DataModel.ThongTinNoiThat;
import javafx.scene.control.TreeItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class LuaChonNoiThatControllerTest extends LuaChonNoiThatController {
    List<ThongTinNoiThat> thongTinNoiThatList;

    @BeforeEach
    void setUp() {
        thongTinNoiThatList = new ArrayList<>();
        thongTinNoiThatList.add(new ThongTinNoiThat("A", "Hàng mục 1", "Chi tiết 1", "100", "100", "100", "mét vuông", "1000000", "1", "1000000"));
//        thongTinNoiThatList.add(new ThongTinNoiThat("I", "Hàng mục 2", "Chi tiết 2", "100", "100", "100", "mét vuông", "1000000", "1", "1000000"));
        thongTinNoiThatList.add(new ThongTinNoiThat("1", "Hàng mục 2", "Chi tiết 2", "100", "100", "100", "mét vuông", "1000000", "1", "1000000"));
        thongTinNoiThatList.add(new ThongTinNoiThat("2", "Hàng mục 3", "Chi tiết 3", "100", "100", "100", "mét vuông", "1000000", "1", "1000000"));
        thongTinNoiThatList.add(new ThongTinNoiThat("II", "Hàng mục 4", "Chi tiết 4", "100", "100", "100", "mét vuông", "1000000", "1", "1000000"));
        thongTinNoiThatList.add(new ThongTinNoiThat("1", "Hàng mục 5", "Chi tiết 5", "100", "100", "100", "mét vuông", "1000000", "1", "1000000"));
        thongTinNoiThatList.add(new ThongTinNoiThat("B", "Hàng mục 6", "Chi tiết 6", "100", "100", "100", "mét vuông", "1000000", "1", "1000000"));
        thongTinNoiThatList.add(new ThongTinNoiThat("I", "Hàng mục 7", "Chi tiết 7", "100", "100", "100", "mét vuông", "1000000", "1", "1000000"));
        thongTinNoiThatList.add(new ThongTinNoiThat("1", "Hàng mục 8", "Chi tiết 8", "100", "100", "100", "mét vuông", "1000000", "1", "1000000"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testImportFromThongTinList() {
        TreeItem<BangNoiThat> root = importFromThongTinList(thongTinNoiThatList);
        System.out.println(root);
    }
}