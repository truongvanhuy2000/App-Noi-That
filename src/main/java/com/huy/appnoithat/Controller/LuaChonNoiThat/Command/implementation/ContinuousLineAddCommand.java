package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Utils.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContinuousLineAddCommand implements Command {
    private final TreeTableView<BangNoiThat> TableNoiThat;
    private Command internalCommand;

    @Override
    public void execute() {
        if (TableNoiThat.getSelectionModel().getSelectedItems().isEmpty()) {
            internalCommand = new AddNewItemIfEmptyCommand(TableNoiThat);
            internalCommand.execute();
            return;
        }

        TreeItem<BangNoiThat> currentSelectedItem = TableNoiThat.getSelectionModel().getSelectedItem();
        if (currentSelectedItem == null) {
            return;
        }
        switch (currentSelectedItem.getValue().getItemType()) {
            case ROMAN -> {
                internalCommand = new ContinuousAddForRomanSttCommand(TableNoiThat);
                internalCommand.execute();
            }
            case AlPHA -> {
                internalCommand = new ContinuousAddForAlphaSttCommand(TableNoiThat);
                internalCommand.execute();
            }
            case NUMERIC -> {
                internalCommand = new ContinuousAddForNumericSttCommand(TableNoiThat);
                internalCommand.execute();
            }
        }
        TableUtils.reArrangeList(TableNoiThat);
    }

    @Override
    public void undo() {
        if (internalCommand != null) {
            internalCommand.undo();
        }
    }
}
