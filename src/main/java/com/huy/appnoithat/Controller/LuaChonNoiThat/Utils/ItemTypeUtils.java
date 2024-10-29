package com.huy.appnoithat.Controller.LuaChonNoiThat.Utils;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableRow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemTypeUtils {
    public static String findTheNextStt(BangNoiThat item) {
        String id = item.getSTT().getValue();
        switch (item.getItemType()) {
            case ROMAN -> {
                int nextStt = Utils.romanToInt(id) + 1;
                return Utils.toRoman(nextStt);
            }
            case AlPHA -> {
                char nextLetter = (char) (id.charAt(0) + 1);
                return String.valueOf(nextLetter);
            }
            case NUMERIC -> {
                return String.valueOf(Integer.parseInt(id) + 1);
            }
            default -> {
                throw new RuntimeException();
            }
        }
    }

    public static void rowStyling(ItemType itemType, TreeTableRow<BangNoiThat> row) {
        switch (itemType) {
            case ROMAN -> {
                row.setStyle("-fx-font-weight: bold");
                row.setBackground(new Background(new BackgroundFill(Color.web("#d0e1e7"), null, null)));
            }
            case AlPHA -> {
                row.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                row.setBackground(new Background(new BackgroundFill(Color.web("#d0e1e7"), null, null)));
            }
            case NUMERIC -> row.setStyle("-fx-font-weight: normal");
        }
    }
}
