package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomVatLieuCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.CommandManager;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Command.implementation.EditCommitVatLieuCommand;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.NoiThatItem;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class VatLieuColumn implements CustomColumn  {
    private final Logger LOGGER = LogManager.getLogger(this);
    private final TreeTableColumn<BangNoiThat, NoiThatItem> VatLieu;
    private final LuaChonNoiThatService luaChonNoiThatService;
    private final CommandManager commandManager;

    private final HashMap<Integer, ThongSo> vatLieuThongSoHashMap = new HashMap<>();
    private final ObservableList<NoiThatItem> vatLieuList = FXCollections.observableArrayList();

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
    private TreeTableCell<BangNoiThat, NoiThatItem> getCustomCellFactory(TreeTableColumn<BangNoiThat, NoiThatItem> param) {
        return new CustomVatLieuCell(vatLieuList, param.getTreeTableView());
    }


    /**
     * Custom cell value factory for the VatLieu column in the TreeTableView.
     * Retrieves and returns the observable value representing the VatLieu property of the current BangNoiThat item.
     *
     * @param param The CellDataFeatures instance representing the data of the current cell.
     * @return An observable value representing the VatLieu property of the current BangNoiThat item.
     */
    private ObservableValue<NoiThatItem> getCustomCellValueFactory(TreeTableColumn.CellDataFeatures<BangNoiThat, NoiThatItem> param) {
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
    private void onEditCommitVatLieu(TreeTableColumn.CellEditEvent<BangNoiThat, NoiThatItem> event) {
        commandManager.execute(new EditCommitVatLieuCommand(vatLieuThongSoHashMap, event));
    }


    /**
     * Handles the start edit event for the VatLieu column in the TreeTableView.
     * Populates the VatLieu dropdown menu based on the selected HangMuc, NoiThat, and PhongCach.
     * Retrieves and displays available VatLieu items for the user to choose from.
     *
     * @param event The CellEditEvent instance representing the start edit event for the VatLieu column.
     */
    private void onStartEditVatLieu(TreeTableColumn.CellEditEvent<BangNoiThat, NoiThatItem> event) {
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        if (currentItem == null) {
            return;
        }
        // Check if editing is allowed for the current event
        if (!TableUtils.isAllowedToEdit(currentItem)) {
            return;
        }
        NoiThatItem hangMuc = currentItem.getValue().getHangMuc().getValue();
        if (hangMuc == null) {
            LOGGER.warn("Hang muc must not be null");
            return;
        }
        List<VatLieu> vatLieus = luaChonNoiThatService.findVatLieuListByHangMucID(hangMuc.getId());
        if (vatLieus == null) {
            return;
        }
        this.vatLieuList.clear();
        vatLieus.forEach(vatLieu -> {
            NoiThatItem noiThatItem = new NoiThatItem(vatLieu.getId(), vatLieu.getName());
            vatLieuThongSoHashMap.put(vatLieu.getId(), vatLieu.getThongSo());
            this.vatLieuList.addAll(noiThatItem);
        });
    }
}
