package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomEditingCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.CommandManager;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.EditCommitThanhTienCommand;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Converter.DecimalLongStringConverter;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.StringConverter;
import javafx.util.converter.LongStringConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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
        ThanhTien.setCellFactory(param -> new CustomEditingCell<>(TableNoiThat, new ThanhTienConverter()));
        ThanhTien.setOnEditCommit(event -> {
            commandManager.execute(new EditCommitThanhTienCommand(event));
        });
    }

    private static class ThanhTienConverter extends StringConverter<Long> {
        private final StringConverter<Long> decimalLongStringConverter = new DecimalLongStringConverter();

        @Override
        public Long fromString(String value) {
            return decimalLongStringConverter.fromString(value);
        }

        @Override
        public String toString(Long value) {
            if (value.equals(0L)) {
                return StringUtils.EMPTY;
            }
            return decimalLongStringConverter.toString(value);
        }
    }
}
