package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Common.PopupUtils;
import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomHangMucCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.LuaChonNoiThatController;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class HangMucCollumHandler {
    private final ObservableList<String> hangMucList;
    private final LuaChonNoiThatService luaChonNoiThatService;
    final static Logger LOGGER = LogManager.getLogger(HangMucCollumHandler.class);

    /**
     * Constructs a HangMucCollumHandler with the given ObservableList of hangMucList.
     *
     * @param hangMucList The ObservableList of String representing hangMucList data.
     */
    public HangMucCollumHandler(ObservableList<String> hangMucList) {
        this.hangMucList = hangMucList;
        luaChonNoiThatService = new LuaChonNoiThatService();
    }

    /**
     * Handles cell edit commit events for the HangMuc column in the TreeTableView.
     * Updates the hangMuc property of the corresponding BangNoiThat object with the new value.
     *
     * @param event The CellEditEvent containing information about the edit event.
     */
    public void onEditCommitHangMuc(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
//        System.out.println("Hang muc: " + event.getNewValue());
        String stt = event.getRowValue().getValue().getSTT().getValue();
        String newValue = event.getNewValue();
        event.getRowValue().getValue().setHangMuc(newValue);
//        event.getTreeTableView().getSelectionModel().clearSelection();
//        if (Utils.isNumeric(stt)) {
//            String firstVatLieu = getTheFirstVatLieu(event.getRowValue());
//            if (firstVatLieu != null) {
//                event.getRowValue().getValue().setVatLieu(firstVatLieu);
//            }
//        }
    }
    private String getTheFirstVatLieu(TreeItem<BangNoiThat> currentItem) {
        try {
            String noiThat = currentItem.getParent().getValue().getHangMuc().getValue();
            String phongCach = currentItem.getParent().getParent().getValue().getHangMuc().getValue();
            String hangMuc = currentItem.getValue().getHangMuc().getValue();
            List<String> items = Utils.getObjectNameList(luaChonNoiThatService.findVatLieuListBy(phongCach, noiThat, hangMuc));
            return items.get(0);
        } catch (NullPointerException e) {
            PopupUtils.throwErrorSignal("Chưa lựa chọn thông tin phía trên");
            LOGGER.error("Error when get the first vat lieu item");
            return null;
        }
    }
    /**
     * Handles the event when a cell in the 'HangMuc' TreeTableColumn is being edited.
     * Dynamically populates 'hangMucList' based on the type of the edited item (noi that, phong cach, or hang muc).
     *
     * @param event The CellEditEvent containing information about the editing event.
     */
    public void onStartEditHangMuc(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        String stt = currentItem.getValue().getSTT().getValue();
        List<String> items = new ArrayList<>();
        hangMucList.clear();
        // Roman mean it's a noi that, mean that its parent is phong cach
        if (Utils.RomanNumber.isRoman(stt)) {
            String phongCach = currentItem.getParent().getValue().getHangMuc().getValue();
            try {
                items = Utils.getObjectNameList(luaChonNoiThatService.findNoiThatListBy(phongCach));
            } catch (NullPointerException e) {
                PopupUtils.throwErrorSignal("Chưa lựa chọn thông tin phía trên");
                return;
            }
            hangMucList.addAll(items);
            return;
        }
        // Alpha mean it's a phong cach
        if (Utils.isAlpha(stt)) {
            try {
                items = Utils.getObjectNameList(luaChonNoiThatService.findAllPhongCachNoiThat());
            } catch (NullPointerException e) {
                PopupUtils.throwErrorSignal("Chưa lựa chọn thông tin phía trên");
                return;
            }
            hangMucList.clear();
            hangMucList.addAll(items);
            return;
        }
        // Numeric mean it's a hang muc
        if (Utils.isNumeric(stt)) {
            String noiThat = currentItem.getParent().getValue().getHangMuc().getValue();
            String phongCach = currentItem.getParent().getParent().getValue().getHangMuc().getValue();
            try {
                items = Utils.getObjectNameList(luaChonNoiThatService.findHangMucListBy(phongCach, noiThat));
            } catch (NullPointerException e) {
                PopupUtils.throwErrorSignal("Chưa lựa chọn thông tin phía trên");
                return;
            }
            hangMucList.clear();
            hangMucList.addAll(items);
        }

    }

    /**
     * Provides a custom cell factory for the HangMuc column in the TreeTableView.
     * Initializes and returns a new instance of CustomHangMucCell with the specified 'hangMucList'.
     *
     * @param param The TreeTableColumn instance for which the custom cell factory is provided.
     * @return A new CustomHangMucCell instance with the given 'hangMucList'.
     */
    public TreeTableCell<BangNoiThat, String> getCustomCellFactory(TreeTableColumn<BangNoiThat, String> param) {
        return new CustomHangMucCell(hangMucList);
    }


    /**
     * Provides a custom cell value factory for the HangMuc column in the TreeTableView.
     * Retrieves the 'hangMuc' property value from the BangNoiThat object associated with the current row.
     * Returns an ObservableValue<String> representing the 'hangMuc' property of the cell data features.
     *
     * @param param The CellDataFeatures instance representing the data for the current cell.
     * @return An ObservableValue<String> representing the 'hangMuc' property of the current cell's data.
     *         Returns null if the current row's data is null.
     */
    public ObservableValue<String> getCustomCellValueFactory(TreeTableColumn.CellDataFeatures<BangNoiThat, String> param) {
        if (param.getValue() == null) {
            return null;
        }
        return param.getValue().getValue().getHangMuc();
    }
}
