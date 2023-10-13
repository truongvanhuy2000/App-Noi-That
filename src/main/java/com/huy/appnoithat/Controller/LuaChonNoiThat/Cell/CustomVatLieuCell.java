package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class CustomVatLieuCell extends TreeTableCell<BangNoiThat, String> {
    private ComboBox<String> comboBox;
    private final ObservableList<String> items;
    private final TreeTableView<BangNoiThat> TableNoiThat;
    private HBox hBox;

    public CustomVatLieuCell(ObservableList<String> items, TreeTableView<BangNoiThat> TableNoiThat) {
        this.items = items;
        this.TableNoiThat = TableNoiThat;
    }

    @Override
    public void startEdit() {
        if (comboBox == null) {
            createComboBox();
            createHBox();
        }
        if (!TableUtils.isEditable(TableNoiThat)) {
            return;
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
            setGraphic(hBox);
            comboBox.show();
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
    }
    private void createHBox() {
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
