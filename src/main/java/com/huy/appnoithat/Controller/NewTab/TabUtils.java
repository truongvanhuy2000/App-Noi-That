package com.huy.appnoithat.Controller.NewTab;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ArrayList;
import java.util.List;

public class TabUtils {
    /**
     * @param tabContentList
     * @param tabPane        Sorting tabContent based on its current position on the tabPane
     */
    public static List<TabContent> resortTab(List<TabContent> tabContentList, TabPane tabPane) {
        List<Tab> tabList = tabPane.getTabs();
        List<TabContent> sortedTab = new ArrayList<>(tabContentList);
        for (TabContent tabContent : tabContentList) {
            sortedTab.set(tabList.indexOf(tabContent.getTab()) - 1, tabContent);
        }
        return sortedTab;
    }
}
