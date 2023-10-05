package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.TableUtils;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableView;

public class CustomVatLieuCell extends TreeTableCell<BangNoiThat, String> {
    private ComboBox<String> comboBox;
    private final ObservableList<String> items;
    private final TreeTableView<BangNoiThat> TableNoiThat;

    public CustomVatLieuCell(ObservableList<String> items, TreeTableView<BangNoiThat> TableNoiThat) {
        this.items = items;
        this.TableNoiThat = TableNoiThat;
    }

    @Override
    public void startEdit() {
        if (comboBox == null) {
            createComboBox();
        }
        if (!TableUtils.isEditable(TableNoiThat)) {
            return;
        }
        if (!isEmpty()) {
            super.startEdit();
            setGraphic(comboBox);
            comboBox.show();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        super.setText(getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            super.setText(null);
            setGraphic(null);
            return;
        }
        if (isEditing()) {
            if (comboBox != null) {
                comboBox.setValue(super.getItem());
            }
            super.setText(super.getItem());
            setGraphic(comboBox);
        } else {
            super.setText(super.getItem());
            setGraphic(null);
        }
    }

    private void createComboBox() {
        comboBox = new ComboBox<>(items);
        comboBox.valueProperty().set(super.getItem());
        comboBox.setMinWidth(this.getWidth());
        comboBox.setOnAction((e) -> {
            if (comboBox.getSelectionModel().getSelectedItem() != null) {
                super.commitEdit(comboBox.getSelectionModel().getSelectedItem());
                updateItem(comboBox.getSelectionModel().getSelectedItem(), false);
            }
        });
//        comboBox.getStyleClass().add("combo-border");
//        comboBox.setEditable(true);
    }
}
