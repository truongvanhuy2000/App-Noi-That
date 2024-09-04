package com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.ItemTypeUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Constant.ItemType;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ContinuousAddForNumericSttCommand implements Command {
    private final Logger LOGGER = LogManager.getLogger(this);

    private final TreeTableView<BangNoiThat> TableNoiThat;
    private final List<TreeItem<BangNoiThat>> createdItemList = new ArrayList<>();
    private TreeItem<BangNoiThat> parent;

    @Override
    public void execute() {
        TreeItem<BangNoiThat> item = TableNoiThat.getSelectionModel().getSelectedItem();
        if (item == null) {
            return;
        }
        parent = item.getParent();
        if (item.nextSibling() != null) {
            TableUtils.createNewSibling(item);
        } else {
            addNewNumericStt(5);
        }
    }

    @Override
    public void undo() {
        if (parent != null) {
            LOGGER.info("undo ContinuousAddForNumericSttCommand");
            parent.getChildren().removeAll(createdItemList);
        }
    }

    private void addNewNumericStt(int num) {
        for (int i = 0; i < num; i++) {
            TreeItem<BangNoiThat> item = TableNoiThat.getSelectionModel().getSelectedItem();
            if (item == null) {
                throw new NullPointerException("No item is selected");
            }
            TreeItem<BangNoiThat> newItem = continuousLineAddForNumericStt(item);
            if (newItem != null) {
                createdItemList.add(newItem);
            }
        }
    }

    /**
     * Performs the continuous addition of items in the numeric sequence format.
     * Adds a new item with the next available numeric STT to the parent of the given item.
     *
     * @param currentSelectedItem The currently selected item in the TreeTableView.
     */
    private TreeItem<BangNoiThat> continuousLineAddForNumericStt(TreeItem<BangNoiThat> currentSelectedItem) {
        TreeItem<BangNoiThat> parent = currentSelectedItem.getParent();
        if (currentSelectedItem.getValue() == null) {
            return null;
        }
        String nextStt = ItemTypeUtils.findTheNextStt(currentSelectedItem.getValue());
        TreeItem<BangNoiThat> tempNewItem = TableUtils.createNewItem(ItemType.NUMERIC, nextStt);
        parent.getChildren().add(tempNewItem);
        selectNewItem(tempNewItem);
        return tempNewItem;
    }

    /**
     * Selects the provided TreeItem in the TreeTableView and clears any existing selections.
     * If the provided item is null, the method does nothing.
     *
     * @param tempNewItem The TreeItem to be selected in the TreeTableView.
     */
    private void selectNewItem(TreeItem<BangNoiThat> tempNewItem) {
        TableNoiThat.getSelectionModel().clearSelection();
        TableNoiThat.getSelectionModel().select(tempNewItem);
    }
}
