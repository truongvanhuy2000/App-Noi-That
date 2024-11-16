package com.huy.appnoithat.Controller.LuaChonNoiThat.Utils;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class BackgroundColorUtils {
    private static final double DARKER_BRIGHTER_FACTOR = 0.9;

    public static Background getDarkerBackgroundColor(Background background) {
        Color color = (Color) background.getFills().get(0).getFill();
        CornerRadii cornerRadii = background.getFills().get(0).getRadii();
        Insets insets = background.getFills().get(0).getInsets();
        return new Background(new BackgroundFill(makeDarker(color), cornerRadii, insets));
    }

    public static Background getBrighterBackgroundColor(Background background) {
        Color color = (Color) background.getFills().get(0).getFill();
        CornerRadii cornerRadii = background.getFills().get(0).getRadii();
        Insets insets = background.getFills().get(0).getInsets();
        return new Background(new BackgroundFill(makeBrighter(color), cornerRadii, insets));
    }

    private static Color makeBrighter(Color color) {
        return color.deriveColor(0, 1.0, 1.0 / DARKER_BRIGHTER_FACTOR, 1.0);
    }

    private static Color makeDarker(Color color) {
        return color.deriveColor(0, 1.0, DARKER_BRIGHTER_FACTOR, 1.0);
    }
}
