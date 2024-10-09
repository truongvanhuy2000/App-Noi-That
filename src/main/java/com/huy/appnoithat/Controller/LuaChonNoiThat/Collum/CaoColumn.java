package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomEditingCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.CommandManager;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.EditCommitCaoCommand;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Converter.KichThuocConverter;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CaoColumn implements CustomColumn {
    private final TreeTableColumn<BangNoiThat, Double> Cao;
    private final CommandManager commandManager;

    @Override
    public void setup() {
        Cao.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getCao().asObject();
        });
        Cao.setCellFactory(param -> new CustomEditingCell<>(Cao.getTreeTableView(), new KichThuocConverter()));
        Cao.setOnEditCommit(event -> {
            commandManager.execute(new EditCommitCaoCommand(event));
        });
    }
}
