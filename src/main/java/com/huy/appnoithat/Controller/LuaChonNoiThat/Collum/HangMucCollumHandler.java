package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.BangNoiThat;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomComboboxCell;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import com.huy.appnoithat.Shared.ErrorUtils;
import com.huy.appnoithat.Shared.Utils;
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
    private ObservableList<String> hangMucList;
    private final static Logger logger = LogManager.getLogger(HangMucCollumHandler.class);

    private final LuaChonNoiThatService luaChonNoiThatService;

    public HangMucCollumHandler(ObservableList<String> hangMucList) {
        this.hangMucList = hangMucList;
        luaChonNoiThatService = new LuaChonNoiThatService();
    }

    public void onEditCommitHangMuc(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        String newValue =  event.getNewValue();
        event.getRowValue().getValue().setHangMuc(newValue);
        event.getTreeTableView().getSelectionModel().clearSelection();
    }

    public void onStartEditHangMuc(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        String stt = currentItem.getValue().getSTT().getValue();
        List<String> items = new ArrayList<>();
        hangMucList.clear();
        // Roman mean it's a noi that, mean that its parent is phong cach
        if (Utils.RomanNumber.isRoman(stt)){
            String phongCach = currentItem.getParent().getValue().getHangMuc().getValue();
            try {
                items = Utils.getObjectNameList(luaChonNoiThatService.findNoiThatByPhongCachName(phongCach));
            } catch (NullPointerException e) {
                logger.error(e.getMessage());
                ErrorUtils.throwErrorSignal("Chưa lựa chọn thông tin phía trên");
                return;
            }
            hangMucList.addAll(items);
            return;
        }
        // Alpha mean it's a phong cach
        if (Utils.isAlpha(stt)){
            try {
                items = Utils.getObjectNameList(luaChonNoiThatService.findAllPhongCachNoiThat());
            } catch (NullPointerException e) {
                logger.error(e.getMessage());
                ErrorUtils.throwErrorSignal("Chưa lựa chọn thông tin phía trên");
                return;
            }
            hangMucList.clear();
            hangMucList.addAll(items);
            return;
        }
        // Numeric mean it's a hang muc
        if (Utils.isNumeric(stt)){
            String noiThat = currentItem.getParent().getValue().getHangMuc().getValue();
            String phongCach = currentItem.getParent().getParent().getValue().getHangMuc().getValue();
            try{
                items = Utils.getObjectNameList(luaChonNoiThatService.findHangMucListByPhongCachAndNoiThat(phongCach, noiThat));
            } catch (NullPointerException e) {
                logger.error(e.getMessage());
                ErrorUtils.throwErrorSignal("Chưa lựa chọn thông tin phía trên");
                return;
            }
            hangMucList.clear();
            hangMucList.addAll(items);
        }

    }
    public TreeTableCell<BangNoiThat, String> getCustomCellFactory(TreeTableColumn<BangNoiThat, String> param){
        return new CustomComboboxCell(hangMucList);
    }
    public ObservableValue<String> getCustomCellValueFactory(TreeTableColumn.CellDataFeatures<BangNoiThat, String> param){
        return param.getValue().getValue().getHangMuc();
    }
}
