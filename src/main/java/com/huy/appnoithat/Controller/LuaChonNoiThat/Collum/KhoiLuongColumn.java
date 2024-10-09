package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomEditingCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.CommandManager;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.EditCommitKhoiLuongCommand;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import javafx.util.converter.DoubleStringConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class KhoiLuongColumn implements CustomColumn {
    private final TreeTableColumn<BangNoiThat, Double> KhoiLuong;
    private final CommandManager commandManager;

    /**
     * This function will set up the collum for KichThuoc
     */
    @Override
    public void setup() {
        // Set up collum for KhoiLuong
        KhoiLuong.setText("Khối\nlượng");
        KhoiLuong.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getKhoiLuong().asObject();
        });
        KhoiLuong.setCellFactory(param -> new CustomEditingCell<>(KhoiLuong.getTreeTableView(), new KhoiLuongConverter()));
        KhoiLuong.setOnEditCommit(event -> {
            commandManager.execute(new EditCommitKhoiLuongCommand(event));
        });
    }

    private static class KhoiLuongConverter extends DoubleStringConverter {
        @Override
        public String toString(Double value) {
            if (value.equals(0.0)) {
                return StringUtils.EMPTY;
            }
            return super.toString(value);
        }
    }
}
