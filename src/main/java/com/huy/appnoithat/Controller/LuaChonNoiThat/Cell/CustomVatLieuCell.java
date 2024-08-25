package com.huy.appnoithat.Controller.LuaChonNoiThat.Cell;

import com.huy.appnoithat.Common.KeyboardUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.DataModel.Enums.Action;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
    private Button dropDownBtn;
    private Button editButton;

    /**
     * Constructs a CustomVatLieuCell with the given ObservableList of items and a TreeTableView.
     *
     * @param items        The ObservableList of String items associated with this cell.
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
        if (!TableUtils.isEditable(TableNoiThat)) {
            return;
        }
        init();
        if (!isEmpty()) {
            super.startEdit();
            setGraphic(new HBox(dropDownBtn, editButton));
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
            setGraphic(comboBox);
            comboBox.show();
        } else {
            super.setText(super.getItem());
            setGraphic(null);
        }
    }

    private void init() {
        createComboBox();
        createDropDownBtn();
        createEditBtn();
    }

    private void createDropDownBtn() {
        if (dropDownBtn != null) {
            return;
        }
        dropDownBtn = new Button();
        dropDownBtn.setOnAction((e) -> {
            setGraphic(comboBox);
            showComboBoxAfter(200);
        });
        Image img = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/com/huy/appnoithat/Scene/icons/dropdown.png")));
        ImageView view = new ImageView(img);
        view.setFitHeight(10);
        view.setFitWidth(10);
        view.preserveRatioProperty().set(false);
        dropDownBtn.setGraphic(view);
        dropDownBtn.setPrefSize(20, 20);
        dropDownBtn.setMaxSize(20, 20);
    }

    private void createEditBtn() {
        editButton = new Button();
        Image img = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/com/huy/appnoithat/Scene/icons/edit.png")));
        ImageView view = new ImageView(img);
        view.setFitHeight(15);
        view.setFitWidth(15);
        view.preserveRatioProperty().set(false);
        editButton.setGraphic(view);
        editButton.setPrefSize(20, 20);
        editButton.setMaxSize(20, 20);

        TextArea textArea = new TextArea();
        textArea.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textArea.setOnKeyPressed((key) -> {
            if (KeyboardUtils.isRightKeyCombo(Action.NEXT_LINE, key)) {
                int currentTextPos = textArea.getCaretPosition();
                if (currentTextPos > textArea.getText().length()) {
                    currentTextPos = textArea.getText().length();
                }
                textArea.setText(new StringBuilder(textArea.getText())
                        .insert(currentTextPos, System.lineSeparator()).toString());
                textArea.positionCaret(currentTextPos + 1);
                key.consume();
                return;
            }
            if (KeyboardUtils.isRightKeyCombo(Action.COMMIT, key)) {
                commitEdit(textArea.getText().trim().strip());
                updateItem(textArea.getText().trim().strip(), false);
                key.consume();
            }
        });
        VBox vBox = new VBox();
        vBox.getChildren().add(new Label("Nhấn Alt + Enter để xuống dòng"));
        vBox.getChildren().add(textArea);

        editButton.setOnAction((e) -> {
            super.setText(null);
            textArea.setText(super.getItem());
            setGraphic(vBox);
        });
    }

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
        comboBox.setOnAction((e) -> {
            if (comboBox.getSelectionModel().getSelectedItem() != null) {
                super.commitEdit(comboBox.getSelectionModel().getSelectedItem());
                updateItem(comboBox.getSelectionModel().getSelectedItem(), false);
                cancelEdit();
            }
        });
        comboBox.setVisibleRowCount(8);
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
        comboBox.getStyleClass().add("combo-border");
        getTableColumn().prefWidthProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println("New value: " + newValue);
            comboBox.setPrefWidth(newValue.doubleValue() - 10);
        });
        Platform.runLater(() -> {
            comboBox.setPrefWidth(getTableColumn().getWidth() - 10);
        });
    }

    /**
     * Displays the ComboBox after a specified delay in milliseconds.
     *
     * @param millis The delay time in milliseconds before showing the ComboBox.
     */
    private void showComboBoxAfter(double millis) {
        PauseTransition delay = new PauseTransition(Duration.millis(millis));
        delay.setOnFinished(event -> comboBox.show());
        delay.play();
    }
}
