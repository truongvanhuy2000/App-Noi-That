package com.huy.appnoithat.Controller.LuaChonNoiThat;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.Command;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.CommandManager;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.ContinuousLineAddCommand;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.DeepCopyCommand;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.DeleteRowCommand;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

public class ButtonHandler {
    private final TreeTableView<BangNoiThat> TableNoiThat;
    private final CommandManager commandManager;

    /**
     * Initializes the ButtonHandler with the specified LuaChonNoiThatController instance and sets the internal
     * TreeTableView and LuaChonNoiThatController references.
     *
     * @param luaChonNoiThatController The LuaChonNoiThatController instance to associate with this ButtonHandler.
     */
    public ButtonHandler(LuaChonNoiThatController luaChonNoiThatController, CommandManager commandManager) {
        this.TableNoiThat = luaChonNoiThatController.getTableNoiThat();
        this.commandManager = commandManager;
    }

    /**
     * Handles the continuous addition of items in the TreeTableView. Determines the appropriate STT (sequence number)
     * format of the selected item and adds new items accordingly (Roman, Alphabet, or Numeric sequence). After adding
     * items, it rearranges the TreeTableView list to maintain the proper order.
     *
     * @param event The ActionEvent triggering the continuous line addition operation.
     */
    public void continuousLineAdd(ActionEvent event) {
        commandManager.execute(new ContinuousLineAddCommand(TableNoiThat));
    }

    public void duplicateButtonHandler(ActionEvent actionEvent) {
        commandManager.execute(new DeepCopyCommand(TableNoiThat));
    }

    public void handleDeleteAction(ActionEvent event) {
        commandManager.execute(new DeleteRowCommand(TableNoiThat));
    }
}
