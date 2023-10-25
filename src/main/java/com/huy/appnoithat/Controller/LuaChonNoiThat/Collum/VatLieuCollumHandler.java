package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Common.Utils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Cell.CustomVatLieuCell;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import com.huy.appnoithat.Entity.ThongSo;
import com.huy.appnoithat.Entity.VatLieu;
import com.huy.appnoithat.Service.LuaChonNoiThat.LuaChonNoiThatService;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class VatLieuCollumHandler {
    private final ObservableList<String> vatLieuList;
    private final LuaChonNoiThatService luaChonNoiThatService;
    HashMap<String, ThongSo> vatLieuThongSoHashMap = new HashMap<>();

    /**
     * Handler class for managing the VatLieu (Material) column in a TreeTableView of BangNoiThat items.
     * Handles custom cell factory for the VatLieu column based on the provided 'vatLieuList'.
     * Provides a custom cell factory for rendering VatLieu cells in the TreeTableView.
     *
     * @param vatLieuList The list of VatLieu items to populate the VatLieu column options.
     */
    public VatLieuCollumHandler(ObservableList<String> vatLieuList) {
        this.vatLieuList = vatLieuList;
        luaChonNoiThatService = new LuaChonNoiThatService();
    }

    /**
     * Provides a custom cell factory for the VatLieu column in the TreeTableView.
     * Initializes and returns a new CustomVatLieuCell with the provided 'vatLieuList' and TreeTableView instance.
     *
     * @param param The TreeTableColumn instance for which the custom cell factory is provided.
     * @return A customized TreeTableCell for the VatLieu column.
     */
    public TreeTableCell<BangNoiThat, String> getCustomCellFactory(TreeTableColumn<BangNoiThat, String> param) {
        return new CustomVatLieuCell(vatLieuList, param.getTreeTableView());
    }


    /**
     * Custom cell value factory for the VatLieu column in the TreeTableView.
     * Retrieves and returns the observable value representing the VatLieu property of the current BangNoiThat item.
     *
     * @param param The CellDataFeatures instance representing the data of the current cell.
     * @return An observable value representing the VatLieu property of the current BangNoiThat item.
     */
    public ObservableValue<String> getCustomCellValueFactory(TreeTableColumn.CellDataFeatures<BangNoiThat, String> param) {
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
    public void onEditCommitVatLieu(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        String vatLieu = event.getNewValue();
        event.getRowValue().getValue().setVatLieu(vatLieu);
        ThongSo coresspondingThongSo = vatLieuThongSoHashMap.get(vatLieu);
        if (coresspondingThongSo == null) {
            return;
        }
        Double dai = Objects.requireNonNullElse(coresspondingThongSo.getDai(), 0.0);
        Double rong = Objects.requireNonNullElse(coresspondingThongSo.getRong(), 0.0);
        Double cao = Objects.requireNonNullElse(coresspondingThongSo.getCao(), 0.0);
        Long donGia = coresspondingThongSo.getDon_gia();
        String donVi = coresspondingThongSo.getDon_vi();
        Double khoiLuong = TableCalculationUtils.calculateKhoiLuong(dai, cao, rong, donVi);
        Long thanhTien = TableCalculationUtils.calculateThanhTien(khoiLuong, donGia);

        event.getRowValue().getValue().setThanhTien(thanhTien);
        event.getRowValue().getValue().setKhoiLuong(khoiLuong);
        event.getRowValue().getValue().setDai(dai);
        event.getRowValue().getValue().setRong(rong);
        event.getRowValue().getValue().setCao(cao);
        event.getRowValue().getValue().setDonGia(donGia);
        event.getRowValue().getValue().setDonVi(donVi);

        TableCalculationUtils.calculateTongTien(event.getRowValue().getParent());
//        event.getTreeTableView().getSelectionModel().clearSelection();
    }


    /**
     * Handles the start edit event for the VatLieu column in the TreeTableView.
     * Populates the VatLieu dropdown menu based on the selected HangMuc, NoiThat, and PhongCach.
     * Retrieves and displays available VatLieu items for the user to choose from.
     *
     * @param event The CellEditEvent instance representing the start edit event for the VatLieu column.
     */
    public void onStartEditVatLieu(TreeTableColumn.CellEditEvent<BangNoiThat, String> event) {
        System.out.println("Start edit vat lieu");
        TreeItem<BangNoiThat> currentItem = event.getRowValue();
        List<String> items;

        // Check if editing is allowed for the current event
        if (!TableUtils.isAllowedToEdit(event)) {
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
