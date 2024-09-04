package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomVatLieuCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.CommandManager;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.EditCommitVatLieuCommand;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.DataModel.Entity.ThongSo;
import com.huy.appnoithat.DataModel.Entity.VatLieu;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class VatLieuColumn implements CustomColumn  {
    private final TreeTableColumn<BangNoiThat, String> VatLieu;
    private final LuaChonNoiThatService luaChonNoiThatService;
    private final CommandManager commandManager;

    private final HashMap<String, ThongSo> vatLieuThongSoHashMap = new HashMap<>();
    private final ObservableList<String> vatLieuList = FXCollections.observableArrayList();

    @Override
    public void setup() {
        VatLieu.setCellValueFactory(this::getCustomCellValueFactory);
        VatLieu.setCellFactory(this::getCustomCellFactory);
        VatLieu.setOnEditStart(this::onStartEditVatLieu);
        VatLieu.setOnEditCommit(this::onEditCommitVatLieu);
    }

    /**
     * Provides a custom cell factory for the VatLieu column in the TreeTableView.
     * Initializes and returns a new CustomVatLieuCell with the provided 'vatLieuList' and TreeTableView instance.
     *
     * @param param The TreeTableColumn instance for which the custom cell factory is provided.
     * @return A customized TreeTableCell for the VatLieu column.
     */
    private TreeTableCell<BangNoiThat, String> getCustomCellFactory(TreeTableColumn<BangNoiThat, String> param) {
        return new CustomVatLieuCell(vatLieuList, param.getTreeTableView());
    }


    /**
     * Custom cell value factory for the VatLieu column in the TreeTableView.
     * Retrieves and returns the observable value representing the VatLieu property of the current BangNoiThat item.
     *
     * @param param The CellDataFeatures instance representing the data of the current cell.
     * @return An observable value representing the VatLieu property of the current BangNoiThat item.
     */
    private ObservableValue<String> getCustomCellValueFactory(TreeTableColumn.CellDataFeatures<BangNoiThat, String> param) {
        if (param.getValue() == null) {
            return null;
        }
        return param.getValue().getValue().getVatLieu();
    }


    /**
     * Handles the commit event for the VatLieu column in the TreeTableView.
     * Updates the VatLieu property of the corresponding BangNoiThat item and recalculates related properties.
     *
     * @param event The CellEditEvent instance representing the edit event for the VatLieu column.
     */
    private void onEditCommitVatLieu(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        commandManager.execute(new EditCommitVatLieuCommand(vatLieuThongSoHashMap, event));
    }


    /**
     * Handles the start edit event for the VatLieu column in the TreeTableView.
     * Populates the VatLieu dropdown menu based on the selected HangMuc, NoiThat, and PhongCach.
     * Retrieves and displays available VatLieu items for the user to choose from.
     *
     * @param event The CellEditEvent instance representing the start edit event for the VatLieu column.
     */
    private void onStartEditVatLieu(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        if (currentItem == null) {
            return;
        }
        List<String> items;

        // Check if editing is allowed for the current event
        if (!TableUtils.isAllowedToEdit(currentItem)) {
            return;
        }

        // Retrieve HangMuc, NoiThat, and PhongCach values from the current TreeItem
        String hangMuc = currentItem.getValue().getHangMuc().getValue();
        String noiThat = currentItem.getParent().getValue().getHangMuc().getValue();
        String phongCach = currentItem.getParent().getParent().getValue().getHangMuc().getValue();

        // Retrieve a list of VatLieu objects based on PhongCach, NoiThat, and HangMuc
        List<VatLieu> vatLieus = luaChonNoiThatService.findVatLieuListBy(phongCach, noiThat, hangMuc);
        if (vatLieus == null) {
            return;
        }

        // Populate the vatLieuThongSoHashMap for corresponding VatLieu items
        vatLieus.forEach(vatLieu -> {
            vatLieuThongSoHashMap.put(vatLieu.getName(), vatLieu.getThongSo());
        });
        items = Utils.getObjectNameList(vatLieus);
        this.vatLieuList.clear();
        this.vatLieuList.addAll(items);
    }
}
