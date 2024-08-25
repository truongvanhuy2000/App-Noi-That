package com.huy.appnoithat.Controller.LuaChonNoiThat.Collum;

import com.huy.appnoithat.Controller.LuaChonNoiThat.Common.TableCalculationUtils;
import com.huy.appnoithat.Controller.LuaChonNoiThat.DataModel.BangNoiThat;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;


public class KichThuocHandler {
    //    final static Logger LOGGER = LogManager.getLogger(KichThuocHandler.class);
    private final TreeTableView<BangNoiThat> TableNoiThat;
    private final TreeTableColumn<BangNoiThat, Double> Cao;
    private final TreeTableColumn<BangNoiThat, Double> Dai;
    private final TreeTableColumn<BangNoiThat, Double> Rong;
    private final TreeTableColumn<BangNoiThat, Long> DonGia;

    /**
     * Handler class for managing size-related columns in a TreeTableView.
     * Manages the editing events for columns representing height (Cao), length (Dai),
     * width (Rong), and unit price (DonGia) of items in the TreeTableView.
     *
     * @param tableNoiThat The TreeTableView representing the table of items.
     * @param cao          The TreeTableColumn representing the height of items.
     * @param dai          The TreeTableColumn representing the length of items.
     * @param rong         The TreeTableColumn representing the width of items.
     * @param donGia       The TreeTableColumn representing the unit price of items.
     */
    public KichThuocHandler(TreeTableView<BangNoiThat> tableNoiThat,
                            TreeTableColumn<BangNoiThat, Double> cao,
                            TreeTableColumn<BangNoiThat, Double> dai,
                            TreeTableColumn<BangNoiThat, Double> rong,
                            TreeTableColumn<BangNoiThat, Long> donGia) {
        TableNoiThat = tableNoiThat;
        Cao = cao;
        Dai = dai;
        Rong = rong;
        DonGia = donGia;
    }


    /**
     * Handles cell edit commit events for the height, length, width, and unit price columns.
     * Updates the corresponding property values of the BangNoiThat item based on the edited value.
     * Recalculates and updates the volume, total price, and triggers total calculation for the parent item.
     *
     * @param event The CellEditEvent containing information about the edit event.
     */
    public void onCommitEditKichThuoc(TreeTableColumn.CellEditEvent<BangNoiThat, ?> event) {
        if (event.getSource().equals(Cao)) {
            event.getRowValue().getValue().setCao((double) event.getNewValue());
        }
        if (event.getSource().equals(Dai)) {
            event.getRowValue().getValue().setDai((double) event.getNewValue());
        }
        if (event.getSource().equals(Rong)) {
            event.getRowValue().getValue().setRong((double) event.getNewValue());
        }
        if (event.getSource().equals(DonGia)) {
            event.getRowValue().getValue().setDonGia((long) event.getNewValue());
        }
        Double dai = event.getRowValue().getValue().getDai().getValue();
        Double rong = event.getRowValue().getValue().getRong().getValue();
        Double cao = event.getRowValue().getValue().getCao().getValue();
        String donvi = event.getRowValue().getValue().getDonVi().getValue();
        Long dongia = event.getRowValue().getValue().getDonGia().getValue();
        Double khoiluong = TableCalculationUtils.calculateKhoiLuong(dai, cao, rong, donvi);
        Long thanhTien = TableCalculationUtils.calculateThanhTien(khoiluong, dongia);
        event.getRowValue().getValue().setKhoiLuong(khoiluong);
        event.getRowValue().getValue().setThanhTien(thanhTien);

        TableCalculationUtils.recalculateAllTongTien(TableNoiThat);
    }
}
