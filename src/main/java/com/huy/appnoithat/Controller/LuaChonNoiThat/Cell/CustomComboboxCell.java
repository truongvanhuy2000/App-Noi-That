package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Controller.LuaChonNoiThat.BangNoiThat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.cell.ComboBoxTreeTableCell;

public class CustomComboboxCell extends TreeTableCell<BangNoiThat, String> {
    private ComboBox<String> comboBox;
    private final ObservableList<String> items;
    private String oldValue;
    public CustomComboboxCell(ObservableList<String> items) {
        this.items = items;
    }
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBox();
            super.setText(null);
            setGraphic(comboBox);
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
        } else {
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
//            if (super.getItem() != null && !super.getItem().isEmpty()) {
//                oldValue = super.getItem();
//                System.out.println(oldValue);
//            }
        }
    }

    private void createComboBox() {
        comboBox = new ComboBox<>(items);
//        comboBoxConverter(comboBox);
        comboBox.valueProperty().set(super.getItem());
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        comboBox.setOnAction((e) -> {
//            System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
            super.commitEdit(comboBox.getSelectionModel().getSelectedItem());
        });
    }

//    private void comboBoxConverter(ComboBox<String> comboBox) {
//        // Define rendering of the list of values in ComboBox drop down.
//        comboBox.setCellFactory((c) -> new ListCell<String>() {
//            @Override
//            protected void updateItem(String item, boolean empty) {
//                super.updateItem(item, empty);
//
//                if (item == null || empty) {
//                    super.setText(null);
//                } else {
//                    super.setText(item);
//                }
//            }
//        });
//    }

}
