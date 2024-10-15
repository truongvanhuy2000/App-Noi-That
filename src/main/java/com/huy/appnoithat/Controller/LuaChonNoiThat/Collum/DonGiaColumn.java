package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomEditingCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.DecimalPlaceCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.CommandManager;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.EditCommitDonGiaCommand;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Converter.DecimalLongStringConverter;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Converter.DecimalPlaceFormulaLongConverter;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import javafx.util.StringConverter;
import javafx.util.converter.LongStringConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class DonGiaColumn implements CustomColumn {
    private final TreeTableColumn<BangNoiThat, Long> DonGia;
    private final CommandManager commandManager;

    @Override
    public void setup() {
        DonGia.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getDonGia().asObject();
        });
        DonGia.setCellFactory(param -> new DecimalPlaceCell<>(DonGia.getTreeTableView(), new DecimalPlaceFormulaLongConverter()));
        DonGia.setOnEditCommit(event -> {
            commandManager.execute(new EditCommitDonGiaCommand(event));
        });
    }
}
