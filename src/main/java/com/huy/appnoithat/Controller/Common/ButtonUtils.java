package com.huy.appnoithat.Controller.Common;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ButtonUtils {
    public static Button createEditButton() {
        Button editButton = new Button();
        Image img = new Image(Objects.requireNonNull(ButtonUtils.class.getResourceAsStream("/com/huy/appnoithat/Scene/icons/edit.png")));
        ImageView view = new ImageView(img);
        view.setFitHeight(15);
        view.setFitWidth(15);
        view.preserveRatioProperty().set(false);
        editButton.setGraphic(view);
        editButton.setPrefSize(20, 20);
        editButton.setMaxSize(20, 20);
        return editButton;
    }

    public static Button createDropDownButton(EventHandler<ActionEvent> eventHandler) {
        Button dropDownBtn = new Button();
        dropDownBtn.setOnAction(eventHandler);
        Image img = new Image(Objects.requireNonNull(ButtonUtils.class.getResourceAsStream("/com/huy/appnoithat/Scene/icons/dropdown.png")));
        ImageView view = new ImageView(img);
        view.setFitHeight(10);
        view.setFitWidth(10);
        view.preserveRatioProperty().set(false);
        dropDownBtn.setGraphic(view);
        dropDownBtn.setPrefSize(20, 20);
        dropDownBtn.setMaxSize(20, 20);
        return dropDownBtn;
    }
}
