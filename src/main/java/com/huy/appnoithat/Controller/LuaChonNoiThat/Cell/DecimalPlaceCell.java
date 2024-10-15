package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableView;
import javafx.util.StringConverter;

public class DecimalPlaceCell<X> extends CustomEditingCell<X> {

    public DecimalPlaceCell(TreeTableView<BangNoiThat> TableNoiThat, StringConverter<X> converter) {
        super(TableNoiThat, converter);
    }

    @Override
    public void startEdit() {
        if (!TableUtils.isEditable(TableNoiThat)) {
            return;
        }
        super.startEdit();
        textField.setText(convertDecimalPlaceToNormalNumber(getString()));
    }

    private String convertDecimalPlaceToNormalNumber(String input) {
        if (input == null) {
            return "";
        }
        input = input.trim();
        return String.valueOf(Utils.convertDecimalToLong(input));
    }
}
