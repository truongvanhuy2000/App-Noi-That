package com.huy.appnoithat.Controller.LuaChonNoiThat;

import javafx.scene.control.TreeItem;
import javafx.scene.control.skin.TableColumnHeader;

public class TableUtils {
    public static TreeItem<BangNoiThat> findYoungestChildern(TreeItem<BangNoiThat> root){
        if (root.isLeaf()){
            throw new IllegalArgumentException("Root must not be leaf");
        }
        while (true){
            if (root.getChildren().size() != 0){
                return root;
            }
        }
    }


}
