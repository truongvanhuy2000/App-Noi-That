package com.huy.appnoithat.Controller.DatabaseModify.Common;

import com.huy.appnoithat.Entity.Common.CommonItemInterface;
import javafx.collections.ObservableList;

public class DBModifyUtils {
    public static String getNewName(int currentPos) {
        return String.format("<Thêm mới %d>", currentPos);
    }
    public static String getNotDuplicateName(String currentName, ObservableList<? extends CommonItemInterface> list) {
        int count = 0;
        for(CommonItemInterface item : list) {
            if(item.getName().trim().equals(currentName.trim())) {
                count++;
            }
        }
        if (count > 1) {
            return String.format("%s (%d)", currentName, count - 1);
        }
        return currentName;
    }
}
