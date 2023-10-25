package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Enums.Action;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.Objects;

public class CustomHangMucCell extends TreeTableCell<BangNoiThat, String> {
    private ComboBox<String> comboBox;
    private HBox hBox;
    private final ObservableList<String> items;

    public CustomHangMucCell(ObservableList<String> items) {
        this.items = items;
    }

    /**
     * Initiates the editing process for this cell. If the ComboBox and VBox are not
     * initialized, creates them. If the cell is not empty, invokes the superclass's
     * startEdit method, sets the graphic content to the HBox containing the ComboBox and VBox,
     * and schedules the ComboBox to be shown after a delay of 100 milliseconds.
     */
    @Override
    public void startEdit() {
        if (comboBox == null) {
            createComboBox();
            createVBox();
        }

        if (!isEmpty()) {
            super.startEdit();
            setGraphic(hBox);
            showComboBoxAfter(200);
        }
    }


    /**
     * Cancels the editing process for this cell. Overrides the superclass method
     * to revert the cell's state to its original value. Invokes the superclass's
     * cancelEdit method, sets the text to the original item value, and removes the graphic content.
     */
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


    /**
     * Updates the item within this cell and manages its visual representation based on
     * the provided item and its emptiness. Overrides the superclass method to customize
     * the cell's appearance. If the cell is empty or the item is null, displays no text
     * or graphic content. If editing, shows the ComboBox with the current item value;
     * otherwise, displays the item text.
     *
     * @param item  The item to be displayed in the cell.
     * @param empty Indicates whether the cell should be displayed as empty.
     */
    private void createComboBox() {
        if (comboBox != null) {
            return;
        }
        comboBox = new ComboBox<>(items);
        comboBox.valueProperty().set(super.getItem());
        comboBox.setMinWidth(this.getWidth() - 20);
        comboBox.setMaxWidth(Double.MAX_VALUE);
        comboBox.setOnAction((e) -> {
            if (comboBox.getSelectionModel().getSelectedItem() != null) {
                super.commitEdit(comboBox.getSelectionModel().getSelectedItem());
                updateItem(comboBox.getSelectionModel().getSelectedItem(), false);
            }
        });
        comboBox.setOnMouseClicked((e) -> {
            comboBox.hide();
            showComboBoxAfter(200);
        });
        comboBox.setMaxHeight(Double.MAX_VALUE);
        comboBox.setOnKeyPressed((key) -> {
            if (KeyboardUtils.isRightKeyCombo(Action.COMMIT, key)) {
                commitEdit(comboBox.getSelectionModel().getSelectedItem());
                updateItem(comboBox.getSelectionModel().getSelectedItem(), false);
                cancelEdit();
            }
        });
        comboBox.getStyleClass().add("combo-border");
    }

    /**
     * Creates an HBox containing a drop-down button and a ComboBox. If the HBox is already
     * initialized, this method does nothing. The drop-down button triggers the ComboBox
     * to be shown after a delay of 100 milliseconds.
     */
    private void createVBox() {
        if (hBox != null) {
            return;
        }
//        Button dropDownButton = new Button("V");
//        dropDownButton.setOnAction((e) -> {
//            showComboBoxAfter(100);
//        });
        Button editButton = new Button();
        Image img = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/com/huy/appnoithat/Scene/icons/edit.png")));
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        view.preserveRatioProperty().set(true);
        editButton.setGraphic(view);
        editButton.setMaxSize(20, 20);
        TextField textField = new TextField();
        textField.setOnAction((e) -> {
            super.commitEdit(textField.getText());
            updateItem(textField.getText(), false);
        });
        textField.setMaxWidth(Double.MAX_VALUE);
        editButton.setOnAction((e) -> {
            super.setText(null);
            textField.setText(super.getItem());
            setGraphic(textField);
        });

        hBox = new HBox();
        hBox.getChildren().add(editButton);
        hBox.getChildren().add(comboBox);
//        hBox.getChildren().add(editButton);
        hBox.setMaxWidth(Double.MAX_VALUE);
        hBox.setMaxWidth(this.getWidth());
    }

    /**
     * Displays the ComboBox after a specified delay in milliseconds.
     *
     * @param millis The delay time in milliseconds before showing the ComboBox.
     */
    private void showComboBoxAfter(double millis) {
        PauseTransition delay = new PauseTransition(Duration.millis(millis));
        delay.setOnFinished( event -> comboBox.show());
        delay.play();
    }
}
