package com.huy.appnoithat.Controller.Common;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class DelayUtils {
    /**
     * Displays the ComboBox after a specified delay in milliseconds.
     *
     * @param millis The delay time in milliseconds before showing the ComboBox.
     */
    public static void doActionAfter(double millis, EventHandler<ActionEvent> eventHandler) {
        PauseTransition delay = new PauseTransition(Duration.millis(millis));
        delay.setOnFinished(eventHandler);
        delay.play();
    }
}
