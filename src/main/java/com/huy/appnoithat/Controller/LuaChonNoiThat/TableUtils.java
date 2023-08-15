package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Shared.ErrorUtils;
import com.huy.appnoithat.Shared.Utils;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.skin.TableColumnHeader;

public class TableUtils {
    public static TreeItem<BangNoiThat> findYoungestChildern(TreeItem<BangNoiThat> root){
        if (root.isLeaf()){
            throw new IllegalArgumentException("Root must not be leaf");
        }
        while (true){
            if (!root.getChildren().isEmpty()){
                return root;
            }
        }
    }
    public static boolean isALlowedToEdit(TreeTableColumn.CellEditEvent<BangNoiThat, String> event){
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        if (!Utils.isNumeric(currentItem.getValue().getSTT().getValue())){
            ErrorUtils.throwErrorSignal("Không được phép chọn trường cho mục ");
            event.consume();
            return false;
        }
        return true;
    }
}
