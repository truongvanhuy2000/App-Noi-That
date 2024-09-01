package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.BangThanhToan;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangThanhToan;
import javafx.beans.property.LongPropertyBase;
import javafx.scene.control.TableColumn;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditBangThanhToan<V extends LongPropertyBase, T extends Number> implements Command {
    private final TableColumn.CellEditEvent<BangThanhToan, T> event;
    private final V editedProperty;

    private T oldValue;

    @Override
    public void execute() {
        if (event.getNewValue() == null) {
            return;
        }
        oldValue = event.getOldValue();
        editedProperty.setValue(event.getNewValue());
    }

    @Override
    public void undo() {
        if (oldValue != null) {
            editedProperty.setValue(oldValue);
        }
    }
}
