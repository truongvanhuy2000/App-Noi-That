package com.huy.appnoithat.Controller.LuaChonNoiThat.Common;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeItem;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class ItemTypeUtils {
    public static String createFullId(ItemType type, String id) {
        return type.toString() + ":" + id;
    }

    public static String getIdFromFullId(String fullId) {
        if (fullId == null || fullId.isEmpty()) {
            return "";
        }
        return fullId.split(":")[1];
    }

    public static ItemType determineItemType(String fullId) {
        if (fullId == null || fullId.isEmpty()) {
            return ItemType.NONE;
        }
        String type = fullId.split(":")[0];
        return switch (type) {
            case "NUMERIC" -> ItemType.NUMERIC;
            case "AlPHA" -> ItemType.AlPHA;
            case "ROMAN" -> ItemType.ROMAN;
            default -> ItemType.NONE;
        };
    }

    public static ItemType determineItemType(@NonNull TreeItem<BangNoiThat> item) {
        return determineItemType(item.getValue().getSTT().getValue());
    }

    public static Optional<String> findTheNextStt(String stt) {
        if (stt == null || stt.isEmpty()) {
            return Optional.empty();
        }
        String id = getIdFromFullId(stt);
        switch (ItemTypeUtils.determineItemType(stt)) {
            case ROMAN -> {
                int nextStt = Utils.romanToInt(id) + 1;
                return Optional.of(Utils.toRoman(nextStt));
            }
            case AlPHA -> {
                char nextLetter = (char) (id.charAt(0) + 1);
                return Optional.of(String.valueOf(nextLetter));
            }
            case NUMERIC -> {
                return Optional.of(String.valueOf(Integer.parseInt(id) + 1));
            }
            default -> {
                return Optional.empty();
            }
        }
    }
}
