package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomNumberCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.CommandManager;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.EditCommitDonGiaCommand;
import com.huy.appnoithat.Controller.LuaChonNoiThat.CustomConverter.CustomLongStringConverter;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import lombok.RequiredArgsConstructor;

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
        DonGia.setCellFactory(param -> new CustomNumberCell<>(new CustomLongStringConverter(), DonGia.getTreeTableView(), false));
        DonGia.setOnEditCommit(event -> {
            commandManager.execute(new EditCommitDonGiaCommand(event));
        });
    }
}
