package com.huy.appnoithat.Controller.FileNoiThatExplorer;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.DataModel.RecentFile;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Objects;
import java.util.function.Consumer;

public class ActionCell extends TableCell<RecentFile, String> {
    private static final String DELETE_ICON = "/com/huy/appnoithat/Scene/icons/icons8-delete-48.png";
    private HBox hBox;
    Consumer<Integer> deleteAction;
    public ActionCell(Consumer<Integer> deleteAction) {
        if (hBox == null) {
            init();
        }
        this.deleteAction = deleteAction;
    }
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            setGraphic(hBox);
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(hBox);
        } else {
            setGraphic(null);
        }
    }

    private void init() {
        Button deleteButton = new Button();
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(
                this.getClass().getResourceAsStream(DELETE_ICON))));
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        deleteButton.setGraphic(imageView);
        hBox = new HBox(deleteButton);
        deleteButton.setOnAction((event) -> {
            PopupUtils.throwSuccessNotification("Xoá thành công");
            deleteAction.accept(getTableRow().getIndex());
        });
    }
}
