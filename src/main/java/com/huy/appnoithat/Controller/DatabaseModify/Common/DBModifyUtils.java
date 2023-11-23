package com.huy.appnoithat.Controller.DatabaseModify.Common;

import com.huy.appnoithat.Entity.CommonItemInterface;
import javafx.collections.ObservableList;

public class DBModifyUtils {

    /**
     * Generates a new name with a numerical suffix for creating a new item.
     * @param currentPos The current position or index of the new item in the list.
     * @return A new name with a numerical suffix indicating its position.
     */
    public static String getNewName(int currentPos) {
        return String.format("<Thêm mới %d>", currentPos);
    }

    /**
     * Ensures the provided name is unique within the given list of items.
     * If a duplicate name is found, appends a numerical suffix to make it unique.
     * @param currentName The name to be checked for duplication.
     * @param list The list of items to check for duplicates.
     * @return A unique name if a duplicate is found; otherwise, the original name.
     */
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
