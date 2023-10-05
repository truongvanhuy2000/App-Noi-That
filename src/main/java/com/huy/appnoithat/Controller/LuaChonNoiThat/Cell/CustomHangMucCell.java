package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.awt.event.MouseEvent;

public class CustomHangMucCell extends TreeTableCell<BangNoiThat, String> {
    private ComboBox<String> comboBox;
    private HBox hBox;
    private final ObservableList<String> items;

    public CustomHangMucCell(ObservableList<String> items) {
        this.items = items;
    }
    @Override
    public void startEdit() {
        if (comboBox == null) {
            createComboBox();
            createVBox();
        }

        if (!isEmpty()) {
            super.startEdit();
            setGraphic(hBox);
            showComboBoxAfter(100);
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
        if (empty || item == null) {
            super.setText(null);
            setGraphic(null);
            return;
        }
        if (isEditing()) {
            if (comboBox != null) {
                comboBox.setValue(super.getItem());
            }
            super.setText(super.getItem());
            setGraphic(hBox);
        } else {
            super.setText(super.getItem());
            setGraphic(null);
        }
    }

    private void createComboBox() {
        if (comboBox != null) {
            return;
        }
        comboBox = new ComboBox<>(items);
        comboBox.valueProperty().set(super.getItem());
        comboBox.setMinWidth(this.getWidth() - 30);
        comboBox.setOnAction((e) -> {
            if (comboBox.getSelectionModel().getSelectedItem() != null) {
                super.commitEdit(comboBox.getSelectionModel().getSelectedItem());
                updateItem(comboBox.getSelectionModel().getSelectedItem(), false);
            }
        });
        comboBox.setOnMouseClicked((e) -> {
            comboBox.hide();
        });
        comboBox.getStyleClass().add("combo-border");
    }
    private void createVBox() {
        if (hBox != null) {
            return;
        }
        Button dropDownButton = new Button("V");
        dropDownButton.setOnAction((e) -> {
            showComboBoxAfter(100);
        });
        hBox = new HBox();
        hBox.getChildren().add(dropDownButton);
        hBox.getChildren().add(comboBox);
    }
    private void showComboBoxAfter(double millis) {
        PauseTransition delay = new PauseTransition(Duration.millis(millis));
        delay.setOnFinished( event -> comboBox.show());
        delay.play();
    }
}
