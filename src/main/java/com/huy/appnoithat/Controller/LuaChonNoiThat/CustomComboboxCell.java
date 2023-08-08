package com.huy.appnoithat.Controller.LuaChonNoiThat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;

public class CustomComboboxCell extends TableCell<BangNoiThat, String> {
    private ComboBox<String> comboBox;
    ObservableList<String> dropDownData = FXCollections.observableArrayList();
    @Override
    public void startEdit() {
        if (isEmpty()){
            return;
        }
        super.startEdit();
        createComboBox();
        setText(null);
        setGraphic(comboBox);
    }
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
    }
    @Override
    public void cancelEdit() {
        super.cancelEdit();
//            setText(getTyp().getTyp());
        setGraphic(null);
    }
    private void createComboBox() {
        comboBox = new ComboBox<>(dropDownData);
//            comboBoxConverter(comboBox);
//            comboBox.valueProperty().set(getTyp());
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        comboBox.setOnAction((e) -> {
            System.out.println("Committed: " + comboBox.getSelectionModel().getSelectedItem());
            commitEdit(comboBox.getSelectionModel().getSelectedItem());
        });
        //            comboBox.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
        //                if (!newValue) {
        //                    commitEdit(comboBox.getSelectionModel().getSelectedItem());
        //                }
        //            });
    }
}
