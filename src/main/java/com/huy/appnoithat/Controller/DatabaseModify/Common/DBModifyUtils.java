package com.huy.appnoithat.Controller.DatabaseModify.Common;

import com.huy.appnoithat.Entity.Common.CommonItemInterface;
import javafx.collections.ObservableList;
import org.apache.poi.ss.formula.functions.T;

public class DBModifyUtils {
    public static String getNewName(int currentPos) {
        return String.format("<Thêm mới %d>", currentPos);
    }
    public static String getNotDuplicateName(String currentName, ObservableList<? extends CommonItemInterface> list) {
        if (list.stream().anyMatch(item -> {
            if (item.getName() == null) {
                return false;
            }
            return item.getName().trim().equals(currentName.trim());
        })) {
            return currentName + "(1)";
        }
        return currentName;
    }
}
