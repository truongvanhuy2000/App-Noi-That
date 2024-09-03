package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;


import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Scene.LuaChonNoiThat.LuaChonNoiThatScene;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableColumn;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.assertions.api.Assertions;

import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class EditCommitThanhTienCommandTest {


    @Start
    private void start(Stage stage) {

    }

    @Test
    void execute() {
        BangNoiThat testData = BangNoiThat.builder()
                .id("1").thanhTien(100L).cao(100.0)
                .dai(100.0).rong(100.0).donVi("test")
                .donGia(100L).hangMuc("test").vatLieu("test").khoiLuong(100.0)
                .build();
        long expectedThanhTien = 999L;

        Mockito.mockStatic(TableCalculationUtils.class);
        when(event.getRowValue().getValue()).thenReturn(testData);
        when(event.getNewValue()).thenReturn(expectedThanhTien);
        command.execute();

        Assertions.assertEquals(testData.getThanhTien(), expectedThanhTien);
    }

    @Test
    void undo() {
    }
}