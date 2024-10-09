package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomEditingCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.CommandManager;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.EditCommitDaiCommand;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Converter.KichThuocConverter;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DaiColumn implements CustomColumn{
    private final TreeTableColumn<BangNoiThat, Double> Dai;
    private final CommandManager commandManager;

    @Override
    public void setup() {
        Dai.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getDai().asObject();
        });
        Dai.setCellFactory(param -> new CustomEditingCell<>(Dai.getTreeTableView(), new KichThuocConverter()));
        Dai.setOnEditCommit(event -> {
            commandManager.execute(new EditCommitDaiCommand(event));
        });
    }
}
