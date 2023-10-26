package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Enums.Action;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Objects;

public class CustomVatLieuCell extends TreeTableCell<BangNoiThat, String> {
    private ComboBox<String> comboBox;
    private final ObservableList<String> items;
    private final TreeTableView<BangNoiThat> TableNoiThat;
    private HBox hBox;
    private TextArea textArea;

    /**
     * Constructs a CustomVatLieuCell with the given ObservableList of items and a TreeTableView.
     *
     * @param items       The ObservableList of String items associated with this cell.
     * @param TableNoiThat The TreeTableView associated with this cell.
     */
    public CustomVatLieuCell(ObservableList<String> items, TreeTableView<BangNoiThat> TableNoiThat) {
        this.items = items;
        this.TableNoiThat = TableNoiThat;
    }


    /**
     * Initiates the editing process for this cell. If the ComboBox and HBox are not
     * initialized, creates them. If the cell is not editable or empty, the editing
     * process is not started. Otherwise, invokes the superclass's startEdit method,
     * sets the graphic content to the HBox containing the ComboBox, and schedules the
     * ComboBox to be shown after a delay of 100 milliseconds.
     */
    @Override
    public void startEdit() {
        if (comboBox == null) {
            createComboBox();
            createTextArea();
            createHBox();
        }
        if (!TableUtils.isEditable(TableNoiThat)) {
            return;
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

    /**
     * Updates the item within this cell and manages its visual representation based on
     * the provided item and its emptiness. Overrides the superclass method to customize
     * the cell's appearance. If the item is empty, displays no text or graphic content.
     * If editing, shows the ComboBox with the current item value and the associated HBox.
     * If not editing, displays the item text without any graphic content.
     *
     * @param item  The item to be displayed in the cell.
     * @param empty Indicates whether the cell should be displayed as empty.
     */
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
//            if (super.getItem() != null && !super.getItem().isEmpty()) {
//                customEdit(super.getItem());
//            }
            setGraphic(null);
        }
    }
//    private void customEdit(String value) {
//        TreeTableView<BangNoiThat> var2 = this.getTreeTableView();
//        TreeTablePosition<BangNoiThat, ?> pos = var2.getFocusModel().getFocusedCell();
//        if (var2 != null) {
//            TreeTableColumn.CellEditEvent var4 = new TreeTableColumn.CellEditEvent(var2, pos, TreeTableColumn.editCommitEvent(), value);
//            Event.fireEvent(this.getTableColumn(), var4);
//        }
//    }
    /**
     * Creates a ComboBox for editing the cell's content. If the ComboBox is already
     * initialized, this method does nothing. Configures the ComboBox's behavior,
     * such as handling commit actions and mouse clicks.
     */
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
    }

    /**
     * Creates an HBox containing a drop-down button and the ComboBox. If the HBox is already
     * initialized, this method does nothing. The drop-down button triggers the ComboBox
     * to be shown after a delay of 100 milliseconds.
     */
    private void createHBox() {
        if (hBox != null) {
            return;
        }
//        Button dropDownButton = new Button("V");
//        dropDownButton.setOnAction((e) -> {
//            showComboBoxAfter(100);
//        });
        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("Nhấn Ctrl + S để lưu"));
        vBox.getChildren().add(textArea);

        Button editButton = new Button();
        Image img = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/com/huy/appnoithat/Scene/icons/edit.png")));
        ImageView view = new ImageView(img);
        view.setFitHeight(20);
        view.setFitWidth(20);
        view.preserveRatioProperty().set(true);
        editButton.setGraphic(view);
//        editButton.setMaxSize(20, 20);
        editButton.setOnAction((e) -> {
            super.setText(null);
            textArea.setText(super.getItem());
            setGraphic(vBox);
        });

        hBox = new HBox();
        hBox.getChildren().add(editButton);
        hBox.getChildren().add(comboBox);
//        hBox.getChildren().add(editButton);
        hBox.setMaxWidth(Double.MAX_VALUE);
        hBox.setMaxHeight(Double.MAX_VALUE);
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
    private void createTextArea() {
        if (textArea != null) {
            return;
        }
        textArea = new TextArea();
        textArea.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textArea.setOnKeyPressed((key) -> {
            if (KeyboardUtils.isRightKeyCombo(Action.SAVE, key)) {
                commitEdit(textArea.getText());
                updateItem(textArea.getText(), false);
            }
        });
    }
}
