package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomEditingCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DonViColumn implements CustomColumn {
    private final TreeTableColumn<BangNoiThat, String> DonVi;

    @Override
    public void setup() {
        DonVi.setCellValueFactory(param -> {
            if (param.getValue() == null) return null;
            return param.getValue().getValue().getDonVi();
        });
        DonVi.setCellFactory(param -> new CustomEditingCell<>(false));
    }
}
