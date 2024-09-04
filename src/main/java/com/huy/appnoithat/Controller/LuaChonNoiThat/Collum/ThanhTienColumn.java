package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomNumberCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.CommandManager;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.EditCommitThanhTienCommand;
import com.huy.appnoithat.Controller.LuaChonNoiThat.CustomConverter.CustomLongStringConverter;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ThanhTienColumn implements CustomColumn  {
    private final TreeTableColumn<BangNoiThat, Long> ThanhTien;
    private final CommandManager commandManager;

    /**
     * This function will set up the collum for ThanhTien
     */
    @Override
    public void setup() {
        TreeTableView<BangNoiThat> TableNoiThat = ThanhTien.getTreeTableView();
        // Set up collum for ThanhTien
        ThanhTien.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getThanhTien().asObject();
        });
        ThanhTien.setCellFactory(param -> new CustomNumberCell<>(new CustomLongStringConverter(), TableNoiThat, false));
        ThanhTien.setOnEditCommit(event -> {
            commandManager.execute(new EditCommitThanhTienCommand(event));
        });
    }
}
