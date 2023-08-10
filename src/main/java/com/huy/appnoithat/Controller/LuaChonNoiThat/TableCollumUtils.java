package com.huy.appnoithat.Controller.LuaChonNoiThat;

import javafx.scene.control.TableColumnBase;
import javafx.scene.control.skin.TableColumnHeader;

public class TableCollumUtils extends TableColumnHeader {

    public TableCollumUtils(TableColumnBase tableColumnBase) {
        super(tableColumnBase);
    }

    public void resizeToFitContent() {
        // Do something here
        resizeColumnToFitContent(-1);
    }
}
